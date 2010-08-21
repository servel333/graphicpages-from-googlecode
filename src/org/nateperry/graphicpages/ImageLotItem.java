package org.nateperry.graphicpages;

import java.io.File;
import java.io.FileOutputStream;

import android.content.ContextWrapper;
import android.os.Environment;

public class ImageLotItem {
	
	private ImageLot _lot;
	private String _filename;
	private int _id;
	private boolean _isExternal;
	private boolean _exists;
	
	public ImageLotItem(ImageLot lot) {
		_lot = lot;
	}
	
	public ImageLotItem(ImageLot lot, int id, String filename) {
		
		_lot = lot;
		_id = id;
		_filename = filename;
	}
	
	public ImageLotItem(ImageLotItem item) {
		
		_lot = (ImageLot)item._lot.clone();
		_id = item._id;
		_filename = new String(item._filename);
	}
	
	public String GetFileName() {
		return _filename;
	}
	
	public int GetId() {
		return _id;
	}
	
	public ImageLot GetComic() {
		return _lot;
	}
	
	public String toString() {
		return _filename;
	}
	
	@Override
	public Object clone() {
		return new ImageLotItem(this);
	}
	
	public static ImageLotItem getFrom(ContextWrapper context, ImageLot lot, int index) {
		
		ImageLotItem item = new ImageLotItem(lot);
		
		File dir = context.getFilesDir();
		File file = new File(dir, lot.GetFileName(ImageLotInstance.getIndex()));
		File xDir = null;
		File xFile = null;
		
    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
    		
	    	//File myDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES); // Android API 8 only
    		
	    	File xRootDir = Environment.getExternalStorageDirectory();
	    	xDir = new File(xRootDir, Globals.EXTERNAL_DATA_FOLDER);
    		xFile = new File(xDir, ImageLotInstance.getLot().GetFileName(ImageLotInstance.GetIndex()));
    		
    	}
		
		if (file.exists()) {
			
			item._isExternal = false;
			item._exists = true;
			
		} else if (xFile != null && xFile.exists()) {
			
			item._isExternal = true;
			item._exists = true;
			
		} else {
			
			item._isExternal = false;
			item._exists = false;
			
		}
		
	}
	
}
