package org.nateperry.graphicpages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GraphicPageViewerActivity extends Activity {

	private int _current;
	public static final String KEY_LAST_VIEWED_COMIC = "last_viewed_comic";
	public static final String KEY_LAST_VIEWED_ID = "last_viewed_id";
	public WebComic _comic;
	public PageTouchListener touchListener;
	private UpdateTask _updateTask;
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
    	
    	touchListener = new PageTouchListener();
    	((ImageView)findViewById(R.id.ui_image_ImageView)).setOnTouchListener(touchListener);
    	
    	_comic = new QuestionableContentWebComic();
		
    	// this.getIntent().getIntExtra(KEY_LAST_VIEWED, DEFAULT_LAST_VIEWED);
    	if (savedInstanceState != null) {
    		_current = savedInstanceState.getInt(KEY_LAST_VIEWED_ID, -1);
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
    	outState.putInt(KEY_LAST_VIEWED_ID, _current);
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


	//public void onClick(View v) {
    protected void Update(int action) {
    	if (_updateTask != null) _updateTask.cancel(true);
		_updateTask = new UpdateTask();
    	_updateTask.execute(action);
    };

    //protected boolean HavePage(int number) {
	//    // MediaStore.Images.Media.getBitmap(getContentResolver(), url)
    //
    //    return false;
    //};
    

    //protected void ShowPage(int number) {
    //    //ImageView im = (ImageView)findViewById(R.id.ui_image_ImageView);
    //    //im.setAdjustViewBounds(true);
	//    //im.setBackgroundDrawable(wget("http://www.questionablecontent.net/comics/1695.png"));
    //};
    

    //protected void GetPage(int number) {
    //
    //    String pageUrl = comic.PageUrl(number);
    //    BitmapDrawable image = Utils.wget_bitmap(pageUrl);
    //
    //    if (image != null) {
	//        //ContentValues values = new ContentValues(1);
	//        //values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
	//        //values.put(MediaStore.Images.Media.DISPLAY_NAME, "");
    //
	//        //String url = 
	//        //	MediaStore.Images.Media.insertImage(
	//        //		getContentResolver(), 
	//        //		image.getBitmap(), 
	//        //		comic.PageName(number), 
	//        //		comic.PageDescription(number));
	//        
	//        //Log.i("Comic URL", url);
    //
    //        ImageView im = (ImageView)findViewById(R.id.ui_image_ImageView);
    //        im.setBackgroundDrawable(image);
    //        //im.setAdjustViewBounds(true);
    //        //im.setScaleType( ImageView.ScaleType.CENTER);
    //    }
    //};

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
			        	_current = _comic.GetNewestId();
						break;
					default: // ACTION_UPDATE
				    	if (_current == -1) {
				    		_current = _comic.GetNewestId();
				    	}
						break;
				}
				
				// Check file on disk
				
				Bitmap image;
				
				File dir = getFilesDir();
	    		File file = new File(dir, _comic.GetFileName(_current));
	    		File xFile = null;

		    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

			    	//File myDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES); // Android API 8 only

			    	File xDir = Environment.getExternalStorageDirectory();
			    	File myDir = new File(xDir, "/Android/data/" + PACKAGE_NAME + "/files/");
		    		xFile = new File(myDir, _comic.GetFileName(_current));
		    	
		    	}

	    		if (file.exists()) {
	    			
	    			FileInputStream in = openFileInput(_comic.GetFileName(_current));
	    			image = BitmapFactory.decodeStream(in);
	    			
	    		} else if (xFile != null && xFile.exists()) {
	    			
	    			FileInputStream in = new FileInputStream(xFile.getAbsolutePath());
	    			image = BitmapFactory.decodeStream(in);
	    			
	    		} else {
	    			
			    	String pageUrl = _comic.GetPageUrl(_current);
			    	image = Utils.downloadBitmap(pageUrl);
	    			
			    	// Write the bitmap to a file.
			    	// Todo: buffer and write the file peace by peace instead of loading the whole thing into memory.

			    	FileOutputStream out;

			    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			    		
				    	out = new FileOutputStream(file);
				    	
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

		protected void onPostExecute(Bitmap result) {
	     	if (result != null) {
		     	TextView text = (TextView)findViewById(R.id.ui_info_TextView);
		     	text.setText("QC #" + _current);

		     	ImageView im = (ImageView)findViewById(R.id.ui_image_ImageView);
		     	BitmapDrawable bd = new BitmapDrawable(result);
	     		im.setBackgroundDrawable(bd);
	    	}
		}
		
		@Override
		protected void onProgressUpdate (Integer... values) {
			
		}
	}

}
