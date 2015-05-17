/**
 * 
 */
package me.firecloud.ideawork.tradewatcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.firecloud.ideawork.tradewatcher.model.CustomizationInfo;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Trade;
import com.taobao.api.internal.tmc.Message;
import com.taobao.api.internal.tmc.MessageHandler;
import com.taobao.api.internal.tmc.MessageStatus;
import com.taobao.api.request.TradeGetRequest;
import com.taobao.api.response.TradeGetResponse;

/**
 * @author kkppccdd
 * 
 */
public class TradeMessageHandler implements MessageHandler {
	private static final Log log = LogFactory.getLog(TradeMessageHandler.class);

	/************
	 * constants
	 */
	// pay will trigger tradeChanged message
	public static final String TOPIC_TRADE_CHNAGED = "taobao_trade_TradeChanged";
	public static final String TOPIC_TRADE_ALIPAY_CREATE = "taobao_trade_TradeAlipayCreate";
	public static final String TOPIC_TRADE_SUCCESS = "taobao_trade_TradeSuccess";

	private TaobaoClient taobaoClient;
	
	private RestService tradeDataService;

	private ObjectMapper objectMapper;

	public TradeMessageHandler() {
		AuthenticationSupport auSupport = AuthenticationSupport.getInstance();
		taobaoClient = new DefaultTaobaoClient(auSupport.getTopServiceUrl(),
				auSupport.getAppKey(), auSupport.getAppSecret());

		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);

		String dataServiceUrl=AuthenticationSupport.getInstance().getProperty(AuthenticationSupport.PROPERTY_DATA_SERVICE_URL);
		String dataServiceUsername=AuthenticationSupport.getInstance().getProperty(AuthenticationSupport.PROPERTY_DATA_SERVICE_USERNAME);
		String dataServicePassword=AuthenticationSupport.getInstance().getProperty(AuthenticationSupport.PROPERTY_DATA_SERVICE_PASSWORD);
		
		tradeDataService = new RestService(dataServiceUrl, "trade", dataServiceUsername, dataServicePassword);
	}

	/**
	 * 
	 */
	@Override
	public void onMessage(Message message, MessageStatus status)
			throws Exception {
		try {
			String topic = message.getTopic();
			String content = message.getContent();

			if (topic.equals(TOPIC_TRADE_ALIPAY_CREATE)) {
				// new trade
				Long tid = extractTid(content);
				Trade taobaoTrade = getTradeInfoFromTaobao(tid);

				// convert to internal trade
				me.firecloud.ideawork.tradewatcher.model.Trade newTrade = taobaoTrade2InternalTrade(taobaoTrade);

				// post to data service

				String newTradeJson = objectMapper.writeValueAsString(newTrade);

				if (log.isDebugEnabled()) {
					log.debug(String.format("new trade: %s", newTradeJson));
				}
				
				me.firecloud.ideawork.tradewatcher.model.Trade savedTrade = tradeDataService.save(newTrade);
				
				if (log.isDebugEnabled()) {
					log.debug(String.format("saved trade id : %s", savedTrade.getId()));
				}

			} else if (topic.equals(TOPIC_TRADE_CHNAGED)
					|| topic.equals(TOPIC_TRADE_SUCCESS)) {
				// update trade
				Long tid = extractTid(content);
				Trade taobaoTrade = getTradeInfoFromTaobao(tid);
				// get internal trade

				Map<String, Object> criteria = new HashMap<String, Object>();
				criteria.put("tid", tid);
				List<me.firecloud.ideawork.tradewatcher.model.Trade> existedTrades = tradeDataService.get(criteria);
				
				if(existedTrades.size()>0){
					me.firecloud.ideawork.tradewatcher.model.Trade existedTrade = existedTrades.get(0);
					
					// convert 
					// convert to internal trade
					me.firecloud.ideawork.tradewatcher.model.Trade newTrade = taobaoTrade2InternalTrade(taobaoTrade);
					newTrade.setId(existedTrade.getId());
					
					me.firecloud.ideawork.tradewatcher.model.Trade updatedTrade = tradeDataService.update(newTrade.getId(), newTrade);
					
					if (log.isDebugEnabled()) {
						log.debug(String.format("updated trade id : %s", updatedTrade.getId()));
					}
					
				}else{
					// error
					log.warn(String.format("trade(tid:%d) does not exist.",tid));
				}
				
				//
			} else {
				// unknown topic
				log.warn(String.format("Unknown topic: %s", topic));
			}

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			status.fail();// 消息处理失败回滚，服务端需要重发
		}

	}

	private Long extractTid(String json) throws JsonProcessingException,
			IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(json);
		return node.get("tid").getLongValue();
	}

	private Trade getTradeInfoFromTaobao(Long tid) throws ApiException,
			Exception {
		TradeGetRequest request = new TradeGetRequest();
		request.setTid(tid);
		request.setFields("status,buyer_message,num_iid,payment,pic_path,num,post_fee,tid,has_post_fee,created,sku_id,sku_properties_name,price,orders");

		String sessionKey = AuthenticationSupport.getInstance().getSessionKey();

		TradeGetResponse response = taobaoClient.execute(request, sessionKey);

		if (response.isSuccess()) {
			Trade trade = response.getTrade();
			return trade;
		} else {
			throw new Exception(
					String.format(
							"Get trade info from taobao failed. ErrorCode: %s, message: %s",
							response.getErrorCode(), response.getMsg()));
		}
	}

	private me.firecloud.ideawork.tradewatcher.model.Trade taobaoTrade2InternalTrade(
			Trade taobaoTrade) {
		me.firecloud.ideawork.tradewatcher.model.Trade internalTrade = new me.firecloud.ideawork.tradewatcher.model.Trade();

		internalTrade.setStatus(taobaoTrade.getStatus());
		internalTrade.setBuyerMessage(taobaoTrade.getBuyerMessage());
		internalTrade.setNumIid(taobaoTrade.getNumIid());
		internalTrade.setPayment(taobaoTrade.getPayment());
		internalTrade.setPicPath(taobaoTrade.getPicPath());
		internalTrade.setNum(taobaoTrade.getNum());
		internalTrade.setPostFee(taobaoTrade.getPostFee());
		internalTrade.setTid(taobaoTrade.getTid());
		internalTrade.setHasPostFee(taobaoTrade.getHasPostFee());
		internalTrade.setCreated(taobaoTrade.getCreated());

		// convert orders
		for (Order taobaoOrder : taobaoTrade.getOrders()) {
			internalTrade.getOrders().add(
					taobaoOrder2InternalOrder(taobaoOrder));
		}

		// parse customizationInfo
		String customizationInfoJson = taobaoTrade.getBuyerMessage();

		try {
			// unescape html
			customizationInfoJson = StringEscapeUtils
					.unescapeHtml(customizationInfoJson);

			CustomizationInfo customizationInfo = objectMapper.readValue(
					customizationInfoJson, CustomizationInfo.class);

			internalTrade.setCustomizationInfo(customizationInfo);
		} catch (JsonProcessingException ex) {
			log.warn(
					String.format("Parse customization info failed. %s",
							ex.getMessage()), ex);
		} catch (IOException ex) {
			log.warn(
					String.format("Parse customization info failed. %s",
							ex.getMessage()), ex);
		}catch(NullPointerException ex){
			log.warn(
					String.format("Parse customization info failed. %s",
							ex.getMessage()), ex);
		}

		return internalTrade;
	}

	private me.firecloud.ideawork.tradewatcher.model.Order taobaoOrder2InternalOrder(
			Order taobaoOrder) {
		me.firecloud.ideawork.tradewatcher.model.Order internalOrder = new me.firecloud.ideawork.tradewatcher.model.Order();

		internalOrder.setNumIid(taobaoOrder.getNumIid());
		internalOrder.setNum(taobaoOrder.getNum());
		internalOrder.setPayment(taobaoOrder.getPayment());
		internalOrder.setPicPath(taobaoOrder.getPicPath());
		internalOrder.setSkuId(taobaoOrder.getSkuId());
		internalOrder.setSkuPropertiesName(taobaoOrder.getSkuPropertiesName());
		
		// hard code for test
		if(internalOrder.getSkuPropertiesName()==null){
			internalOrder.setSkuPropertiesName("颜色:红;尺码:L");
			log.info(String.format("Hard code sku properties name: %s",internalOrder.getSkuPropertiesName()));
		}
		
		internalOrder.setPrice(taobaoOrder.getPrice());

		return internalOrder;

	}
}
