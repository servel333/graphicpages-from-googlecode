package org.nateperry.graphicpages.library;

import org.nateperry.graphicpages.library.ImageSet;

public abstract class ImageSetIndex<IndexType> {

	protected ImageSet<IndexType> _lot;
	protected IndexType _index;

	public ImageSetIndex(ImageSet<IndexType> lot) {

		_lot = lot;
		_index = _lot.getDefaultIndex();
	}

	public ImageSetIndex(ImageSet<IndexType> lot, IndexType index) {

		_lot = lot;
		_index = index;
	}

	public ImageSet<IndexType> getLot() {
		return _lot;
	}

	public IndexType get() {
		return _index;
	}

	public void set(IndexType index) {
		_index = index;
		_index = _lot.getValidIndex(_index);
	}

	public void change(IndexType amount) {
		_index = _lot.changeIndex(_index, amount);
	}

	public void setToOldest() {
		_index = _lot.getOldestIndex();
	}

	public void setToNewest() {
		_index = _lot.getNewestIndex();
	}

}
