package org.nateperry.graphicpages;

import android.graphics.Bitmap;
import android.os.Environment;

public class FileSystemUtils {

	public static boolean SaveBitmap(String name, Bitmap image) {

    	//File dir;
    	//File urlPage = new File(pageUrl);
    	//String relPath = _comic.GetName() + "/" + _current + "-" + urlPage.getName();
    	//String relFile = _comic.GetName() + "-" + _current + "-" + urlPage.getName();
    	//urlPage = null;

    	//FileOutputStream out;

    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
    		
	    	//dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
	    	
	    	//File file = new File(dir, relPath);
	    	//file.createNewFile();
	    	//out = new FileOutputStream(name);
	    	
    	} else {
    		
        	//name.replace("/", "-");
        	//name.replace("\\", "-");
	    	//dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
	    	
	    	//out = openFileOutput(name, Context.MODE_PRIVATE);
    	}

    	//image.compress(CompressFormat.JPEG, 75, out);
    	//out.flush();
    	//out.close();

    	return false;
	}
	
}
