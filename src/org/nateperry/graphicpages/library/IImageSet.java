package org.nateperry.graphicpages.library;

public interface IImageSet {

	/**
	 * Gets the default index for this this ImageLot implementation.  This
	 * value is intended for use to initialize values.
	 * 
	 * @return  The default index.
	 */
	public Integer  getDefaultIndex();

	public String   getFileName(Integer index);
	public String   getIndexName();
	public String   getName();
	public Integer  getNewestIndex(Integer latest);
	public Integer  getOldestIndex();
	public String   getPageDescription(Integer index);
	public String   getPageName(Integer index);
	public String   getPageUrl(Integer index);
	public String   getUrl();
	public Integer  parseFileName(String filename);

}
