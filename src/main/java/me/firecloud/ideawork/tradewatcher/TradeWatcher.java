package me.firecloud.ideawork.tradewatcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.internal.tmc.Message;
import com.taobao.api.internal.tmc.MessageHandler;
import com.taobao.api.internal.tmc.MessageStatus;
import com.taobao.api.internal.tmc.TmcClient;
import com.taobao.api.request.TmcUserPermitRequest;
import com.taobao.api.response.TmcUserPermitResponse;
import com.taobao.top.link.LinkException;

/**
 * Hello world!
 * 
 */
public class TradeWatcher {
	private static final Log log = LogFactory.getLog(TradeWatcher.class);

	private static final String APP_KEY = "1023114454";
	private static final String APP_SECRET = "sandbox97457d42058adcf70031c4a14";
	
	private static final String SESSION_KEY="6101b27c26740932c172880f2941699537916b3de7316f73651882214";
	
	
	/********
	 * 
	 * @param args
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {
		try {
			// TmcClient client = new TmcClient("app_key",
			// "app_secret","default");//关于default消息分组参见：消息分组介绍
			// 沙箱测试消息服务：
			
			String tmcServiceUrl = AuthenticationSupport.getInstance().getProperty(AuthenticationSupport.PROPERTY_TOP_TMC_SERVICE_URL);
			TmcClient client = new TmcClient(tmcServiceUrl,
					APP_KEY, APP_SECRET, "default");
			
			MessageHandler tradeMessageHandler = new TradeMessageHandler();
			
			client.setMessageHandler(tradeMessageHandler);
			client.connect();
		} catch (LinkException ex) {
			log.error(ex.getMessage(), ex);
		}
		
		/*
		TaobaoClient taobaoClient = new DefaultTaobaoClient("http://gw.api.tbsandbox.com/router/rest", APP_KEY, APP_SECRET);
		
		TmcUserPermitRequest request = new TmcUserPermitRequest();
		TmcUserPermitResponse response = taobaoClient.execute(request,SESSION_KEY);
		
		if(response.isSuccess()){
			log.info("User permit success.");
		}else{
			log.warn(String.format("User permit fail. Error Code: %s, message: %s",response.getErrorCode(),response.getMsg()));
		}
		*/
	}
}
