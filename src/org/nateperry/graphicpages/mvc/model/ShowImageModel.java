package org.nateperry.graphicpages.mvc.model;

import java.io.File;
import java.io.FileInputStream;

import org.nateperry.graphicpages.U;
import org.nateperry.graphicpages.library.IImageSet;
import org.nateperry.graphicpages.util.CachedProperty;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;



public class ShowImageModel {

	protected IImageSet _set;
	protected final CachedProperty<Integer> _newest;
	protected Integer _index;

	public ShowImageModel(IImageSet set) {
		_set = set;
		_index = _set.getDefaultIndex();
		_newest = new CachedProperty<Integer>(new CPUpdater());
		_newest.setCachedProperty(_set.getDefaultIndex());
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
		_index = U.getValidIndex(_index, _set.getOldestIndex(), _newest.getProperty());
	}

	public String getImageTitle() {
		return _set.getPageName(_index);
	}

	public Bitmap getImage(Activity view) {

		try {

//	     	TextView text = (TextView)findViewById(R.id.ui_info_TextView);
//	     	text.setText(I.setManager.getPageName());

			File file = null;
			File iFile = U.getInternalFile(view, _set.getFileName(_index));
			File xFile = U.getExternalFile(_set.getFileName(_index));

			if (null != iFile && iFile.exists()) {
				file = iFile;
			} else if (null != xFile && xFile.exists()) {
				file = xFile;
			}

			Bitmap image = null;

			if (null != file) {

				FileInputStream in = view.openFileInput(file.getAbsolutePath());
				image = BitmapFactory.decodeStream(in);

    			if (null == image) {
    				file.delete();
    			}

			}

//     		ImageView im = (ImageView)findViewById(R.id.ui_image_ImageView);
//     		im.setImageBitmap(image);

//     		_touchListener.ResetTouch();

			return image;

		} catch (Exception e) {
			e.printStackTrace();
			Log.w("downloading", e);
		}

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
