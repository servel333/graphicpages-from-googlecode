package org.nateperry.graphicpages;

public abstract class WebComic { //extends ContentProvider {

	// content://GraphicPage/<ComicName>/<Id>
	public static final String URI_NAMESPACE = "GraphicPage";

	public abstract int NewestId();
	public abstract int OldestId();
	public abstract int NewerId(int id);
	public abstract int OlderId(int id);

	public abstract String Name();
	public abstract String IdName();
	
	public String FullName() {
		return R.string.app_full_name + "." + IdName();
	};
	
	public abstract String Url();
	public abstract String PageUrl(int id);

	public abstract String UriName();

}
