//package org.nateperry.graphicpages;
//
//import org.nateperry.library.ImageLot;
//
//public class DownloadQueueItem {
//	
//	private String _url;
//	private ImageLotItem _item;
//	
//	public DownloadQueueItem(String url, ImageLotItem item) {
//		_url = url;
//		_item = item;
//	}
//	
//	public DownloadQueueItem(DownloadQueueItem item) {
//		_url = new String(item._url);
//		_item = (ImageLotItem)item._item.clone();
//	}
//	
//	public String GetUrl() {
//		return _url;
//	}
//	
//	public ImageLotItem GetPage() {
//		return _item;
//	}
//	
//	public ImageLot getLot() {
//		return _item.getLot();
//	}
//	
//	@Override
//	public Object clone() {
//		return new DownloadQueueItem(this);
//	}
//	
//}
