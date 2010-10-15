package org.nateperry.graphicpages.util;



/**
 * Manages a single instance of the DownloadAsyncTask class.
 */
public class DownloadAsyncTaskManager implements IDownloadAsyncTaskManager {

	protected final DownloadItemInfo _item;
	protected volatile eDownloadStatus _status;
	protected volatile OnChangeListener listener;
	protected DownloadAsyncTask _downloader;

	public DownloadAsyncTaskManager(DownloadItemInfo item) {
		_item = item;
		_status = eDownloadStatus.DOWNLOADING;
	}

	public boolean wasError() {
		return (_status == eDownloadStatus.ERROR);
	}

    public boolean isDownloadCompleted() {
    	return (_status == eDownloadStatus.COMPLETE && 
    			_status == eDownloadStatus.ERROR);
    }

    public boolean isDownloadPaused() {
		return (_status == eDownloadStatus.PAUSED);
    }

	public eDownloadStatus getDownloadStatus() {
		return _status;
	}

	public void setDownloadStatus(eDownloadStatus status) {
		_status = status;
	}

    public DownloadAsyncTaskManager pauseDownload() {
    	if (_status == eDownloadStatus.DOWNLOADING) {
            _status = eDownloadStatus.PAUSED;
	    	if (_downloader != null) {
	    		_downloader.cancel(false);
	    	}
            onStatusChanged(_status);
    	}

    	return this;
    }

    public DownloadAsyncTaskManager startDownload() {

    	if (_status != eDownloadStatus.DOWNLOADING) {
	    	_status = eDownloadStatus.DOWNLOADING;
	    	if (_downloader != null) {
	    		_downloader.cancel(true);
	    	}
	    	_downloader = new DownloadAsyncTask(this);
	    	_downloader.execute();
	        onStatusChanged(_status);
    	}

    	return this;
    }

    public DownloadItemInfo getDownloadItemInfo() {
    	return _item;
    }

	public void setOnChangeListener(OnChangeListener l) {
		listener = l;
	}

	public void clearOnChangedListener() {
		listener = null;
	}

	public void onStatusChanged(eDownloadStatus status) {
		if (listener != null) {
			listener.onStatusChanged(this,
					isDownloadPaused(),
					isDownloadCompleted(), 
					wasError());
		}
	}

	public void onDownloadedChanged(int downloaded) {
		if (listener != null) {
			listener.onDownloadedChanged(this, downloaded);
		}		
	}

	public void onSizeChanged(int size) {
		if (listener != null) {
			listener.onSizeChanged(this, size);
		}		
	}

	public interface OnChangeListener {

		public void onStatusChanged(
				DownloadAsyncTaskManager task, 
				boolean isPaused, 
				boolean isComplete, 
				boolean isError);

		public void onDownloadedChanged(
				DownloadAsyncTaskManager task, 
				int downloaded);

		public void onSizeChanged(
				DownloadAsyncTaskManager task, 
				int size);
	}

}
