package org.nateperry.graphicpages.library;

import org.nateperry.graphicpages.library.ImageLot;

public abstract class ImageLotIndex<IndexType> {

	protected ImageLot<IndexType> _lot;
	protected IndexType _index;

	public ImageLotIndex(ImageLot<IndexType> lot) {

		_lot = lot;
		_index = _lot.getDefaultIndex();
	}

	public ImageLotIndex(ImageLot<IndexType> lot, IndexType index) {

		_lot = lot;
		_index = index;
	}

	public ImageLot<IndexType> getLot() {
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
