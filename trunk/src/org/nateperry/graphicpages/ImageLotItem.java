//package org.nateperry.graphicpages;
//
//import java.io.File;
//
//import org.nateperry.library.ImageLot;
//
//import android.content.ContextWrapper;
//import android.os.Environment;
//
//public class ImageLotItem {
//
//	protected ImageLot<?> _lot;
//	protected String _filename;
//	protected int _id;
//	protected boolean _isExternal;
//	protected boolean _exists;
//
//	public ImageLotItem(ImageLot<?> lot) {
//		_lot = lot;
//	}
//
//	public ImageLotItem(ImageLot<?> lot, int id, String filename) {
//		
//		_lot = lot;
//		_id = id;
//		_filename = filename;
//	}
//
//	public ImageLotItem(ImageLotItem item) {
//		
//		_lot = (ImageLot<?>)item._lot.clone();
//		_id = item._id;
//		_filename = new String(item._filename);
//	}
//
//	public String getFileName() {
//		return _filename;
//	}
//
//	public int getId() {
//		return _id;
//	}
//
//	public ImageLot<?> getLot() {
//		return _lot;
//	}
//
//	public String toString() {
//		return _filename;
//	}
//
//	@Override
//	public Object clone() {
//		return new ImageLotItem(this);
//	}
//
//	public boolean isExternal() {
//		return _isExternal;
//	}
//
//	public boolean exists() {
//		return _exists;
//	}
//
//	public static ImageLotItem getFrom(ContextWrapper context, ImageLot<?> lot, int index) {
//
//		ImageLotItem item = new ImageLotItem(lot);
//
//		File dir = context.getFilesDir();
//		File file = new File(dir, lot.getFileName(index));
//		File xDir = null;
//		File xFile = null;
//
//    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//
//	    	//File myDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES); // Android API 8 only
//
//	    	File xRootDir = Environment.getExternalStorageDirectory();
//	    	xDir = new File(xRootDir, Globals.EXTERNAL_DATA_FOLDER);
//    		xFile = new File(xDir, lot.getFileName(index));
//
//    	}
//
//		if (file.exists()) {
//
//			item._isExternal = false;
//			item._exists = true;
//
//		} else if (xFile != null && xFile.exists()) {
//			
//			item._isExternal = true;
//			item._exists = true;
//			
//		} else {
//			
//			item._isExternal = false;
//			item._exists = false;
//			
//		}
//		
//		return item;
//	}
//	
//}
