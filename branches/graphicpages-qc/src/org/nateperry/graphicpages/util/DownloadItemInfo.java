package org.nateperry.graphicpages.util;

import java.io.File;
import java.net.URL;

public class DownloadItemInfo {

    private final URL _url;
	protected final File _file;
	protected volatile int _downloaded;
	protected volatile int _size;

    public DownloadItemInfo(
    		URL url,
    		File file,
    		int size) {

    	_url = url;
    	_file = file;
		_size = -1;
		_downloaded = 0;

    }

    public URL getUrl() {
    	return _url;
    }

    public int getSize() {
    	return _size;
    }

    public void setSize(int size) {
    	_size = size;
    }

    public int getDownloaded() {
    	return _downloaded;
    }

    public void setDownloaded(int downloaded) {
    	_downloaded = downloaded;
    }

    public void addToDownloaded(int length) {
    	_downloaded += length;
    }

    public File getFile() {
    	return _file;
    }

    public float getPercent() {
        return ((float) _downloaded / _size) * 100;
    }

}
