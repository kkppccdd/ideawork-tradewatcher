/**
 * 
 */
package me.firecloud.ideawork.tradewatcher.model;

/**
 * @author kkppccdd
 *
 */
public class Order {
	private Long numIid;
	private Long num;
	private String payment;
	private String picPath;
	private String skuId;
	private String skuPropertiesName;
	private String price;

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
	 * @return the skuId
	 */
	public String getSkuId() {
		return skuId;
	}
	/**
	 * @param skuId the skuId to set
	 */
	public void setSkuId(String skuId) {
		this.skuId = skuId;
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
	 * @return the skuPropertiesName
	 */
	public String getSkuPropertiesName() {
		return skuPropertiesName;
	}
	/**
	 * @param skuPropertiesName the skuPropertiesName to set
	 */
	public void setSkuPropertiesName(String skuPropertiesName) {
		this.skuPropertiesName = skuPropertiesName;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	
	
}
