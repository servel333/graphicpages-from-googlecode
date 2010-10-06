package org.nateperry.graphicpages.view;

import org.nateperry.graphicpages.model.ViewImageActivity_model;
import org.nateperry.graphicpages.model.ViewImageActivity_model.Action;
import org.nateperry.graphicpages.single.questionablecontent.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class ViewImageActivity_view extends Activity {

	private ViewImageActivity_model _model;

	public ViewImageActivity_view(ViewImageActivity_model model) {
		_model = model;
	}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_comic);

     	((Button)findViewById(R.id.ui_newest_Button)).setOnClickListener(_model.getOnClickListener(Action.NEWEST));
     	((Button)findViewById(R.id.ui_newer_Button)).setOnClickListener(_model.getOnClickListener(Action.NEWER));
     	((Button)findViewById(R.id.ui_oldest_Button)).setOnClickListener(_model.getOnClickListener(Action.OLDEST));
     	((Button)findViewById(R.id.ui_older_Button)).setOnClickListener(_model.getOnClickListener(Action.OLDER));

    }

}
