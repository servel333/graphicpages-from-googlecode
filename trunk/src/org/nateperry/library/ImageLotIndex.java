package org.nateperry.library;

public abstract class ImageLotIndex<indexType> {

	protected ImageLot<indexType> _lot;
	protected indexType _index;

	public ImageLotIndex(ImageLot<indexType> lot) {

		_lot = lot;
		_index = _lot.getDefaultIndex();
	}

	public ImageLotIndex(ImageLot<indexType> lot, indexType index) {

		_lot = lot;
		_index = index;
	}

	public ImageLot<indexType> getLot() {
		return _lot;
	}

	public indexType get() {
		return _index;
	}

	public void set(indexType index) {
		_index = index;
		_index = _lot.getValidIndex(_index);
	}

	public void change(indexType amount) {
		_index = _lot.changeIndex(_index, amount);
	}

	public void setToOldest() {
		_index = _lot.getOldestIndex();
	}

	public void setToNewest() {
		_index = _lot.getNewestIndex();
	}

}
