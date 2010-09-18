package org.nateperry.graphicpages.single.questionablecontent;

import android.content.Context;
import android.content.Intent;

public class GoToPageIntent extends Intent {

	private int _index;
	
	public GoToPageIntent(int index) {
		super();
		_index = index;
	}
	
	//public GoToPageIntent() {};
	
	//public GoToPageIntent(GoToPageIntent o) {};
	
	//public GoToPageIntent(String action) {};
	
	//public GoToPageIntent(String action, Uri uri) {};
	
	public GoToPageIntent(Context packageContext, Class<?> cls, int index) {
		super(packageContext, cls);
		_index = index;
	};
	
	//public GoToPageIntent(String action, Uri uri, Context packageContext, Class<?> cls) {};
	
	public int GetIndex() {
		return _index;
	}

}
