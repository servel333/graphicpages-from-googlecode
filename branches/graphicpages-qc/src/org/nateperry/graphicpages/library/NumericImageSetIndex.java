package org.nateperry.graphicpages.library;

import java.util.Comparator;

import org.nateperry.graphicpages.library.ImageSetIndex;
import org.nateperry.graphicpages.library.NumericImageSet;

public class NumericImageSetIndex extends ImageSetIndex<Integer> {

	public NumericImageSetIndex(NumericImageSet lot, Integer index) {
		super(lot, index);
	}

	public void change(Integer amount) {
		_index += amount;
	}

	public NumericImageSet getLot() {
		return (NumericImageSet)_lot;
	}

	public class comparer implements Comparator<NumericImageSetIndex> {

		public int compare(NumericImageSetIndex left, NumericImageSetIndex right) {
			return new Integer(left.get()).compareTo(right.get());
		}

	}

}
