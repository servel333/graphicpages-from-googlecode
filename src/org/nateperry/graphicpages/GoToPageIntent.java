package org.nateperry.graphicpages;

import org.nateperry.library.NumericImageLot;

import android.content.Context;
import android.content.Intent;

public class GoToPageIntent extends Intent {

	protected NumericImageLot _lot;
	protected Integer _index;

	public GoToPageIntent(NumericImageLot lot, Integer index) {
		super();
		_lot = lot;
		_index = index;
	}

	//public GoToPageIntent() {};

	//public GoToPageIntent(GoToPageIntent o) {};

	//public GoToPageIntent(String action) {};

	//public GoToPageIntent(String action, Uri uri) {};

	public GoToPageIntent(Context packageContext, Class<?> cls, NumericImageLot lot, Integer index) {
		super(packageContext, cls);
		_index = index;
	};

	//public GoToPageIntent(String action, Uri uri, Context packageContext, Class<?> cls) {};

	public NumericImageLot getLot() {
		return _lot;
	}

	public Integer getIndex() {
		return _index;
	}

}
