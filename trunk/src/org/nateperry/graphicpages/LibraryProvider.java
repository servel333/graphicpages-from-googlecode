package org.nateperry.graphicpages;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class LibraryProvider extends ContentProvider {

	public static final String URI = "content://" + R.string.app_full_name + "/page/%s/%s"; //  /comic/<name>/<id>

	@Override
	public int delete(
			Uri uri, 
			String selection, 
			String[] selectionArgs) {

		return 0;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public boolean onCreate() {
		return false;
	}

	@Override
	public Cursor query(
			Uri uri, 
			String[] projection, 
			String selection,
			String[] selectionArgs, 
			String sortOrder) {

		return null;
	}

	@Override
	public int update(
			Uri uri, 
			ContentValues values, 
			String selection,
			String[] selectionArgs) {

		return 0;
	}

    public static String GetUri(int number) {
    	return String.format(URI, QC_NAME, "" + number);
    };

    public static String GetUrl(int number) {
    	Log.i("Comic URL", String.format(QC_URL, "" + number));
    	return String.format(QC_URL, number);
    };

    public static String GetName(int number) {
    	return String.format(QC_IMAGE_NAME, number);
    };

    public static String GetDescription(int number) {
    	return String.format(QC_IMAGE_DESCRIPTION, number);
    };

}
