package org.nateperry.graphicpages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GraphicPageViewerActivity extends Activity {

	private int _current;
	public WebComic _comic;
	public PageTouchListener _touchListener;
	private UpdateTask _updateTask;

	public static final String KEY_LAST_VIEWED_PAGE = "last_viewed_page";
	public static final String PACKAGE_NAME = "org.nateperry.graphicpages";

	private static final int ACTION_OLDEST = -2;
	private static final int ACTION_OLDER  = -1;
	private static final int ACTION_UPDATE =  0;
	private static final int ACTION_NEWER  =  1;
	private static final int ACTION_NEWEST =  2;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_comic);
        
     	((Button)findViewById(R.id.ui_newest_Button)).setOnClickListener(ui_newest_Button_Click);
    	((Button)findViewById(R.id.ui_oldest_Button)).setOnClickListener(ui_oldest_Button_Click);
    	((Button)findViewById(R.id.ui_newer_Button)).setOnClickListener(ui_newer_Button_Click);
    	((Button)findViewById(R.id.ui_older_Button)).setOnClickListener(ui_older_Button_Click);
    	
    	_touchListener = new PageTouchListener();
    	((ImageView)findViewById(R.id.ui_image_ImageView)).setOnTouchListener(_touchListener);
    	
    	_comic = new QuestionableContentWebComic();
		
    	// this.getIntent().getIntExtra(KEY_LAST_VIEWED, DEFAULT_LAST_VIEWED);
    	if (savedInstanceState != null) {
    		_current = savedInstanceState.getInt(KEY_LAST_VIEWED_PAGE, -1);
    	} else {
    		_current = -1;
    	}
    };

    @Override
    public void onStart() {
    	super.onStart();
    };

    @Override
    public void onResume() {
    	super.onResume();

    	Update(ACTION_UPDATE);
    };

    @Override
    public void onPause() {
    	
    	if (_updateTask != null) _updateTask.cancel(false);
    	
    	super.onPause();
    };

    @Override
    public void onStop() {
    	super.onStop();
    };

    @Override
    public void onDestroy() {
    	super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);

    	if (outState == null) {
    		outState = new Bundle();
    	}

    	//outState.putString(KEY_LAST_VIEWED_COMIC, QC_NAME);
    	outState.putInt(KEY_LAST_VIEWED_PAGE, _current);
    };

    private OnClickListener ui_newest_Button_Click = new OnClickListener()
    {
        public void onClick(View v)
        {
        	Update(ACTION_NEWEST);
        }
    };

    private OnClickListener ui_oldest_Button_Click = new OnClickListener()
    {
        public void onClick(View v)
        {
        	Update(ACTION_OLDEST);
        }
    };

    private OnClickListener ui_newer_Button_Click = new OnClickListener()
    {
        public void onClick(View v)
        {
        	Update(ACTION_NEWER);
        }
    };

    private OnClickListener ui_older_Button_Click = new OnClickListener()
    {
        public void onClick(View v)
        {
        	Update(ACTION_OLDER);
        }
    };

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_comic_menu, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.list_files:
        	//Toast.makeText(getApplicationContext(), "Not suppoted yet.", Toast.LENGTH_SHORT).show();
        	Intent i = new Intent(this, ListFilesActivity.class);
            startActivity(i);
            return true;
        case R.id.jump_to:
        	Toast.makeText(getApplicationContext(), "Not suppoted yet.", Toast.LENGTH_SHORT).show();
            return true;
        case R.id.quit:
        	finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    protected void Update(int action) {
    	if (_updateTask != null) _updateTask.cancel(false);
		_updateTask = new UpdateTask();
    	_updateTask.execute(action);
    };

	private class UpdateTask extends AsyncTask<Integer, Integer, Bitmap> {

		@Override
		protected void onPreExecute () {
			
		}
		
		@Override
		protected Bitmap doInBackground(Integer... params) {

			try {
				int action = params[0];
				switch (action) {
					case ACTION_OLDEST:
			        	_current = _comic.GetOldestId();
						break;
					case ACTION_OLDER:
			        	_current = _comic.GetOlderId(_current);
						break;
					case ACTION_NEWER:
			        	_current = _comic.GetNewerId(_current);
						break;
					case ACTION_NEWEST:
			        	_current = _comic.GetNewestId(true);
						break;
					default: // ACTION_UPDATE
				    	if (_current == -1) {
				    		_current = _comic.GetNewestId();
				    	}
						break;
				}
				
				// Check file on disk
				
				Bitmap image = null;
				
				File dir = getFilesDir();
	    		File file = new File(dir, _comic.GetFileName(_current));
	    		File xDir = null;
	    		File xFile = null;
	    		
		    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
		    		
			    	//File myDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES); // Android API 8 only
		    		
			    	File xRootDir = Environment.getExternalStorageDirectory();
			    	xDir = new File(xRootDir, "/Android/data/" + PACKAGE_NAME + "/files/");
		    		xFile = new File(xDir, _comic.GetFileName(_current));
		    		
		    	}
		    	
	    		if (file.exists()) {
	    			
	    			FileInputStream in = openFileInput(_comic.GetFileName(_current));
	    			image = BitmapFactory.decodeStream(in);
	    			
	    			if (null == image) {
	    				file.delete();
	    			}
	    			
	    		} else if (xFile != null && xFile.exists()) {
	    			
	    			FileInputStream in = new FileInputStream(xFile.getAbsolutePath());
	    			image = BitmapFactory.decodeStream(in);
	    			
	    			if (null == image) {
	    				xFile.delete();
	    			}
	    			
	    		} 
	    		
	    		if (null == image) {
	    			
			    	String pageUrl = _comic.GetPageUrl(_current);
			    	image = Utils.downloadBitmap(pageUrl);
	    			
			    	// Write the bitmap to a file.
			    	// Todo: buffer and write the file peace by peace instead of loading the whole thing into memory.
			    	
			    	FileOutputStream out;
			    	
			    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			    		
		    			xDir.mkdirs();
		    			xFile.createNewFile();
		    			
				    	out = new FileOutputStream(xFile);
				    	
			    	} else {
			    		
				    	out = openFileOutput(_comic.GetFileName(_current), Context.MODE_PRIVATE);
				    	
			    	}
			    	
			    	image.compress(CompressFormat.PNG, 75, out);
			    	out.flush();
			    	out.close();
	    		}
	    		
		    	return image;
		    	
			} catch (Exception e) {
				e.printStackTrace();
				Log.w("downloading", e);
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
	     	if (result != null) {
		     	TextView text = (TextView)findViewById(R.id.ui_info_TextView);
		     	text.setText(_comic.GetPageName(_current));
		     	
		     	ImageView im = (ImageView)findViewById(R.id.ui_image_ImageView);
	     		im.setImageBitmap(result);
	     		
	     		_touchListener.ResetTouch();
	    	}
		}
		
		@Override
		protected void onProgressUpdate (Integer... values) {
			
		}
	}

}
