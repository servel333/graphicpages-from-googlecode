package org.nateperry.graphicpages.mvc.model;

import org.nateperry.graphicpages.U;
import org.nateperry.graphicpages.library.IImageSet;
import org.nateperry.graphicpages.util.CachedProperty;

import android.graphics.Bitmap;



public class ShowImageModel {

	protected IImageSet _set;
	protected final CachedProperty<Integer> _newest;
	protected Integer _index;

	public ShowImageModel(IImageSet set) {
		_set = set;
		_newest = new CachedProperty<Integer>(new CPUpdater());
	}

	public void changeToNewerIndex() {
		_index += 1;
		validateIndex();
	}

	public void changeToNewestIndex() {
		_index = _newest.getProperty();
	}

	public void changeToOlderIndex () {
		_index -= 1;
		validateIndex();
	}

	public void changeToOldestIndex() {
		_index = _set.getOldestIndex();
	}

	public void validateIndex() {
		_index = U.getValidIndex(_index, _set.getOldestIndex(), _newest.getCachedProperty());
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

	protected class CPUpdater implements CachedProperty.IUpdater<Integer> {

		public Integer updateProperty() {
			return _set.getNewestIndex(_newest.getCachedProperty());
		}
		
	}

}
