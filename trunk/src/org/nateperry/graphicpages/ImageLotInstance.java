//package org.nateperry.graphicpages;
//
//public class ImageLotInstance {
//	
//	private volatile static ImageLot _lot;
//	private volatile static int _index = -1;
//	
//	public static ImageLot getLot() {
//		if (_lot == null) {
//			_lot = new QuestionableContentImageLot();
//		}
//		return _lot;
//	}
//	
//	public static void setLot(ImageLot lot) {
//		_lot = lot;
//	}
//	
//	public static int getIndex() {
//		if (_index == -1) {
//			setNewestId();
//		}
//		return _index;
//	}
//	
//	public static void setIndex(int index) {
//		_index = index;
//	}
//	
//	public static void setNewestId() {
//		_index = getLot().GetNewestId();
//	}
//	
//	public static void setNewestId(boolean forceUpdate) {
//		_index = getLot().GetNewestId(forceUpdate);
//	}
//	
//	public static void setOldestId() {
//		_index = getLot().GetOldestId();
//	}
//	
//	public static void setOlderId() {
//		_index = getLot().GetOlderId(_index);
//	}
//	
//	public static void setNewerId() {
//		_index = getLot().GetNewerId(_index);
//	}
//	
//	public static String getPageName() {
//		return getLot().GetPageName(_index);
//	}
//	
//	public static String getPageDescription() {
//		return getLot().GetPageDescription(_index);
//	}
//	
//	public static String getPageUrl() {
//		return getLot().GetPageUrl(_index);
//	}
//	
//	public static String getFileName() {
//		return getLot().GetFileName(_index);
//	}
//	
//}
