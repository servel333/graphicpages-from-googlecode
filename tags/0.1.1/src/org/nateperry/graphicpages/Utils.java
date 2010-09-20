package org.nateperry.graphicpages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Utils {
	
	public static final String PACKAGE_NAME = "org.nateperry.graphicpages";

	//public static InputStream wget(String imageUrl)
	//{
	//	InputStream stream = null;
	//
	//	try 
	//	{
	//		URL url = null;
	//		url= new URL(imageUrl);
	//		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	//		connection.setDoInput(true);
	//		connection.connect();
	//		stream = connection.getInputStream();
	//	} 
	//	catch (MalformedURLException e) 
	//	{
	//		e.printStackTrace();
	//	}
	//	catch (IOException e) 
	//	{
	//		e.printStackTrace();
	//	}
	//
	//	return stream;
	//};

    ///** downloads the image and returns the drawable. */
	//public static BitmapDrawable wget_bitmap(String imageUrl)
	//{
	//	InputStream stream = wget(imageUrl);
	//	BitmapDrawable image = null;
	//	image = new BitmapDrawable(stream);
	//	return image;
	//};

	public static BufferedReader getReader(String pageUrl) 
			throws MalformedURLException, IOException {
		
		URL page = new URL(pageUrl);;
		InputStreamReader isreader = new InputStreamReader(page.openStream());
		BufferedReader reader = new BufferedReader(isreader);
		
		return reader;
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
	public static String FindOnPage(
			String pageUrl, 
			String expr) 
			throws MalformedURLException, IOException {

		return FindOnPage(pageUrl, expr, 0);
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
	public static String FindOnPage(
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
	public static String FindOnPage_Safe(
			String pageUrl, 
			String expr) {
		
		return FindOnPage_Safe(pageUrl, expr, 0);
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
	public static String FindOnPage_Safe(
			String pageUrl, 
			String expr, 
			int skipMatches) {
		
		try {
			return FindOnPage(pageUrl, expr, skipMatches);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}

	public static boolean IsNullOrEmpty(String text) {
		return (text == null) | (text == "");
	}

	public static boolean TryParse(String s, Integer i) {
		
		try {
			i = Integer.parseInt(s);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	// From http://android-developers.blogspot.com/2010/07/multithreading-for-performance.html
	static Bitmap downloadBitmap(String url) {
		
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
}
