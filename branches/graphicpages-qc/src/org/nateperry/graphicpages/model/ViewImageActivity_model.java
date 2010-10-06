package org.nateperry.graphicpages.model;

import org.nateperry.graphicpages.library.NumericImageLot;
import org.nateperry.graphicpages.library.NumericImageLotIndex;
import org.nateperry.graphicpages.single.questionablecontent.R;

import android.view.View;
import android.view.View.OnClickListener;

public class ViewImageActivity_model {

	private PageTouchListener _touchListener;
	private UpdateTask _updateTask;
	protected NumericImageLot _lot;
	protected NumericImageLotIndex _index;

	public static final String KEY_LAST_VIEWED_PAGE = "last_viewed_page";
	static final int DIALOG_JUMP_TO_ID = 0;

	public enum Action {
		OLDEST, OLDER, UPDATE, NEWER, NEWEST
	}

	private enum UpdateState {
		UPDATED, DOWNLOADING
	}

	public ViewImageActivity_model() {
		
	}

	public OnClickListener getOnClickListener(Action action) {

		final Action _action = action;

		return new OnClickListener() {
			public void onClick(View v) {
				doAction(_action);
			}
		};
	}

	private void doAction(Action action) {

		switch (action) {
		case OLDEST:
		case OLDER:
		case UPDATE:
		case NEWER:
		case NEWEST:
		}
	}

}
