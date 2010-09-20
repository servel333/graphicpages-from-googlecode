package org.nateperry.graphicpages.single.questionablecontent;

public class WebComicInstance {
	
	private volatile static WebComic _comic;
	private volatile static int _index = -1;
	
	public static WebComic GetComic() {
		if (_comic == null) {
			_comic = new QuestionableContentWebComic();
		}
		return _comic;
	}
	
	public static void SetComic(WebComic comic) {
		_comic = comic;
	}
	
	public static int GetIndex() {
		if (_index == -1) {
			SetNewestId();
		}
		return _index;
	}
	
	public static void SetIndex(int index) {
		_index = index;
	}
	
	public static void SetNewestId() {
		_index = GetComic().GetNewestId();
	}
	
	public static void SetNewestId(boolean forceUpdate) {
		_index = GetComic().GetNewestId(forceUpdate);
	}
	
	public static void SetOldestId() {
		_index = GetComic().GetOldestId();
	}
	
	public static void SetOlderId() {
		_index = GetComic().GetOlderId(_index);
	}
	
	public static void SetNewerId() {
		_index = GetComic().GetNewerId(_index);
	}
	
	public static String GetPageName() {
		return GetComic().GetPageName(_index);
	}
	
	public static String GetPageDescription() {
		return GetComic().GetPageDescription(_index);
	}
	
	public static String GetPageUrl() {
		return GetComic().GetPageUrl(_index);
	}
	
	public static String GetFileName() {
		return GetComic().GetFileName(_index);
	}
	
}
