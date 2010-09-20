package org.nateperry.graphicpages;

import java.net.MalformedURLException;
import java.net.URL;

public class DownloadItem {
	
	private URL _url;
	private WebComicPage _page;
	
	public DownloadItem(URL url, WebComicPage page) {
		_url = url;
		_page = page;
	}

	public DownloadItem(String url) throws MalformedURLException {
		_url = new URL(url);
	}
	
	public URL GetUrl() {
		return _url;
	}
	
	public WebComicPage GetPage() {
		return _page;
	}
	
}
