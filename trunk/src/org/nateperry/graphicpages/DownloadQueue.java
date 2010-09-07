package org.nateperry.graphicpages;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.nateperry.Util;
import org.nateperry.library.NumericImageLot;

import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.util.Log;

public class DownloadQueue {
	
	protected ContextWrapper _context;
	protected volatile ArrayList<DownloadQueueItem> _list;
	protected volatile DownloadTask _downloadTask;

//	private enum DownloadTaskStatus {
//		STARTING, FINISHED
//	}

	public DownloadQueue(ContextWrapper context) {
		_context = context;
		_list = new ArrayList<DownloadQueueItem>();
		_downloadTask = new DownloadTask();
	}
	
	public synchronized void AddItem(DownloadQueueItem item) {
		_list.add(item);
		Start();
	}

	public synchronized void Start() {

		if (_downloadTask == null) {

			_downloadTask = new DownloadTask();
			_downloadTask.execute();

		} else {
			if (_downloadTask.isCancelled()) {

				_downloadTask = new DownloadTask();
				_downloadTask.execute();

			} else if (_downloadTask.getStatus() != Status.RUNNING) {

				_downloadTask.cancel(true);
				_downloadTask = new DownloadTask();
				_downloadTask.execute();

			}
		}
	}

	private class DownloadTask extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected void onPreExecute () {

		}

		@Override
		protected Integer doInBackground(Integer... params) {

			try {

				while (_list.size() > 0  || !this.isCancelled()) {

					DownloadQueueItem item = _list.remove(0);
					String fileName = item.getLot().getFileName(item.getId());
//					boolean isOnExternal = Util.existsOnExternal(fileName);
//					boolean isOnInternal = Util.existsOnInternal(_context, fileName);

//					if (isOnExternal || isOnInternal) {
//
//						// File already exists.
//
//					} else {

						File dir = Util.getPreferredFileDirectory(_context);
						File file = new File(dir, fileName);

				    	Bitmap image = Util.downloadBitmap(item.GetUrl());

				    	// Write the bitmap to a file.
				    	// Todo: buffer and write the file peace by peace instead of loading the whole thing into memory.

				    	FileOutputStream out = new FileOutputStream(file);

				    	image.compress(CompressFormat.PNG, 75, out);
				    	out.flush();
				    	out.close();
//		    		}

				}

			} catch (Exception e) {
				e.printStackTrace();
				Log.w("downloading", e);
			}

			return null;
		}

		@Override
		protected void onProgressUpdate (Integer... values) {

			//if (values.length > 0) {
			//	if (values[0] == DownloadTaskStatus.FINISHED) {
			//		
			//		TextView text = (TextView)findViewById(R.id.ui_info_TextView);
			//     	text.setText(WebComicInstance.GetComic().GetPageName(WebComicInstance.GetIndex()));
			//		
			//	} else if (values[0] == UPDATE_STATE_DOWNLOADING) {
			//		
			//		Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();
			//		
			//	}
			//}

		}

		@Override
		protected void onPostExecute(Integer result) {
	     	//if (result != null) {
	     	//	
	     	//	ImageView im = (ImageView)findViewById(R.id.ui_image_ImageView);
	     	//	im.setImageBitmap(result);
	     	//	
	     	//	_touchListener.ResetTouch();
	    	//}
		}

	}

	protected class DownloadQueueItem {

		protected String _url;
		protected NumericImageLot _lot;
		protected Integer _id;

		public DownloadQueueItem(String url, NumericImageLot lot, Integer id) {
			_url = url;
			_lot = lot;
			_id = id;
		}

		public DownloadQueueItem(DownloadQueueItem item) {
			_url = new String(item._url);
			_lot = (NumericImageLot)item._lot.clone();
			_id = item._id;
		}

		public String GetUrl() {
			return _url;
		}

		public NumericImageLot getLot() {
			return _lot;
		}

		public Integer getId() {
			return _id;
		}

		@Override
		public Object clone() {
			return new DownloadQueueItem(this);
		}

	}

}
