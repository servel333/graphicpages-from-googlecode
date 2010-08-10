package org.nateperry.graphicpages;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ListFilesActivity extends ListActivity {

	private UpdateTask _updateTask;
	private ListFilesActivity _thisActivity;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _thisActivity = this;
        //setContentView(R.layout.list_files);
        
        setListAdapter(new ArrayAdapter<String>(_thisActivity, R.layout.list_file, new String[] {}));
        
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(ui_ListView_ClickListener);
		
		Update();
    }
	
	private OnItemClickListener ui_ListView_ClickListener =  new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			
			// When clicked, show a toast with the TextView text
			//Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
			
		}
	};

	//public void onClick(View v) {
    protected void Update() {
    	if (_updateTask != null) _updateTask.cancel(false);
		_updateTask = new UpdateTask();
    	_updateTask.execute(0);
    };
    
	private class UpdateTask extends AsyncTask<Integer, Integer, String[]> {

		@Override
		protected void onPreExecute () {
			
		}
		
		@Override
		protected String[] doInBackground(Integer... params) {

			//String[] files;
			ArrayList<String> files = new ArrayList<String>();
			
			try {
				
				File dir = getFilesDir();
	    		File xDir = null;
	    		
		    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
		    		
			    	//File myDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES); // Android API 8 only
		    		
			    	File xDirRoot = Environment.getExternalStorageDirectory();
			    	xDir = new File(xDirRoot, "/Android/data/" + GraphicPageViewerActivity.PACKAGE_NAME + "/files/");
		    		
		    	}
		    	
		    	if (null != xDir) {
		    		String[] list = xDir.list();
		    		if (null != list) {
				    	files.addAll(Arrays.asList(list));
		    		}
		    	}
		    	
		    	if (null != dir) {
		    		String[] list = dir.list();
		    		if (null != list) {
		    			files.addAll(Arrays.asList(list));
		    		}
		    	}
	    		
		    	Collections.sort(files);
		    	return files.toArray(new String[] {});
		    	
			} catch (Exception e) {
				e.printStackTrace();
				Log.w("downloading", e);
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(String[] files) {
	     	if (files != null) {
		     	//ListView list = (ListView)findViewById(R.id.ui_files_ListView);
		        setListAdapter(new ArrayAdapter<String>(_thisActivity, R.layout.list_file, files));
	    	}
		}
		
		@Override
		protected void onProgressUpdate (Integer... values) {
			
		}
	}
    
}
