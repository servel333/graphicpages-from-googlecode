package org.nateperry.graphicpages;

import org.nateperry.library.NumericImageLot;
import org.nateperry.library.NumericImageLotIndex;

import android.content.Context;
import android.content.Intent;

public class GoToPageIntent extends Intent {

	protected NumericImageLotIndex _index;

	public GoToPageIntent(NumericImageLotIndex index) {
		super();
		_index = index;
	}

	//public GoToPageIntent() {};

	//public GoToPageIntent(GoToPageIntent o) {};

	//public GoToPageIntent(String action) {};

	//public GoToPageIntent(String action, Uri uri) {};

	public GoToPageIntent(Context packageContext, Class<?> cls, NumericImageLotIndex index) {
		super(packageContext, cls);
		_index = index;
	};

	//public GoToPageIntent(String action, Uri uri, Context packageContext, Class<?> cls) {};

	public NumericImageLot getLot() {
		return _index.getLot();
	}

	public NumericImageLotIndex getIndex() {
		return _index;
	}

}
