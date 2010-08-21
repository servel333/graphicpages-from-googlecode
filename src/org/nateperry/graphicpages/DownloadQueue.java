package org.nateperry.graphicpages;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Environment;
import android.os.AsyncTask.Status;

public class DownloadQueue {
	
	private volatile ArrayList<DownloadItem> _list;
	private volatile DownloadTask _downloadTask;
	
	private enum DownloadTaskStatus {
		STARTING, FINISHED
	}

	public DownloadQueue() {
		_list = new ArrayList<DownloadItem>();
		_downloadTask = new DownloadTask();
	}
	
	public synchronized void AddItem(DownloadItem item) {
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
	

	private class DownloadTask extends AsyncTask<Integer, DownloadTaskStatus, Integer> {

		@Override
		protected void onPreExecute () {
			
		}
		
		@Override
		protected Integer doInBackground(Integer... params) {

			try {
				
				while (_list.size() > 0  | !this.isCancelled()) {
					
					DownloadItem item = _list.remove(0);
					
					File dir = getFilesDir();
		    		File file = new File(dir, WebComicInstance.getLot().GetFileName(WebComicInstance.getIndex()));
		    		File xDir = null;
		    		File xFile = null;
					
			    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			    		
				    	//File myDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES); // Android API 8 only
			    		
				    	File xRootDir = Environment.getExternalStorageDirectory();
				    	xDir = new File(xRootDir, Globals.EXTERNAL_DATA_FOLDER);
			    		xFile = new File(xDir, WebComicInstance.getLot().GetFileName(WebComicInstance.getIndex()));
			    		
			    	}
					
		    		if (file.exists()) {
		    			
		    		} else if (xFile != null && xFile.exists()) {
		    			
		    		} else {
		    			
				    	String pageUrl = WebComicInstance.getLot().GetPageUrl(WebComicInstance.getIndex());
				    	image = Utils.downloadBitmap(pageUrl);
		    			
				    	// Write the bitmap to a file.
				    	// Todo: buffer and write the file peace by peace instead of loading the whole thing into memory.
				    	
				    	FileOutputStream out;
				    	
				    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
				    		
			    			xDir.mkdirs();
			    			xFile.createNewFile();
			    			
					    	out = new FileOutputStream(xFile);
					    	
				    	} else {
				    		
					    	out = openFileOutput(WebComicInstance.getLot().GetFileName(WebComicInstance.getIndex()), Context.MODE_PRIVATE);
					    	
				    	}
				    	
				    	image.compress(CompressFormat.PNG, 75, out);
				    	out.flush();
				    	out.close();
		    		}
		    		
				}

				return image;
		    	
			} catch (Exception e) {
				e.printStackTrace();
				Log.w("downloading", e);
			}
			
			return null;
		}
		
		@Override
		protected void onProgressUpdate (DownloadTaskStatus... values) {
			
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

	
}
