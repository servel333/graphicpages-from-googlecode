package org.nateperry.graphicpages;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.drawable.BitmapDrawable;

public class Utils {

    static InputStream wget(String imageUrl)
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
    static BitmapDrawable wget_bitmap(String imageUrl)
	{
		InputStream stream = wget(imageUrl);
		BitmapDrawable image = null;
		image = new BitmapDrawable(stream);
		return image;
	};

    static String wget_html(String imageUrl)
	{
		return null;
	};

}
