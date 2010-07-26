package org.nateperry.graphicpages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.drawable.BitmapDrawable;

public class Utils {

	public static InputStream wget(String imageUrl)
	{
		URL url = null;
		InputStream stream = null;

		try 
		{
			url= new URL(imageUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			stream = connection.getInputStream();
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		return stream;
	};

    /** downloads the image and returns the drawable. */
	public static BitmapDrawable wget_bitmap(String imageUrl)
	{
		InputStream stream = wget(imageUrl);
		BitmapDrawable image = null;
		image = new BitmapDrawable(stream);
		return image;
	};

    public static String wget_html(String pageUrl)
	{
		return null;
	};

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

	
}
