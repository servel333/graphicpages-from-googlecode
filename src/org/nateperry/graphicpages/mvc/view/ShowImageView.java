package org.nateperry.graphicpages.mvc.view;

import org.nateperry.graphicpages.single.questionablecontent.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowImageView extends Activity {

	protected OnChangeListener _listener;

	public ShowImageView() {
	}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_comic);

    	((Button)findViewById(R.id.ui_newest_Button)).setOnClickListener(
        		new OnClickListener() {
					public void onClick(View v) {
						onNewestClicked();
					}
        		});

    	((Button)findViewById(R.id.ui_newer_Button)).setOnClickListener(
        		new OnClickListener() {
					public void onClick(View v) {
						onNewerClicked();
					}
        		});

    	((Button)findViewById(R.id.ui_oldest_Button)).setOnClickListener(
        		new OnClickListener() {
					public void onClick(View v) {
						onOldestClicked();
					}
        		});

    	((Button)findViewById(R.id.ui_older_Button)).setOnClickListener(
        		new OnClickListener() {
					public void onClick(View v) {
						onOlderClicked();
					}
        		});

    }

	public void onNewestClicked() {
		if (_listener != null) {
			_listener.onNewestClicked(this);
		}
	}

	public void onOldestClicked() {
		if (_listener != null) {
			_listener.onOldestClicked(this);
		}
	}

	public void onNewerClicked() {
		if (_listener != null) {
			_listener.onNewerClicked(this);
		}
	}

	public void onOlderClicked() {
		if (_listener != null) {
			_listener.onOlderClicked(this);
		}
	}

	public void setImageTitle(String text) {
     	((TextView)findViewById(R.id.ui_info_TextView)).setText(text);
	}

	public void setImage(Bitmap image) {
		((ImageView)findViewById(R.id.ui_image_ImageView)).setImageBitmap(image);
	}

	public void showToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT);
	}

    public void setOnChangeListener(OnChangeListener l) {
    	_listener = l;
    }

    public void clearOnChangeListener() {
    	_listener = null;
    }

    public interface OnChangeListener {
    	public void onNewestClicked(ShowImageView view);
    	public void onOldestClicked(ShowImageView view);
    	public void onNewerClicked(ShowImageView view);
    	public void onOlderClicked(ShowImageView view);
    }

}
