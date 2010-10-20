package org.nateperry.graphicpages.intents;

import android.content.Context;
import android.content.Intent;

public class JumpToIndexIntent extends Intent {

	private int _index;

	public JumpToIndexIntent(int index) {
		super();
		_index = index;
	}

	//public JumpToIndexIntent() {};

	//public JumpToIndexIntent(GoToPageIntent o) {};

	//public JumpToIndexIntent(String action) {};

	//public JumpToIndexIntent(String action, Uri uri) {};

	public JumpToIndexIntent(Context packageContext, Class<?> cls, int index) {
		super(packageContext, cls);
		_index = index;
	};

	//public JumpToIndexIntent(String action, Uri uri, Context packageContext, Class<?> cls) {};

	public int getIndex() {
		return _index;
	}

}
