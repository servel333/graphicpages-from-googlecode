package org.nateperry.graphicpages;

import android.content.ContentValues;
import android.database.Cursor;

public class QuestionableContent extends WebComic {

	public static final String FULL_NAME = R.string.app_full_name + "." + ID_NAME;
	public static final String QC_IMAGE_NAME = R.string.app_full_name + "/" + ID_NAME + "/%s";
	public static final String QC_IMAGE_DESCRIPTION = NAME + " comic number %s";

	@Override
	public String IdName() {
		return "QuestionableContent";
	}

	@Override
	public String Name() {
		return "Questionable Content";
	}

	@Override
	public String PageUrl(int id) {
		return String.format("http://www.questionablecontent.net/comics/%s.png", id);
	}

	@Override
	public String Url() {
		return "http://www.questionablecontent.net";
	}

	@Override
	public String UriName() {
		return null;
	}

	@Override
	public int NewerId(int id) {
		return 0;
	}

	@Override
	public int NewestId() {

		wget(Url());

		// http://www.questionablecontent.net/comics/1705.png

		return 0;
	}

	@Override
	public int OlderId(int id) {
		return 0;
	}

	@Override
	public int OldestId() {
		return 0;
	}

}
