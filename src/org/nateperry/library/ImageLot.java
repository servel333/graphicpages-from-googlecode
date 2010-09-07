package org.nateperry.library;

import org.nateperry.CachedProperty;
import org.nateperry.CachedPropertyUpdater;
import org.nateperry.graphicpages.R;

public abstract class ImageLot<IndexType> { //extends ContentProvider {

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

	protected CachedPropertyUpdater<IndexType> _indexUpdater = 
			new CachedPropertyUpdater<IndexType>() {

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

	public final IndexType moveIndex(IndexType index, int count) {
		return onMoveIndex(index, count);
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

	public final String getPageDescription(IndexType index) {
		return onGetPageDescription(index);
	}

	public final String getUrl() {
		return onGetUrl();
	}

	public final String getPageUrl(IndexType index) {
		return onGetPageUrl(index);
	}

	public final String getFileName(IndexType index) {
		return onGetFileName(index);
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
	protected abstract IndexType onMoveIndex(IndexType index, int count);
	protected abstract IndexType onParseFileName(String filename);

}
