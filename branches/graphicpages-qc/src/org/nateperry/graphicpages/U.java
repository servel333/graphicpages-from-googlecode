package org.nateperry.graphicpages;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

/**
 * Utility Functions
 */
public class U {

	/**
	 * Determines if the string is either Null or an empty string.
	 * 
	 * @param text  The string to evaluate.
	 * @return  True if the string is Null or empty; otherwise False.
	 */
	public static boolean isNullOrEmpty(String text) {
		return (text == null) | (text == "");
	}

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

	public static BufferedReader getReader(String pageUrl) 
			throws MalformedURLException, IOException {

		URL page = new URL(pageUrl);;
		InputStreamReader isreader = new InputStreamReader(page.openStream());
		BufferedReader reader = new BufferedReader(isreader);

		return reader;
	}

	// From http://android-developers.blogspot.com/2010/07/multithreading-for-performance.html
	public static Bitmap downloadBitmap(String url) {
		
		//final AndroidHttpClient client = AndroidHttpClient("Android"); // Android API 8 only
	    final DefaultHttpClient client = new DefaultHttpClient();
	    final HttpGet getRequest = new HttpGet(url);

	    try {
	        HttpResponse response = client.execute(getRequest);
	        final int statusCode = response.getStatusLine().getStatusCode();
	        if (statusCode != HttpStatus.SC_OK) { 
	            Log.w("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url); 
	            return null;
	        }
	        
	        final HttpEntity entity = response.getEntity();
	        if (entity != null) {
	            InputStream inputStream = null;
	            try {
	                inputStream = entity.getContent(); 
	                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
	                return bitmap;
	            } finally {
	                if (inputStream != null) {
	                    inputStream.close();  
	                }
	                entity.consumeContent();
	            }
	        }
	    } catch (Exception e) {
	        // Could provide a more explicit error message for IOException or IllegalStateException
	        getRequest.abort();
	        //Log.w("ImageDownloader", "Error while retrieving bitmap from " + url, e.toString());
	    } finally {
	        if (client != null) {
	            //client.close(); // Android API 8 only
	        }
	    }
	    return null;
	}

	/**
	 * Searches for the specified expression and returns the first line that
	 * matches.
	 * 
	 * @param pageUrl 		The URL of the page to search.
	 * @param expr 			A expression to match. (String.matches) 
	 * 
	 * @return 	returns the first match or "".
	 */
	public static String findOnPage(
			String pageUrl, 
			String expr) 
			throws MalformedURLException, IOException {

		return findOnPage(pageUrl, expr, 0);
	}

	/**
	 * Searches for the specified expression and returns the first line after
	 * skipping lines that matches the expression.
	 * 
	 * @param pageUrl 		The URL of the page to search.
	 * @param expr 			A expression to match. (String.matches) 
	 * @param skipMatches	The number of matches to skip. 
	 * @return 	Skips the specified number of matches and then returns the next
	 * 			match or "".
	 */
	public static String findOnPage(
			String pageUrl, 
			String expr, 
			int skipMatches)
			throws MalformedURLException, IOException {
		
		String found = "";
		
		BufferedReader reader = getReader(pageUrl);
		String line = reader.readLine();
		while (null != line) {
			if (line.matches(expr)) {
				if (0 > skipMatches) {
					skipMatches--;
				} else {
					found = line;
					break;
				}
			}
			line = reader.readLine();
		}
		
		return found;
	}

	/**
	 * Searches for the specified expression and returns the first line that
	 * matches.  Does not throw any exceptions.
	 * 
	 * @param pageUrl 		The URL of the page to search.
	 * @param expr 			A expression to match. (String.matches) 
	 * @param skipMatches	The number of matches to skip. 
	 * @return 	Skips the specified number of matches and then returns the next
	 * 			match or "".
	 */
	public static String safeFindOnPage(
			String pageUrl, 
			String expr) {
		
		return safeFindOnPage(pageUrl, expr, 0);
	}

	/**
	 * Searches for the specified expression and returns the first line that
	 * matches.  Does not throw any exceptions.
	 * 
	 * @param pageUrl 		The URL of the page to search.
	 * @param expr 			A expression to match. (String.matches) 
	 * @param skipMatches	The number of matches to skip. 
	 * @return 	Skips the specified number of matches and then returns the next
	 * 			match or "".
	 */
	public static String safeFindOnPage(
			String pageUrl, 
			String expr, 
			int skipMatches) {
		
		try {
			return findOnPage(pageUrl, expr, skipMatches);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}

	public static String[] getStoredFiles(ContextWrapper context) {

		ArrayList<String> list = new ArrayList<String>();

		String[] tempList = getInternalStoredFiles(context);

		for (String item: tempList) {
			list.add(item);
		}

		tempList = getExternalStoredFiles();

		for (String item: tempList) {
			list.add(item);
		}

		return (String[]) list.toArray();
	}

	public static String[] getInternalStoredFiles(ContextWrapper context) {

		String[] list = null;

		try {

			File dir = context.getFilesDir();

	    	if (null != dir) {
	    		list = dir.list();
	    	}

		} catch (Exception e) {
			e.printStackTrace();
			Log.w("getInternalStoredFiles error", e);
		}

		if (list == null) { list = new String[] {}; }

		return list;
	}

	public static String[] getExternalStoredFiles() {

		String[] list = null;

		try {

    		File xDir = null;

	    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

		    	//File myDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES); // Android API 8 only

		    	File xDirRoot = Environment.getExternalStorageDirectory();
		    	xDir = new File(xDirRoot, G.EXTERNAL_DATA_FOLDER);

	    	}

	    	if (null != xDir) {
	    		list = xDir.list();
	    	}

		} catch (Exception e) {
			e.printStackTrace();
			Log.w("getExternalStoredFiles error", e);
		}

		if (list == null) { list = new String[] {}; }

		return list;
	}

	public static boolean existsOnInternal(ContextWrapper context, String file) {

		File iDir = context.getFilesDir();
		File iFile = new File(iDir, file);
		return iFile != null && iFile.exists();

	}

	public static boolean existsOnExternal(String file) {

		File xDir = null;
		File xFile = null;

    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

	    	//File myDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES); // Android API 8 only

	    	File xRootDir = Environment.getExternalStorageDirectory();
	    	xDir = new File(xRootDir, G.EXTERNAL_DATA_FOLDER);
    		xFile = new File(xDir, file);

    	}

    	return xFile != null && xFile.exists();
	}

	public static File getPreferredFileDirectory(ContextWrapper context) {

		File dir = null;

		switch (G.PREFERRED_FILE_LOCATION) {
			case INTERNAL:

				dir = context.getFilesDir();
				break;

			case EXTERNAL:

		    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

			    	File xRootDir = Environment.getExternalStorageDirectory();
			    	dir = new File(xRootDir, G.EXTERNAL_DATA_FOLDER);

		    	} else {

					dir = context.getFilesDir();
		    		
		    	}
				break;
		}

		return dir;
	}

//	public static InputStream wget(String imageUrl)
//	{
//		InputStream stream = null;
//	
//		try 
//		{
//			URL url = null;
//			url= new URL(imageUrl);
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//			connection.setDoInput(true);
//			connection.connect();
//			stream = connection.getInputStream();
//		} 
//		catch (MalformedURLException e) 
//		{
//			e.printStackTrace();
//		}
//		catch (IOException e) 
//		{
//			e.printStackTrace();
//		}
//	
//		return stream;
//	};

//    /** downloads the image and returns the drawable. */
//	public static BitmapDrawable wget_bitmap(String imageUrl)
//	{
//		InputStream stream = wget(imageUrl);
//		BitmapDrawable image = null;
//		image = new BitmapDrawable(stream);
//		return image;
//	};

//	public static boolean tryParse(String s, GenericContainer<Integer> i) {
//
//		try {
//			i.setValue(Integer.parseInt(s));
//		} catch (Exception e) {
//			return false;
//		}
//
//		return true;
//	}

}
