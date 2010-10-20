//package org.nateperry.graphicpages.mvc.view;
//
//import java.io.File;
//import java.net.URI;
//import java.util.ArrayList;
//
//import org.nateperry.graphicpages.library.IImageSet;
//
//import android.app.ListActivity;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Environment;
//import android.util.Log;
//import android.view.ContextMenu;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.Toast;
//import android.widget.AdapterView.AdapterContextMenuInfo;
//import android.widget.AdapterView.OnItemClickListener;
//
//public class ListFilesView extends ListActivity {
//
//	private ArrayAdapter<WrappedFile> _Adapter;
//	private UpdateAsyncTask _updateTask;
//	private PurgeFilesAsyncTask _purgeTask;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        _Adapter = new ArrayAdapter<WrappedFile>(this, R.layout.list_file, new ArrayList<WrappedFile>());
//        setListAdapter(_Adapter);
//
//        ListView lv = getListView();
//        lv.setTextFilterEnabled(true);
//		lv.setOnItemClickListener(ui_ListView_OnItemClickListener);
//		//lv.setOnItemLongClickListener(ui_ListView_OnItemLongClickListener);
//		registerForContextMenu(getListView());
//
//		Update();
//    }
//
//	private OnItemClickListener ui_ListView_OnItemClickListener =  new OnItemClickListener() {
//
//		public void onItemClick(
//				AdapterView<?> parent, 
//				View view, 
//				int position, 
//				long id) {
//
//			viewItem(position);
//
//		};
//
//	};
//
////	private OnItemLongClickListener ui_ListView_OnItemLongClickListener =  new OnItemLongClickListener() {
////
//////		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//////
//////		}
////
////		public boolean onItemLongClick(
////				AdapterView<?> parent, 
////				View view,
////				int position, 
////				long id) {
////
////			Object item = getListAdapter().getItem(position);
////			if (item instanceof WebComicPage) {
////				WebComicPage page = (WebComicPage)item;
////				
////				MenuInflater inflater = getMenuInflater();
////				inflater.inflate(R.menu.list_thumbnails_context_menu, menu);
////				
//////				WebComicInstance.SetIndex(page.GetId());
//////
//////				Intent i = new Intent(_thisActivity, GraphicPageViewerActivity.class);
//////				i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//////	            startActivity(i);
//////	            finish();
////			}
////
////	        //ListView lv = getListView();
////	        //lv.getItemAtPosition(position)
////        	//Intent i = new GoToPageIntent(this, ListFilesActivity.class, );
////
////			return false;
////		};
////	};
//
//    protected void Update() {
//
//    	if (_updateTask != null) _updateTask.cancel(false);
//		_updateTask = new UpdateAsyncTask();
//    	_updateTask.execute();
//    };
//
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.list_thumbnails_context_menu, menu);
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//
//    	AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
//
//    	File file = _Adapter.getItem(info.position);
//    	if (file.isDirectory()) {
//    		// Todo: enumerate directory.
//    	}
//
//        switch (item.getItemId()) {
//	        case R.id.delete_item:
//	        	deleteItem(info.position);
//	            return true;
//	        case R.id.view_item:
//	        	viewItem(info.position);
//	            return true;
//	        default:
//	            return super.onOptionsItemSelected(item);
//        }
//    };
//
//	public void deleteItem(int position) {
//
//		Object item = getListAdapter().getItem(position);
//		if (item instanceof WrappedFile) {
//
//			WrappedFile file = (WrappedFile)item;
//			file.delete();
//			Update();
//
//		}
//    };
//
//	private void viewItem(int position) {
//
//		Object item = getListAdapter().getItem(position);
//		if (item instanceof WrappedFile) {
//
//			WrappedFile file = (WrappedFile)item;
//			WebComicPage page = new WebComicPage(WebComicInstance.GetComic(), file.getName());
//			WebComicInstance.SetIndex(page.GetId());
//
//			GoToPageIntent i = new GoToPageIntent(_Activity, GraphicPageViewerActivity.class, page.GetId());
//			i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//            startActivity(i);
//            finish();
//
//		}
//	};
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.list_files_menu, menu);
//        return true;
//    };
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//        case R.id.purge:
//        	purgeAllItems();
//            return true;
//        default:
//            return super.onOptionsItemSelected(item);
//        }
//    };
//
//    protected void purgeAllItems() {
//
//       	if (_purgeTask != null) _purgeTask.cancel(false);
//       	_purgeTask = new PurgeFilesAsyncTask();
//       	_purgeTask.execute();
//    };
//
//	private class UpdateAsyncTask extends AsyncTask<Boolean, WrappedFile, Boolean> {
//
//		@Override
//		protected void onPreExecute () {
//
//        	Toast.makeText(getApplicationContext(), "Loading list...", Toast.LENGTH_SHORT).show();
//        	_Adapter.clear();
//		};
//
//		@Override
//		protected Boolean doInBackground(Boolean... params) {
//
//			try {
//
//				File dir = getFilesDir();
//	    		File xDir = null;
//
//		    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//
//			    	//File myDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES); // Android API 8 only
//
//			    	File xDirRoot = Environment.getExternalStorageDirectory();
//			    	xDir = new File(xDirRoot, Globals.EXTERNAL_DATA_FOLDER);
//
//		    	}
//
//		    	if (null != xDir) {
//		    		String[] list = xDir.list();
//		    		if (null != list) {
//
//		    			for (String item : list) {
//			    			publishProgress(new WrappedFile(xDir, item));
//		    			}
//		    		}
//		    	}
//
//		    	if (null != dir) {
//		    		String[] list = dir.list();
//		    		if (null != list) {
//
//		    			for (String item : list) {
//			    			publishProgress(new WrappedFile(dir, item));
//		    			}
//		    		}
//		    	}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//				Log.w("Listing all files failed", e);
//			}
//
//			return true;
//		};
//
//		@Override
//		protected void onPostExecute(Boolean result) {
//			_Adapter.sort(new FileWithIdComparator());
//		};
//
//		@Override
//		protected void onProgressUpdate (WrappedFile... values) {
//
//			for (WrappedFile file : values) {
//
//		    	if (file.isDirectory()) {
//		    		// Todo: List directories in a special way.
//		    	} else {
//		    		_Adapter.add(file);
//		    	}
//			}
//		};
//
//	};
//
//	private class PurgeFilesAsyncTask extends AsyncTask<Boolean, Boolean, Boolean> {
//
//		@Override
//		protected void onPreExecute() {
//
//        	Toast.makeText(getApplicationContext(), "Purging.", Toast.LENGTH_SHORT).show();
//		};
//
//		@Override
//		protected Boolean doInBackground(Boolean... params) {
//
//			try {
//
//	    		File xDir = null;
//
//		    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//
//			    	//File myDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES); // Android API 8 only
//
//			    	File xDirRoot = Environment.getExternalStorageDirectory();
//			    	xDir = new File(xDirRoot, Globals.EXTERNAL_DATA_FOLDER);
//
//		    	}
//
//		    	if (null != xDir) {
//		    		String[] list = xDir.list();
//		    		if (null != list) {
//
//		    			for (String item : list) {
//		    				File file = new File(xDir, item);
//		    				file.delete();
//		    			}
//
//				    	//files.addAll(Arrays.asList(list));
//		    		}
//		    	}
//
//				File dir = getFilesDir();
//
//		    	if (null != dir) {
//		    		String[] list = dir.list();
//		    		if (null != list) {
//
//		    			for (String item : list) {
//		    				File file = new File(dir, item);
//		    				file.delete();
//		    			}
//
//		    		}
//		    	}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//				Log.w("purging all files failed", e);
//			}
//
//			return true;
//		};
//
//		@Override
//		protected void onPostExecute(Boolean files) {
//
//			Update();
//		};
//
//		@Override
//		protected void onProgressUpdate (Boolean... values) {
//		};
//
//	};
//
//	public class WrappedFile {
//
//		protected final File _file;
//		protected final IImageSet _set;
//		protected final Integer _index;
//
//		public WrappedFile(File file, IImageSet set, Integer index) {
//			_file = file;
//			_set = set;
//			_index = index;
//		}
//
//		@Override
//		public String toString() {
//			return _file.getName();
//		}
//	}
//
//}
