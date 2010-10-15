package org.nateperry.graphicpages.util;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;

import org.nateperry.graphicpages.G;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;


/**
 * Downloads a file from a URL in a background thread.
 */
public class DownloadAsyncTask extends AsyncTask<Object, Object, Object> {

	protected enum eProgressUpdateEvent {
		STATUSCHANGED,
		DOWNLOADEDCHANGED,
		SIZECHANGED,
	}

	protected final IDownloadAsyncTaskManager _manager;

	/**
	 * Initializes this task.
	 * @param manager  The manager of this task. Must not be null.
	 */
	public DownloadAsyncTask(IDownloadAsyncTaskManager manager) {
		_manager = manager;
	}

	/**
	 * Gets the download status from the manager.
	 * @return  The download status.
	 */
	private eDownloadStatus gStatus() {
		return _manager.getDownloadStatus();
	}

	/**
	 * Sets the download status in the manager.
	 * @param status  The new download status.
	 */
	private void sStatus(eDownloadStatus status) {
		_manager.setDownloadStatus(status);
	}

	/**
	 * Gets the download item info from the manager.
	 * @return  The download item info.
	 */
	private DownloadItemInfo gInfo() {
		return _manager.getDownloadItemInfo();
	}

	protected void publishStatusChanged(eDownloadStatus status) {
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

	@Override
	protected void onPreExecute () {

	}

	@Override
	protected Bitmap doInBackground(Object... params) {

		RandomAccessFile file = null;
        InputStream stream = null;

		try {

            HttpURLConnection connection = (HttpURLConnection) gInfo().getUrl().openConnection();
            connection.setRequestProperty("Range", "bytes=" + gInfo().getDownloaded() + "-");
            connection.connect();

            if (connection.getResponseCode() / 100 != 2) {
            	sStatus(eDownloadStatus.ERROR);
            	publishStatusChanged(gStatus());
                return null;
            }

            int contentLength = connection.getContentLength();
            if (contentLength < 1) {
            	sStatus(eDownloadStatus.ERROR);
            	publishStatusChanged(gStatus());
                return null;
            }

            gInfo().setSize(contentLength);
            publishSizeChanged(gInfo().getSize());

            file = new RandomAccessFile(gInfo().getFile(), "rw");
            file.seek(gInfo().getDownloaded());
            stream = connection.getInputStream();

            while (gStatus() == eDownloadStatus.DOWNLOADING && 
            		!isCancelled()) {

                byte buffer[] = new byte[G.DOWNLOAD_BUFFER_SIZE];
                int justRead = stream.read(buffer);

                if (justRead == -1) {
                	break;
                }

                file.write(buffer, 0, justRead);
                gInfo().addToDownloaded(justRead);
                publishDownloadChanged(gInfo().getDownloaded());
 
            }

            if (gStatus() == eDownloadStatus.DOWNLOADING) {
                sStatus(eDownloadStatus.COMPLETE);
                publishStatusChanged(gStatus());
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

					_manager.onDownloadedChanged((Integer)values[1]);
				}
				break;

			case STATUSCHANGED:
				if (values.length > 2 && 
						values[1] instanceof eDownloadStatus) {

					_manager.onStatusChanged((eDownloadStatus)values[1]);
				}
				break;

			case SIZECHANGED:
				if (values.length > 2 && 
						values[1] instanceof Integer) {

					_manager.onSizeChanged((Integer)values[1]);
				}
				break;

			}
		}

	}

	@Override
	protected void onPostExecute(Object result) {

	}

}