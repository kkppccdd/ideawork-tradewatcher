/**
 * 
 */
package me.firecloud.ideawork.tradewatcher.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author kkppccdd
 *
 */
public class Trade {
	private String id;
	private String status;
	private String buyerMessage;
	private Long numIid;
	private String payment;
	private String picPath;
	private Long num;
	private String postFee;
	private Long tid;
	private Boolean hasPostFee;
	private Date created;
	private CustomizationInfo customizationInfo;
	private List<Order> orders = new ArrayList<Order>();
	/**
	 * @return the id
	 */
	@JsonProperty("_id")
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	@JsonProperty("_id")
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the buyerMessage
	 */
	public String getBuyerMessage() {
		return buyerMessage;
	}
	/**
	 * @param buyerMessage the buyerMessage to set
	 */
	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}

	/**
	 * @return the numIid
	 */
	public Long getNumIid() {
		return numIid;
	}
	/**
	 * @param numIid the numIid to set
	 */
	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}
	/**
	 * @return the payment
	 */
	public String getPayment() {
		return payment;
	}
	/**
	 * @param payment the payment to set
	 */
	public void setPayment(String payment) {
		this.payment = payment;
	}
	/**
	 * @return the picPath
	 */
	public String getPicPath() {
		return picPath;
	}
	/**
	 * @param picPath the picPath to set
	 */
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	/**
	 * @return the num
	 */
	public Long getNum() {
		return num;
	}
	/**
	 * @param num the num to set
	 */
	public void setNum(Long num) {
		this.num = num;
	}
	/**
	 * @return the postFee
	 */
	public String getPostFee() {
		return postFee;
	}
	/**
	 * @param postFee the postFee to set
	 */
	public void setPostFee(String postFee) {
		this.postFee = postFee;
	}

	/**
	 * @return the tid
	 */
	public Long getTid() {
		return tid;
	}
	/**
	 * @param tid the tid to set
	 */
	public void setTid(Long tid) {
		this.tid = tid;
	}
	/**
	 * @return the hasPostFree
	 */
	public Boolean getHasPostFee() {
		return hasPostFee;
	}
	/**
	 * @param hasPostFree the hasPostFree to set
	 */
	public void setHasPostFee(Boolean hasPostFee) {
		this.hasPostFee = hasPostFee;
	}
	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}
	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}
	/**
	 * @return the customizationInfo
	 */
	public CustomizationInfo getCustomizationInfo() {
		return customizationInfo;
	}
	/**
	 * @param customizationInfo the customizationInfo to set
	 */
	public void setCustomizationInfo(CustomizationInfo customizationInfo) {
		this.customizationInfo = customizationInfo;
	}
	/**
	 * @return the orders
	 */
	public List<Order> getOrders() {
		return orders;
	}
	/**
	 * @param orders the orders to set
	 */
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	
}
