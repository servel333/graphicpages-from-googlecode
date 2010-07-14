package org.nateperry.graphicpages;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.ContentValues;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class GraphicPageViewerActivity extends Activity
{

	private int _current;
	public static final String KEY_LAST_VIEWED_COMIC = "last_viewed_comic";
	public static final String KEY_LAST_VIEWED_ID = "last_viewed_id";
	public static final int DEFAULT_LAST_VIEWED = 1703;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_comic);

     	((Button)findViewById(R.id.ui_newest_Button)).setOnClickListener(ui_newest_Button_Click);
    	((Button)findViewById(R.id.ui_oldest_Button)).setOnClickListener(ui_oldest_Button_Click);
    	((Button)findViewById(R.id.ui_newer_Button)).setOnClickListener(ui_newer_Button_Click);
    	((Button)findViewById(R.id.ui_older_Button)).setOnClickListener(ui_older_Button_Click);

    	// this.getIntent().getIntExtra(KEY_LAST_VIEWED, DEFAULT_LAST_VIEWED);
    	if (savedInstanceState != null) {
    		_current = savedInstanceState.getInt(KEY_LAST_VIEWED_ID, DEFAULT_LAST_VIEWED);
    	} else {
    		_current = DEFAULT_LAST_VIEWED;
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

    protected void Update() {
		try {

	     	TextView text = (TextView)findViewById(R.id.ui_info_TextView);
	     	text.setText("QC #" + _current);

	    	if (!HavePage(_current)) {
	    		GetPage(_current);
	    	}

    		ShowPage(_current);

		} catch (Exception e) {
			//Log.e("GraphicPageViewerActivity", "", e);
			e.printStackTrace();
		}
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

    	BitmapDrawable image = Utils.wget(LibraryProvider.GetUrl(number));

    	if (image != null) {
	    	ContentValues values = new ContentValues(1);
	        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
	        //values.put(MediaStore.Images.Media.DISPLAY_NAME, "");

	        String url = MediaStore.Images.Media.insertImage(getContentResolver(), image.getBitmap(), GetName(number), GetDescription(number));
	        Log.i("Comic URL", url);
    	}
    };

    protected int GetNewest() {
		return DEFAULT_LAST_VIEWED;
    };

    protected int GetOldest() {
    	return 0;
    };

    private OnClickListener ui_newest_Button_Click = new OnClickListener()
    {
        public void onClick(View v)
        {
        	_current = GetNewest();
        	Update();
        }
    };

    private OnClickListener ui_oldest_Button_Click = new OnClickListener()
    {
        public void onClick(View v)
        {
        	_current = GetOldest();
        	Update();
        }
    };

    private OnClickListener ui_newer_Button_Click = new OnClickListener()
    {
        public void onClick(View v)
        {
        	_current += 1;
        	Update();
        }
    };

    private OnClickListener ui_older_Button_Click = new OnClickListener()
    {
        public void onClick(View v)
        {
        	_current -= 1;
        	Update();
        }
    };

}
