package org.nateperry.graphicpages.single.questionablecontent;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
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
		lv.setOnItemClickListener(ui_ListView_OnItemClickListener);
		
		Update();
    }
	
	private OnItemClickListener ui_ListView_OnItemClickListener =  new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			
			Object item = getListAdapter().getItem(position);
			if (item instanceof WebComicPage) {
				WebComicPage page = (WebComicPage)item;
				WebComicInstance.SetIndex(page.GetId());
				
				Intent i = new Intent(_thisActivity, GraphicPageViewerActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	            startActivity(i);
	            finish();
			}
			
	        //ListView lv = getListView();
	        //lv.getItemAtPosition(position)
        	//Intent i = new GoToPageIntent(this, ListFilesActivity.class, );
			
		}
	};

    protected void Update() {
    	if (_updateTask != null) _updateTask.cancel(false);
		_updateTask = new UpdateTask();
    	_updateTask.execute(0);
    };

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_files_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.purge:
        	Toast.makeText(getApplicationContext(), "Not yet implemented.", Toast.LENGTH_SHORT).show();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	private class UpdateTask extends AsyncTask<Integer, Integer, ArrayList<WebComicPage>> {

		@Override
		protected void onPreExecute () {
			
		}
		
		@Override
		protected ArrayList<WebComicPage> doInBackground(Integer... params) {

			//String[] files;
			ArrayList<WebComicPage> pages = new ArrayList<WebComicPage>();
			
			try {
				
				File dir = getFilesDir();
	    		File xDir = null;
	    		
		    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
		    		
			    	//File myDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES); // Android API 8 only
		    		
			    	File xDirRoot = Environment.getExternalStorageDirectory();
			    	xDir = new File(xDirRoot, Globals.EXTERNAL_DATA_FOLDER);
		    		
		    	}
		    	
		    	if (null != xDir) {
		    		String[] list = xDir.list();
		    		if (null != list) {
		    			
		    			for (String item : list) {
		    				pages.add(new WebComicPage(WebComicInstance.GetComic(), item));
		    			}
		    			
				    	//files.addAll(Arrays.asList(list));
		    		}
		    	}
		    	
		    	if (null != dir) {
		    		String[] list = dir.list();
		    		if (null != list) {
		    			
		    			for (String item : list) {
		    				pages.add(new WebComicPage(WebComicInstance.GetComic(), item));
		    			}
		    			
		    			//files.addAll(Arrays.asList(list));
		    		}
		    	}
	    		
		    	Collections.sort(pages, new WebComicPageComparator());
		    	return pages;
		    	
			} catch (Exception e) {
				e.printStackTrace();
				Log.w("downloading", e);
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<WebComicPage> files) {
	     	if (files != null) {
		     	//ListView list = (ListView)findViewById(R.id.ui_files_ListView);
		        setListAdapter(new ArrayAdapter<WebComicPage>(_thisActivity, R.layout.list_file, files));
	    	}
		}
		
		@Override
		protected void onProgressUpdate (Integer... values) {
			
		}
	}
    
}
