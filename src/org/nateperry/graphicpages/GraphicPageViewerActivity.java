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

	private PageTouchListener _touchListener;
	private UpdateTask _updateTask;

	public static final String KEY_LAST_VIEWED_PAGE = "last_viewed_page";

	private enum Action {
		OLDEST, OLDER, UPDATE, NEWER, NEWEST
	}

	private enum UpdateState {
		UPDATED, DOWNLOADING
	}

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
    	
    	//this.getIntent().getIntExtra(KEY_LAST_VIEWED, DEFAULT_LAST_VIEWED);
    	
    	Intent intent = getIntent();
    	
    	if (intent instanceof GoToPageIntent) {
    		setIntent(intent);
    		WebComicInstance.SetIndex(((GoToPageIntent)intent).GetIndex());
    		//Update(ACTION_UPDATE);
    	} else {
    		
	    	if (savedInstanceState != null) {
	    		WebComicInstance.SetIndex(savedInstanceState.getInt(KEY_LAST_VIEWED_PAGE, -1));
	    	}
    	}
    };

    @Override
    public void onStart() {
    	super.onStart();
    };

    @Override
    public void onResume() {
    	super.onResume();
    	
    	Intent intent = getIntent();
    	
    	if (intent instanceof GoToPageIntent) {
    		setIntent(intent);
    		WebComicInstance.SetIndex(((GoToPageIntent)intent).GetIndex());
    	}
    	
    	Update(Action.UPDATE);
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
    	outState.putInt(KEY_LAST_VIEWED_PAGE, WebComicInstance.GetIndex());
    };

    @Override
    protected void onNewIntent(Intent intent) {
    	
    	if (intent instanceof GoToPageIntent) {
    		setIntent(intent);
    		WebComicInstance.SetIndex(((GoToPageIntent)intent).GetIndex());
    		//Update(ACTION_UPDATE); // Happens in OnResume.
    	}
    	
    };

    private OnClickListener ui_newest_Button_Click = new OnClickListener()
    {
        public void onClick(View v)
        {
        	Update(Action.NEWEST);
        }
    };

    private OnClickListener ui_oldest_Button_Click = new OnClickListener()
    {
        public void onClick(View v)
        {
        	Update(Action.OLDEST);
        }
    };

    private OnClickListener ui_newer_Button_Click = new OnClickListener()
    {
        public void onClick(View v)
        {
        	Update(Action.NEWER);
        }
    };

    private OnClickListener ui_older_Button_Click = new OnClickListener()
    {
        public void onClick(View v)
        {
        	Update(Action.OLDER);
        }
    };

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_comic_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.list_files:
        	Intent i = new Intent(this, ListFilesActivity.class);
            startActivity(i);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    protected void Update(Action action) {
    	
    	if (_updateTask != null) _updateTask.cancel(false);
		_updateTask = new UpdateTask();
    	_updateTask.execute(action);
    	
    };

	private class UpdateTask extends AsyncTask<Action, UpdateState, Bitmap> {
		
		@Override
		protected void onPreExecute () {
			
		}
		
		@Override
		protected Bitmap doInBackground(Action... params) {

			try {
				Action action = params[0];
				switch (action) {
					case OLDEST:
						WebComicInstance.SetOldestId();
						break;
					case OLDER:
						WebComicInstance.SetOlderId();
						break;
					case NEWER:
						WebComicInstance.SetNewerId();
						break;
					case NEWEST:
						WebComicInstance.SetNewestId(true);
						break;
					default: // ACTION_UPDATE
				    	if (WebComicInstance.GetIndex() == -1) {
				    		WebComicInstance.SetNewestId();
				    	}
						break;
				}
				
				if (!isCancelled()) { publishProgress(UpdateState.UPDATED); }
    			
    			// Check file on disk
				
				Bitmap image = null;
				
				File dir = getFilesDir();
	    		File file = new File(dir, WebComicInstance.GetComic().GetFileName(WebComicInstance.GetIndex()));
	    		File xDir = null;
	    		File xFile = null;
	    		
		    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
		    		
			    	//File myDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES); // Android API 8 only
		    		
			    	File xRootDir = Environment.getExternalStorageDirectory();
			    	xDir = new File(xRootDir, Globals.EXTERNAL_DATA_FOLDER);
		    		xFile = new File(xDir, WebComicInstance.GetComic().GetFileName(WebComicInstance.GetIndex()));
		    		
		    	}
		    	
	    		if (file.exists()) {
	    			
	    			FileInputStream in = openFileInput(WebComicInstance.GetComic().GetFileName(WebComicInstance.GetIndex()));
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
	    			
					if (!isCancelled()) { publishProgress(UpdateState.DOWNLOADING); }
					
			    	String pageUrl = WebComicInstance.GetComic().GetPageUrl(WebComicInstance.GetIndex());
			    	image = Utils.downloadBitmap(pageUrl);
	    			
			    	// Write the bitmap to a file.
			    	// Todo: buffer and write the file peace by peace instead of loading the whole thing into memory.
			    	
			    	FileOutputStream out;
			    	
			    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			    		
		    			xDir.mkdirs();
		    			xFile.createNewFile();
		    			
				    	out = new FileOutputStream(xFile);
				    	
			    	} else {
			    		
				    	out = openFileOutput(WebComicInstance.GetComic().GetFileName(WebComicInstance.GetIndex()), Context.MODE_PRIVATE);
				    	
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
		protected void onProgressUpdate (UpdateState... values) {
			
			if (values.length > 0) {
				if (values[0] == UpdateState.UPDATED) {
					
			     	TextView text = (TextView)findViewById(R.id.ui_info_TextView);
			     	text.setText(WebComicInstance.GetComic().GetPageName(WebComicInstance.GetIndex()));
					
				} else if (values[0] == UpdateState.DOWNLOADING) {
					
					Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();
					
				}
			}
			
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
	     	if (result != null) {
	     		
	     		ImageView im = (ImageView)findViewById(R.id.ui_image_ImageView);
	     		im.setImageBitmap(result);
	     		
	     		_touchListener.ResetTouch();
	    	}
		}
	}

}
