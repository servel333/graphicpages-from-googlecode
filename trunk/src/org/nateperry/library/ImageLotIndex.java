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

	//protected abstract indexType getDefaultIndex();

	public ImageLot<indexType> getLot() {
		return _lot;
	}

	public indexType getIndex() {
		return _index;
	}

	public void setIndex(indexType index) {
		_index = index;
	}

}
