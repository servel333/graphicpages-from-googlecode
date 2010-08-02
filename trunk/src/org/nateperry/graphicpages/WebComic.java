package org.nateperry.graphicpages;

public abstract class WebComic { //extends ContentProvider {

	// content://GraphicPage/<ComicName>/<Id>
	public static final String URI_NAMESPACE = "GraphicPage";
	public static final int TIMEOUT_MINS = 1;
	
	protected abstract int OnGetNewestId();

	public final int GetNewestId() {
		return OnGetNewestId();
	}

	protected abstract int OnGetOldestId();

	public final int GetOldestId() {
		return OnGetOldestId();
	}

	protected abstract int OnGetOlderId(int id);

	public final int GetOlderId(int id) {
		return OnGetOlderId(id);
	}

	protected abstract int OnGetNewerId(int id);
	
	public final int GetNewerId(int id) {
		return OnGetNewerId(id);
	}

	protected abstract String OnGetName();

	public final String GetName() {
		return OnGetName();
	}

	protected abstract String OnGetIdName();

	public final String GetIdName() {
		return OnGetIdName();
	}

	protected abstract String OnGetPageName(int id);

	public final String GetPageName(int id) {
		return OnGetPageName(id);
	}

	protected abstract String OnGetPageDescription(int id);
	
	public final String GetPageDescription(int id) {
		return OnGetPageDescription(id);
	}

	protected abstract String OnGetUrl();

	public final String GetUrl() {
		return OnGetUrl();
	}

	protected abstract String OnGetPageUrl(int id);

	public final String GetPageUrl(int id) {
		return OnGetPageUrl(id);
	}

	protected abstract String OnGetFileName(int id);
	
	public final String GetFileName(int id) {
		return OnGetFileName(id);
	}

	protected String OnGetFullName() {
		return R.string.app_full_name + "." + GetIdName();
	}

	public final String GetFullName() {
		return OnGetFullName();
	}
	
}
