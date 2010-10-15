package org.nateperry.graphicpages.mvc.model;

import org.nateperry.graphicpages.library.NumericImageSet;
import org.nateperry.graphicpages.library.NumericImageSetIndex;

import android.graphics.Bitmap;



public class ShowImageModel {

	protected NumericImageSet _set;
	protected NumericImageSetIndex _index;

	public ShowImageModel(NumericImageSet lot) {
		_set = lot;
	}

	public void changeToNewerIndex() {
		_set;
	}

	public void changeToNewestIndex() {
		_set.();
	}

	public void changeToOlderIndex () {
		_set.();
	}

	public void changeToOldestIndex() {
		_set.();
	}

	public String getImageTitle() {
		return _set.getPageName(_index);
	}

	public Bitmap getImage() {
		return null;
	}

	public boolean isImageAvailable() {
		return true;
	}

}
