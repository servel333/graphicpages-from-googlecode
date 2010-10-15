package org.nateperry.graphicpages.util;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;



public class DownloadTask {

	protected enum eStatus {
		DOWNLOADING, 
		PAUSED, 
		COMPLETE, 
		ERROR,
	}

	protected enum eProgressUpdateEvent {
		STATUSCHANGED,
		DOWNLOADEDCHANGED,
		SIZECHANGED,
	}

	private static final int BUFFER_SIZE = 1024;

	protected final DownloadItemInfo _item;
	protected volatile eStatus _status;
	protected volatile OnChangeListener listener;
	protected DownloadAsyncTask _downloader;

	public DownloadTask(DownloadItemInfo item) {
		_item = item;
		_status = eStatus.DOWNLOADING;
	}

	public boolean wasError() {
		return (_status == eStatus.ERROR);
	}

    public boolean isDownloadCompleted() {
    	return (_status == eStatus.COMPLETE && 
    			_status == eStatus.ERROR);
    }

    public DownloadTask pauseDownload() {
    	if (_status == eStatus.DOWNLOADING) {
            _status = eStatus.PAUSED;
	    	if (_downloader != null) {
	    		_downloader.cancel(false);
	    	}
            onStatusChanged(_status);
    	}

    	return this;
    }

    public DownloadTask startDownload() {

    	if (_status != eStatus.DOWNLOADING) {
	    	_status = eStatus.DOWNLOADING;
	    	if (_downloader != null) {
	    		_downloader.cancel(true);
	    	}
	    	_downloader = new DownloadAsyncTask();
	    	_downloader.execute();
	        onStatusChanged(_status);
    	}

    	return this;
    }

    public DownloadItemInfo getDownloadItem() {
    	return _item;
    }

	public void setOnChangedListener(OnChangeListener l) {
		listener = l;
	}

	public void clearOnChangedListener() {
		listener = null;
	}

	protected void onStatusChanged(eStatus status) {
		if (listener != null) {
			listener.onStatusChanged(this, status);
		}
	}

	protected void onDownloadedChanged(int downloaded) {
		if (listener != null) {
			listener.onDownloadedChanged(this, downloaded);
		}		
	}

	protected void onSizeChanged(int downloaded) {
		if (listener != null) {
			listener.onSizeChanged(this, downloaded);
		}		
	}

	protected class DownloadAsyncTask extends AsyncTask<Object, Object, Object> {

		@Override
		protected void onPreExecute () {

		}

		@Override
		protected Bitmap doInBackground(Object... params) {

			RandomAccessFile file = null;
	        InputStream stream = null;

			try {

	            HttpURLConnection connection = (HttpURLConnection) _item.getUrl().openConnection();
	            connection.setRequestProperty("Range", "bytes=" + _item.getDownloaded() + "-");
	            connection.connect();

	            if (connection.getResponseCode() / 100 != 2) {
	            	_status = eStatus.ERROR;
	            	publishStatusChanged(_status);
	                return null;
	            }

	            int contentLength = connection.getContentLength();
	            if (contentLength < 1) {
	            	_status = eStatus.ERROR;
	            	publishStatusChanged(_status);
	                return null;
	            }

	            _item.setSize(contentLength);
	            publishSizeChanged(_item.getSize());

	            file = new RandomAccessFile(_item.getFile(), "rw");
	            file.seek(_item.getDownloaded());
	            stream = connection.getInputStream();

	            while (_status == eStatus.DOWNLOADING && 
	            		!isCancelled()) {

	                byte buffer[] = new byte[BUFFER_SIZE];
	                int justRead = stream.read(buffer);

	                if (justRead == -1) {
	                	break;
	                }

	                file.write(buffer, 0, justRead);
	                _item.addToDownloaded(justRead);
	                publishDownloadChanged(_item.getDownloaded());
	 
	            }

	            if (_status == eStatus.DOWNLOADING) {
	                _status = eStatus.COMPLETE;
	                publishStatusChanged(_status);
	            }

			} catch (Exception e) {
				e.printStackTrace();
				Log.w("downloading", e);
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Object... values) {

			if (values.length > 1 && 
					values[0] instanceof eProgressUpdateEvent) {

				switch ((eProgressUpdateEvent)values[0]) {

				case DOWNLOADEDCHANGED:
					if (values.length > 2 && 
							values[1] instanceof Integer) {

						onDownloadedChanged((Integer)values[1]);
					}
					break;

				case STATUSCHANGED:
					if (values.length > 2 && 
							values[1] instanceof eStatus) {

						onStatusChanged((eStatus)values[1]);
					}
					break;

				case SIZECHANGED:
					if (values.length > 2 && 
							values[1] instanceof Integer) {

						onSizeChanged((Integer)values[1]);
					}
					break;

				}
			}

		}

		@Override
		protected void onPostExecute(Object result) {

		}

		protected void publishStatusChanged(eStatus status) {
	        this.publishProgress(
	        		eProgressUpdateEvent.STATUSCHANGED,
	        		status);
		}

		protected void publishDownloadChanged(int downloaded) {
	        publishProgress(
	        		eProgressUpdateEvent.DOWNLOADEDCHANGED, 
	        		new Integer(downloaded));
		}

		protected void publishSizeChanged(int size) {
	        publishProgress(
	        		eProgressUpdateEvent.SIZECHANGED, 
	        		new Integer(size));
		}

	}

	public interface OnChangeListener {
		public void onStatusChanged(DownloadTask task, eStatus status);
		public void onDownloadedChanged(DownloadTask task, int downloaded);
		public void onSizeChanged(DownloadTask task, int size);
	}

}
