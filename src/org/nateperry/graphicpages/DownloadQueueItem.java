package org.nateperry.graphicpages;

public class DownloadQueueItem {
	
	private String _url;
	private ImageLotItem _page;
	
	public DownloadItem(String url, ImageLotItem page) {
		_url = url;
		_page = page;
	}
	
	public DownloadItem(DownloadItem item) {
		_url = new String(item._url);
		_page = (ImageLotItem)item._page.clone();
	}
	
	public String GetUrl() {
		return _url;
	}
	
	public ImageLotItem GetPage() {
		return _page;
	}
	
	@Override
	public Object clone() {
		return new DownloadItem(this);
	}
	
}
