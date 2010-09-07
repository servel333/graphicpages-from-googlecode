package org.nateperry.library;

public abstract class NumericImageLotIndex extends ImageLotIndex<Integer> {

	public NumericImageLotIndex(NumericImageLot lot, Integer index) {
		super(lot, index);
	}

	public void change(Integer amount) {
		_index += amount;
	}

}
