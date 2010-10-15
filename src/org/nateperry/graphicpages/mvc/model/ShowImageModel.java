package org.nateperry.graphicpages.mvc.model;

import org.nateperry.graphicpages.library.NumericImageLot;

import android.graphics.Bitmap;

public class ShowImageModel {

	protected NumericImageLot _lot;

	public ShowImageModel(NumericImageLot lot) {
		_lot = lot;
	}

	public void changeToNewerIndex() {
		_lot.();
	}

	public void changeToNewestIndex() {
		_lot.();
	}

	public void changeToOlderIndex () {
		_lot.();
	}

	public void changeToOldestIndex() {
		_lot.();
	}

	public String getImageTitle() {
		return ;
	}

	public Bitmap getImage() {
		return ;
	}

	public boolean isImageAvailable() {
		return ;
	}

}
