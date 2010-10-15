package org.nateperry.graphicpages.library;

public interface IImageSet<T> {

	public IImageSet<T> clone();

	/**
	 * Evaluates value and determines if it is a valid index for this
	 * implementation of ImageLot.
	 * 
	 * @param value  The value to evaluate.
	 * @return  A value indicating if value is valid.
	 */
	public boolean isValidIndex(IImageSetIndex<T> value);

	/**
	 * If value is a valid index, value is returned.  If value is not a valid
	 * index, the closest valid index to value is returned.
	 * 
	 * The definition of 'closest' depends on IndexType and the implemented
	 * ImageLot.
	 * 
	 * @param value  The value to evaluate.
	 * @return  value, or the closest valid value to value.
	 */
	public  T getValidIndex(T value);
	
	/**
	 * Creates a new instance of ImageLotIndex for this implementation of
	 * ImageLot.
	 * 
	 * @return  A new instance of ImageLotIndex.
	 */
	public IImageSetIndex<T> newIndex();

	/**
	 * Gets the default index for this this ImageLot implementation.  This
	 * value is intended for use to initialize values.
	 * 
	 * @return  The default index.
	 */
	public T        getDefaultIndex();

	public String   getFileName(T index);
	public String   getIndexName();
	public String   getName();
	public T        getNewestIndex();
	public T        getOldestIndex();
	public String   getPageDescription(T index);
	public String   getPageName(T index);
	public String   getPageUrl(T index);
	public String   getUrl();
	public T        changeIndex(T index, T count);
	public T        parseFileName(String filename);

}
