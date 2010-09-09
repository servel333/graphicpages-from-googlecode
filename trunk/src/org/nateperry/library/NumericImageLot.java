package org.nateperry.library;

public abstract class NumericImageLot extends ImageLot<Integer> {

	public NumericImageLot() {
		super();
	}

	public NumericImageLot(NumericImageLot lot) {
		super(lot);
	}

	@Override
	public NumericImageLotIndex newIndex() {
		return new NumericImageLotIndex(this, getDefaultIndex());
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
