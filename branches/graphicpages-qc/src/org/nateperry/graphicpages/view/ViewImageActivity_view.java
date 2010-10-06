package org.nateperry.graphicpages.view;

import org.nateperry.graphicpages.model.ViewImageActivity_model;
import org.nateperry.graphicpages.single.questionablecontent.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ViewImageActivity_view extends Activity {

	private ViewImageActivity_model _model;

	public ViewImageActivity_view() {
		
	}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_comic);

     	((Button)findViewById(R.id.ui_newest_Button)).setOnClickListener();

    }

    interface 

}
