package org.nateperry.graphicpages;

public class WebComicPage {

	private WebComic _comic;
	private String _filename;
	private int _id;
	
	public WebComicPage(WebComic comic, String filename) {
		
		_comic = comic;
		_filename = filename;
		_id = comic.ParseFileName(filename);
		
	}
	
	public WebComicPage(WebComic comic, int id) {
		
		_comic = comic;
		_id = id;
		_filename = _comic.GetFileName(_id);
		
	}
	
	public String GetFileName() {
		return _filename;
	}
	
	public int GetId() {
		return _id;
	}
	
	public WebComic GetComic() {
		return _comic;
	}
	
	public String toString(){
		return "(" + _id + ") " + _filename;
	}

}
