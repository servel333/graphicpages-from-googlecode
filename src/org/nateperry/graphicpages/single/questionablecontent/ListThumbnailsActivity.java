//package org.nateperry.graphicpages.single.questionablecontent;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.widget.GridView;
//
//public class ListThumbnailsActivity extends Activity {
//
//	public void onCreate(Bundle savedInstanceState) {
//	    super.onCreate(savedInstanceState);
//	    
//	    setContentView(R.layout.list_thumbnails);
//
//	    GridView gridview = (GridView) findViewById(R.id.gridview);
//	    gridview.setAdapter(new ImageAdapter(this));
//
//	    gridview.setOnItemClickListener(new OnItemClickListener() {
//	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//	            Toast.makeText(HelloGridView.this, "" + position, Toast.LENGTH_SHORT).show();
//	        }
//	    });
//
//
//	}	
//	
//}
