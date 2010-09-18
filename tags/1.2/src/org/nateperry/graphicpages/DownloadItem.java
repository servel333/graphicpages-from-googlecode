package org.nateperry.graphicpages;

public class DownloadItem {
	
	private String _url;
	private WebComicPage _page;
	
	public DownloadItem(String url, WebComicPage page) {
		_url = url;
		_page = page;
	}
	
	public DownloadItem(DownloadItem item) {
		_url = new String(item._url);
		_page = (WebComicPage)item._page.clone();
	}
	
	public String GetUrl() {
		return _url;
	}
	
	public WebComicPage GetPage() {
		return _page;
	}
	
	@Override
	public Object clone() {
		return new DownloadItem(this);
	}
	
}
