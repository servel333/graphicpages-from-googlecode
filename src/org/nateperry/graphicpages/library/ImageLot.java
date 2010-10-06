package org.nateperry.graphicpages.library;

import org.nateperry.graphicpages.single.questionablecontent.R;
import org.nateperry.graphicpages.util.CachedProperty;
import org.nateperry.graphicpages.library.ImageLot;
import org.nateperry.graphicpages.library.ImageLotIndex;

public abstract class ImageLot<IndexType> {

	// content://GraphicPage/<ComicName>/<Index>
	public static final String URI_NAMESPACE = "GraphicPage";
	public static final int TIMEOUT_MINS = 30;

	protected CachedProperty<IndexType> _newestIndex;

	public ImageLot() {
		_newestIndex = new CachedProperty<IndexType>(_indexUpdater);
	}

	public ImageLot(ImageLot<IndexType> lot) {
		_newestIndex = new CachedProperty<IndexType>(lot._newestIndex);
	}

	protected CachedProperty.Updater<IndexType> _indexUpdater = 
			new CachedProperty.Updater<IndexType>() {

		public IndexType updateProperty() {
			return onGetNewestIndex();
		}
	}; 

	public final IndexType getDefaultIndex() {
		return onGetDefaultIndex();
	}

	public final IndexType getNewestIndex() {

		return _newestIndex.getProperty();
	}

	public final IndexType getUpdatedNewestIndex() {

		return _newestIndex.getUpdatedProperty();
	}

	public final IndexType getOldestIndex() {
		return onGetOldestIndex();
	}

	public final IndexType changeIndex(IndexType index, IndexType amount) {
		return onChangeIndex(index, amount);
	}

	public final IndexType changeIndex(ImageLotIndex<IndexType> index, IndexType amount) {
		return onChangeIndex(index.get(), amount);
	}

	public final String getName() {
		return onGetName();
	}

	public final String getIndexName() {
		return onGetIndexName();
	}

	public final String getPageName(IndexType index) {
		return onGetPageName(index);
	}

	public final String getPageName(ImageLotIndex<IndexType> index) {
		return onGetPageName(index.get());
	}

	public final String getPageDescription(IndexType index) {
		return onGetPageDescription(index);
	}

	public final String getPageDescription(ImageLotIndex<IndexType> index) {
		return onGetPageDescription(index.get());
	}

	public final String getUrl() {
		return onGetUrl();
	}

	public final String getPageUrl(IndexType index) {
		return onGetPageUrl(index);
	}

	public final String getPageUrl(ImageLotIndex<IndexType> index) {
		return onGetPageUrl(index.get());
	}

	public final String getFileName(IndexType index) {
		return onGetFileName(index);
	}

	public final String getFileName(ImageLotIndex<IndexType> index) {
		return onGetFileName(index.get());
	}

	public final IndexType parseFileName(String filename) {
		return onParseFileName(filename);
	}

	protected String onGetFullName() {
		return R.string.app_full_name + "." + getIndexName();
	}

	public final String getFullName() {
		return onGetFullName();
	}

	@Override public abstract Object clone();

	/**
	 * Evaluates value and determines if it is a valid index for this
	 * implementation of ImageLot.
	 * 
	 * @param value  The value to evaluate.
	 * @return  A value indicating if value is valid.
	 */
	public abstract boolean isValidIndex(IndexType value);

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
	public abstract IndexType getValidIndex(IndexType value);
	
	/**
	 * Creates a new instance of ImageLotIndex for this implementation of
	 * ImageLot.
	 * 
	 * @return  A new instance of ImageLotIndex.
	 */
	protected abstract ImageLotIndex<IndexType> newIndex();

	/**
	 * Gets the default index for this this ImageLot implementation.  This
	 * value is intended for use to initialize values.
	 * 
	 * @return  The default index.
	 */
	protected abstract IndexType onGetDefaultIndex();
	protected abstract String    onGetFileName(IndexType index);
	protected abstract String    onGetIndexName();
	protected abstract String    onGetName();
	protected abstract IndexType onGetNewestIndex();
	protected abstract IndexType onGetOldestIndex();
	protected abstract String    onGetPageDescription(IndexType index);
	protected abstract String    onGetPageName(IndexType index);
	protected abstract String    onGetPageUrl(IndexType index);
	protected abstract String    onGetUrl();
	protected abstract IndexType onChangeIndex(IndexType index, IndexType count);
	protected abstract IndexType onParseFileName(String filename);

}
