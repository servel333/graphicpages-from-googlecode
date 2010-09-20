package org.nateperry.graphicpages;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//http://www.java-tips.org/java-se-tips/javax.swing/how-to-create-a-download-manager-in-java.html
public class Downloader implements Runnable {

	private static final int MAX_BUFFER_SIZE = 1024;

	public static final int STATE_DOWNLOADING = 0;
    public static final int STATE_PAUSED = 1;
    public static final int STATE_COMPLETE = 2;
    public static final int STATE_CANCELLED = 3;
    public static final int STATE_ERROR = 4;

    private URL _url;
    private int _state;
    private int _size;
    private int _got;

    public Downloader(String url) throws MalformedURLException {
		_url = new URL(url);
		_size = -1;
		_state = STATE_DOWNLOADING;
		_got = 0;
	}

    public Downloader(URL url) {
    	_url = url;
		_size = -1;
		_state = STATE_DOWNLOADING;
		_got = 0;
	}

	public void run() {
		//RandomAccessFile file = null;
        InputStream stream = null;

		try {
			HttpURLConnection connection =
                (HttpURLConnection) _url.openConnection();

	        connection.setRequestProperty("Range", "bytes=" + _got + "-");
        
	        connection.connect();
	        
	        if (connection.getResponseCode() / 100 != 2) {
	            //error();
	        }
	        
	        int contentLength = connection.getContentLength();
	        if (contentLength < 1) {
	            //error();
	        }
	        
	        if (_size == -1) {
	            _size = contentLength;
	            //stateChanged();
	        }
	        
	        //file = new RandomAccessFile(getFileName(_url), "rw");
	        //file.seek(_got);
	        
	        stream = connection.getInputStream();
	        while (_state == STATE_DOWNLOADING) {

	            byte buffer[];
	            if (_size - _got > MAX_BUFFER_SIZE) {
	                buffer = new byte[MAX_BUFFER_SIZE];
	            } else {
	                buffer = new byte[_size - _got];
	            }
	            
	            int read = stream.read(buffer);
	            if (read == -1)
	                break;
	            
	            //file.write(buffer, 0, read);
	            _got += read;
	            //stateChanged();
	        }
	        
	        if (_state == STATE_DOWNLOADING) {
	        	_state = STATE_COMPLETE;
	            //stateChanged();
	        }
		} catch (Exception e) {
        } finally {
        }

	}

}
