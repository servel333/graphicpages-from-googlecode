package org.nateperry.graphicpages.library;

import org.nateperry.graphicpages.library.ImageSet;
import org.nateperry.graphicpages.library.NumericImageSet;
import org.nateperry.graphicpages.library.NumericImageSetIndex;

public abstract class NumericImageSet extends ImageSet<Integer> {

	public NumericImageSet() {
		super();
	}

	public NumericImageSet(NumericImageSet lot) {
		super(lot);
	}

	@Override
	public NumericImageSetIndex newIndex() {
		return new NumericImageSetIndex(this, getDefaultIndex());
	}

	@Override
	public boolean isValidIndex(Integer value) {
		return (getOldestIndex() <= value && value <= getNewestIndex());
	}

	@Override
	public Integer getValidIndex(Integer value) {

		if (getOldestIndex() > value) {
			value = getOldestIndex();
		} else if (value > getNewestIndex()) {
			value = getNewestIndex();
		}

		return value;
	}

	@Override
	protected Integer onChangeIndex(Integer index, Integer count) {

		index += count;
		index = getValidIndex(index);

		return index;
	}

}
