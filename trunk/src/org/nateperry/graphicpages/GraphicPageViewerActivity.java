package org.nateperry.graphicpages;


import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class GraphicPageViewerActivity extends Activity {

	private int _current;
	public static final String KEY_LAST_VIEWED_COMIC = "last_viewed_comic";
	public static final String KEY_LAST_VIEWED_ID = "last_viewed_id";
	public WebComic comic;
	public PageTouchListener touchListener;
	private UpdateTask _updateTask;

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
    	
    	comic = new QuestionableContentWebComic();
		
    	// this.getIntent().getIntExtra(KEY_LAST_VIEWED, DEFAULT_LAST_VIEWED);
    	if (savedInstanceState != null) {
    		_current = savedInstanceState.getInt(KEY_LAST_VIEWED_ID, -1);
    	} else {
    		_current = -1;
    	}
    	
    	if (_current == -1) {
    		_current = comic.NewestId();
    	}
    };

    @Override
    public void onStart() {
    	super.onStart();
    };

    @Override
    public void onResume() {
    	super.onResume();

    	Update();
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
        	_current = comic.NewestId();
        	Update();
        }
    };

    private OnClickListener ui_oldest_Button_Click = new OnClickListener()
    {
        public void onClick(View v)
        {
        	_current = comic.OldestId();
        	Update();
        }
    };

    private OnClickListener ui_newer_Button_Click = new OnClickListener()
    {
        public void onClick(View v)
        {
        	_current = comic.NewerId(_current);
        	Update();
        }
    };

    private OnClickListener ui_older_Button_Click = new OnClickListener()
    {
        public void onClick(View v)
        {
        	_current = comic.OlderId(_current);
        	Update();
        }
    };

	//public void onClick(View v) {
	//	new DownloadImageTask().execute("http://example.com/image.png");
	//}

	private class UpdateTask extends AsyncTask<Integer, Integer, BitmapDrawable> {

		@Override
		protected BitmapDrawable doInBackground(Integer... params) {

			try {
		    	String pageUrl = comic.PageUrl(params[0]);
		    	BitmapDrawable image = Utils.wget_bitmap(pageUrl);

		    	return image;
		    	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}

		protected void onPostExecute(BitmapDrawable result) {
	     	if (result != null) {
		     	TextView text = (TextView)findViewById(R.id.ui_info_TextView);
		     	text.setText("QC #" + _current);

		     	ImageView im = (ImageView)findViewById(R.id.ui_image_ImageView);
	     		im.setBackgroundDrawable(result);
	    	}
		}

	}

    protected void Update() {
    	//if (_updateTask != null) _updateTask.cancel(true);
		_updateTask = new UpdateTask();
    	_updateTask.execute(_current);
    };

    protected boolean HavePage(int number) {
		// MediaStore.Images.Media.getBitmap(getContentResolver(), url)

    	return false;
    };

    protected void ShowPage(int number) {
    	//ImageView im = (ImageView)findViewById(R.id.ui_image_ImageView);
        //im.setAdjustViewBounds(true);
		//im.setBackgroundDrawable(wget("http://www.questionablecontent.net/comics/1695.png"));
    };

    protected void GetPage(int number) {

    	String pageUrl = comic.PageUrl(number);
    	BitmapDrawable image = Utils.wget_bitmap(pageUrl);

    	if (image != null) {
	    	//ContentValues values = new ContentValues(1);
	        //values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
	        //values.put(MediaStore.Images.Media.DISPLAY_NAME, "");

	        //String url = 
	        //	MediaStore.Images.Media.insertImage(
	        //		getContentResolver(), 
	        //		image.getBitmap(), 
	        //		comic.PageName(number), 
	        //		comic.PageDescription(number));
	        
	        //Log.i("Comic URL", url);

        	ImageView im = (ImageView)findViewById(R.id.ui_image_ImageView);
    		im.setBackgroundDrawable(image);
            //im.setAdjustViewBounds(true);
            //im.setScaleType( ImageView.ScaleType.CENTER);
    	}
    };

}
