//package org.nateperry.graphicpages;
//
//import java.io.File;
//import java.io.InputStream;
//import java.io.RandomAccessFile;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.Observable;
//
////http://www.java-tips.org/java-se-tips/javax.swing/how-to-create-a-download-manager-in-java.html
//public class Downloader extends Observable implements Runnable {
//	
//	private static final int BUFFER_SIZE = 1024;
//	
//	public enum State {
//		DOWNLOADING, PAUSED, COMPLETED, CANCELLED, ERROR
//	}
//	
//	private DownloadItem _item;
//    private volatile State _state;
//    private volatile int _size;
//    private volatile int _got;
//    
//    public Downloader(DownloadItem item) throws MalformedURLException {
//		_item = (DownloadItem)item.clone();
//		_size = -1;
//		_state = State.DOWNLOADING;
//		_got = 0;
//	}
//    
//    public URL getUrl() {
//        return _item.GetUrl();
//    }
//	
//    // Get this download's size.
//    public int getSize() {
//        return _size;
//    }
//    
//    // Get this download's progress.
//    public float getProgress() {
//        return ((float) _got / _size) * 100;
//    }
//    
//    // Get this download's status.
//    public State getState() {
//        return _state;
//    }
//    
//    // Pause this download.
//    public void pause() {
//        _state = State.PAUSED;
//        stateChanged();
//    }
//    
//    // Resume this download.
//    public void resume() {
//        _state = State.DOWNLOADING;
//        stateChanged();
//        download();
//    }
//    
//    // Cancel this download.
//    public void cancel() {
//        _state = State.CANCELLED;
//        stateChanged();
//    }
//    
//    private void stateChanged() {
//        setChanged();
//        notifyObservers();
//    }
//    
//    // Mark this download as having an error.
//    private void error() {
//        _state = State.ERROR;
//        stateChanged();
//    }
//    
//    // Start or resume downloading.
//    private void download() {
//        Thread thread = new Thread(this);
//        thread.start();
//    }
//    
////    // Get file name portion of URL.
////    private String getFileName(URL url) {
////        String fileName = url.getFile();
////        return fileName.substring(fileName.lastIndexOf('/') + 1);
////    }
//    
//	public void run() {
//		
//		RandomAccessFile file = null;
//        InputStream stream = null;
//
//		try {
//			HttpURLConnection connection =
//                (HttpURLConnection) _item.GetUrl().openConnection();
//
//	        connection.setRequestProperty("Range", "bytes=" + _got + "-");
//        
//	        connection.connect();
//	        
//	        if (connection.getResponseCode() / 100 != 2) {
//	            error();
//	        }
//	        
//	        int contentLength = connection.getContentLength();
//	        if (contentLength < 1) {
//	            error();
//	        }
//	        
//	        if (_size == -1) {
//	            _size = contentLength;
//	            stateChanged();
//	        }
//	        
//	        file = new RandomAccessFile(_file, "rw");
//	        file.seek(_got);
//	        
//	        stream = connection.getInputStream();
//	        while (_state == State.DOWNLOADING) {
//
//	            byte buffer[];
//	            if (_size - _got > BUFFER_SIZE) {
//	                buffer = new byte[BUFFER_SIZE];
//	            } else {
//	                buffer = new byte[_size - _got];
//	            }
//	            
//	            int read = stream.read(buffer);
//	            if (read == -1)
//	                break;
//	            
//	            //file.write(buffer, 0, read);
//	            _got += read;
//	            //stateChanged();
//	        }
//	        
//	        if (_state == State.DOWNLOADING) {
//	        	_state = State.COMPLETE;
//	            //stateChanged();
//	        }
//		} catch (Exception e) {
//        } finally {
//        }
//
//	}
//
//}
