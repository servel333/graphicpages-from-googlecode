package org.nateperry.library;

public class NumericImageLotIndex extends ImageLotIndex<Integer> {

	public NumericImageLotIndex(NumericImageLot lot, Integer index) {
		super(lot, index);
	}

	public void change(Integer amount) {
		_index += amount;
	}

	public NumericImageLot getLot() {
		return (NumericImageLot)_lot;
	}

}
