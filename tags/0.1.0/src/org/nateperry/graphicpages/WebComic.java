package org.nateperry.graphicpages;

import java.util.Calendar;

public abstract class WebComic { //extends ContentProvider {

	// content://GraphicPage/<ComicName>/<Id>
	public static final String URI_NAMESPACE = "GraphicPage";
	public static final int TIMEOUT_MINS = 30;
	
	public Calendar _lastGotNewestId = null;
	public int _newestId;
	
	protected abstract int OnGetNewestId();

	public final int GetNewestId() {
		if (_lastGotNewestId == null) {
			
			_lastGotNewestId = Calendar.getInstance();
			_newestId = OnGetNewestId();
			
		} else {
			
			Calendar timeout = Calendar.getInstance();
			timeout.add(Calendar.MINUTE, TIMEOUT_MINS);
			
			if (_lastGotNewestId.after(timeout)) {
				_newestId = OnGetNewestId();
			}
		}
		return _newestId;
	}

	public final int GetNewestId(boolean forceUpdate) {
		if (forceUpdate) {
			_lastGotNewestId = Calendar.getInstance();
			_newestId = OnGetNewestId();
		} else {
			if (_lastGotNewestId == null) {
				
				_lastGotNewestId = Calendar.getInstance();
				_newestId = OnGetNewestId();
				
			} else {
				
				Calendar timeout = Calendar.getInstance();
				timeout.add(Calendar.MINUTE, TIMEOUT_MINS);
				
				if (_lastGotNewestId.after(timeout)) {
					_lastGotNewestId = Calendar.getInstance();
					_newestId = OnGetNewestId();
				}
			}
		}

		return _newestId;
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
