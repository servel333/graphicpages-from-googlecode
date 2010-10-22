package org.nateperry.graphicpages.mvc.view;

import org.nateperry.graphicpages.I;
import org.nateperry.graphicpages.intents.JumpToIndexIntent;
import org.nateperry.graphicpages.listeners.ShowImageTouchListener;
import org.nateperry.graphicpages.mvc.controller.ShowImageController;
import org.nateperry.graphicpages.mvc.model.ShowImageModel;
import org.nateperry.graphicpages.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputFilter.LengthFilter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowImageView extends Activity {

	protected IListener _listener;
	protected ShowImageTouchListener _touchListener;
	protected Toast _toast;

	public ShowImageView() {
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

		if (null != _toast) {
			//_toast.cancel();
		}

		_toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		_toast.show();

	}

    public void setListener(IListener l) {
    	_listener = l;
    }

    public void clearListener() {
    	_listener = null;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_comic);

        new ShowImageController(this, new ShowImageModel(I.defaultImageSet));

        _touchListener = new ShowImageTouchListener();
    	((ImageView)findViewById(R.id.ui_image_ImageView)).setOnTouchListener(_touchListener);

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

    @Override
    public void onStart() {
    	super.onStart();
    };

    @Override
    public void onResume() {
    	super.onResume();
    	
    	Intent intent = getIntent();
    	
    	if (intent instanceof JumpToIndexIntent) {
    		setIntent(intent);
    		Integer index = ((JumpToIndexIntent)intent).getIndex();
    		updateImage(index);
    	}
    };

    protected synchronized void updateImage(Integer index) {

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

    	//outState.putInt(KEY_LAST_VIEWED_PAGE, I.setManager.getIndex());
    };

    @Override
    protected void onNewIntent(Intent intent) {
    	
    	if (intent instanceof JumpToIndexIntent) {
    		setIntent(intent);
    		updateImage(((JumpToIndexIntent)intent).getIndex());
    	}
    	
    };

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_comic_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

//    	Intent i;

        switch (item.getItemId()) {
        case R.id.list_files_menuitem:
        	//i = new Intent(this, ListFilesView.class);
            //startActivity(i);
            return true;
//        case R.id.jump_to_menuitem:
//            showDialog(DIALOG_JUMP_TO_ID);
//            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    public interface IListener {
    	public void onNewestClicked(ShowImageView view);
    	public void onOldestClicked(ShowImageView view);
    	public void onNewerClicked(ShowImageView view);
    	public void onOlderClicked(ShowImageView view);
    }

}
