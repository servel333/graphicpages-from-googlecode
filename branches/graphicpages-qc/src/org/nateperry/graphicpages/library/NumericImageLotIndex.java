package org.nateperry.graphicpages.library;

import java.util.Comparator;

import org.nateperry.graphicpages.library.ImageLotIndex;
import org.nateperry.graphicpages.library.NumericImageLot;

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

	public class comparer implements Comparator<NumericImageLotIndex> {

		public int compare(NumericImageLotIndex left, NumericImageLotIndex right) {
			return new Integer(left.get()).compareTo(right.get());
		}

	}

}
