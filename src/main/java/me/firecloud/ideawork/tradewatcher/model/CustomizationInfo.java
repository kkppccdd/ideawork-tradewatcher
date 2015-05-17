/**
 * 
 */
package me.firecloud.ideawork.tradewatcher.model;

/**
 * @author kkppccdd
 * 
 */
public class CustomizationInfo {
	private String creatorId;
	private ImageInfo print;
	private ImageInfo preview;

	/**
	 * @return the creatorId
	 */
	public String getCreatorId() {
		return creatorId;
	}

	/**
	 * @param creatorId
	 *            the creatorId to set
	 */
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * @return the print
	 */
	public ImageInfo getPrint() {
		return print;
	}

	/**
	 * @param print
	 *            the print to set
	 */
	public void setPrint(ImageInfo print) {
		this.print = print;
	}

	/**
	 * @return the preview
	 */
	public ImageInfo getPreview() {
		return preview;
	}

	/**
	 * @param preview
	 *            the preview to set
	 */
	public void setPreview(ImageInfo preview) {
		this.preview = preview;
	}

}
