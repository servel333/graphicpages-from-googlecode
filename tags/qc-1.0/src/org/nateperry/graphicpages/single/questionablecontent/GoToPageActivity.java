//package org.nateperry.graphicpages.single.questionablecontent;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.TextView;
//
//public class GoToPageActivity extends Activity {
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.go_to_page);
//
//     	//((Button)findViewById(R.id.ui_ok_Button)).setOnClickListener(ui_ok_Button_Click);
//
//    }
//
//	private void viewItem(int id) {
//
//		WebComicInstance.SetIndex(id);
//
//		GoToPageIntent i = new GoToPageIntent(this, GraphicPageViewerActivity.class, id);
//		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        startActivity(i);
//        finish();
//
//	};
//
//    private OnClickListener ui_ok_Button_Click = new OnClickListener()
//    {
//        public void onClick(View v)
//        {
//         	TextView text = ((TextView)findViewById(R.id.ui_select_TextView));
//         	int id = Integer.parseInt(text.getText().toString());
//         	viewItem(id);
//        }
//    };
//
//}
