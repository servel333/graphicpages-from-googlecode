//package org.nateperry.graphicpages;
//
//import org.nateperry.graphicpages.GraphicPageViewerActivity.Action;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.NumberPicker;
//
//public class GoToPageActivity extends Activity {
//
//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.go_to_page);
//        
//        //NumberPicker select = (NumberPicker)findViewById(R.id.ui_select_NumberPicker);
//    };
//
//    @Override
//    public void onStart() {
//    	super.onStart();
//    };
//
//    @Override
//    public void onResume() {
//    	super.onResume();
//    	
//    	Intent intent = getIntent();
//    	
//    	if (intent instanceof GoToPageIntent) {
//    		setIntent(intent);
//    		WebComicInstance.SetIndex(((GoToPageIntent)intent).GetIndex());
//    	}
//    	
//    	Update(Action.UPDATE);
//    };
//
//    @Override
//    public void onPause() {
//    	
//    	if (_updateTask != null) _updateTask.cancel(false);
//    	
//    	super.onPause();
//    };
//
//    @Override
//    public void onStop() {
//    	super.onStop();
//    };
//
//    @Override
//    public void onDestroy() {
//    	super.onDestroy();
//    }
//
//}
