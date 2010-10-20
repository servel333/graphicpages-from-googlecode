//package org.nateperry.graphicpages.single.questionablecontent;
//
//public class WebComicPage {
//
//	private WebComic _comic;
//	private String _filename;
//	private int _id;
//	
//	public WebComicPage(WebComic comic, String filename) {
//		
//		_comic = comic;
//		_filename = filename;
//		_id = comic.ParseFileName(filename);
//	}
//	
//	public WebComicPage(WebComic comic, int id) {
//		
//		_comic = comic;
//		_id = id;
//		_filename = _comic.GetFileName(_id);
//	}
//	
//	public WebComicPage(WebComic comic, int id, String filename) {
//		
//		_comic = comic;
//		_id = id;
//		_filename = filename;
//	}
//	
//	public WebComicPage(WebComicPage page) {
//		
//		_comic = (WebComic)page._comic.clone();
//		_id = page._id;
//		_filename = new String(page._filename);
//	}
//	
//	public String GetFileName() {
//		return _filename;
//	}
//	
//	public int GetId() {
//		return _id;
//	}
//	
//	public WebComic GetComic() {
//		return _comic;
//	}
//	
//	public String toString() {
//		return _filename;
//	}
//	
//	@Override
//	public Object clone() {
//		return new WebComicPage(this);
//	}
//
//}
