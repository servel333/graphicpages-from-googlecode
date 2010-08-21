package org.nateperry.graphicpages;

import java.util.Calendar;

public abstract class ImageLot { //extends ContentProvider {

	// content://GraphicPage/<ComicName>/<Id>
	public static final String URI_NAMESPACE = "GraphicPage";
	public static final int TIMEOUT_MINS = 30;
	
	protected Calendar _lastGotNewestId;
	protected int _newestId;
	
	public ImageLot() {
		_lastGotNewestId = null;
	}
	
	public ImageLot(ImageLot comic) {
		_lastGotNewestId = (Calendar)comic._lastGotNewestId.clone();
		_newestId = comic._newestId;
	}

	protected abstract int onGetNewestId();
	protected abstract int onGetOldestId();
	protected abstract int onMoveId(int id, int count);
	protected abstract String onGetName();
	protected abstract String onGetIdName();
	protected abstract String onGetPageName(int id);
	protected abstract String onGetPageDescription(int id);
	protected abstract String onGetUrl();
	protected abstract String onGetPageUrl(int id);
	protected abstract String onGetFileName(int id);
	protected abstract int onParseFileName(String filename);
	
	@Override
	public abstract Object clone();

	public final int getNewestId() {
		if (_lastGotNewestId == null) {
			
			_lastGotNewestId = Calendar.getInstance();
			_newestId = onGetNewestId();
			
		} else {
			
			Calendar timeout = Calendar.getInstance();
			timeout.add(Calendar.MINUTE, TIMEOUT_MINS);
			
			if (_lastGotNewestId.after(timeout)) {
				_newestId = onGetNewestId();
			}
		}
		return _newestId;
	}

	public final int getNewestId(boolean forceUpdate) {
		if (forceUpdate) {
			_lastGotNewestId = Calendar.getInstance();
			_newestId = onGetNewestId();
		} else {
			if (_lastGotNewestId == null) {
				
				_lastGotNewestId = Calendar.getInstance();
				_newestId = onGetNewestId();
				
			} else {
				
				Calendar timeout = Calendar.getInstance();
				timeout.add(Calendar.MINUTE, TIMEOUT_MINS);
				
				if (_lastGotNewestId.after(timeout)) {
					_lastGotNewestId = Calendar.getInstance();
					_newestId = onGetNewestId();
				}
			}
		}

		return _newestId;
	}

	public final int getOldestId() {
		return onGetOldestId();
	}

	public final int moveId(int id, int count) {
		return onMoveId(id, count);
	}
	
	public final String getName() {
		return onGetName();
	}

	public final String getIdName() {
		return onGetIdName();
	}

	public final String getPageName(int id) {
		return onGetPageName(id);
	}
	
	public final String getPageDescription(int id) {
		return onGetPageDescription(id);
	}

	public final String getUrl() {
		return onGetUrl();
	}

	public final String getPageUrl(int id) {
		return onGetPageUrl(id);
	}
	
	public final String getFileName(int id) {
		return onGetFileName(id);
	}
	
	public final int parseFileName(String filename) {
		return onParseFileName(filename);
	}
	
	protected String onGetFullName() {
		return R.string.app_full_name + "." + getIdName();
	}

	public final String getFullName() {
		return onGetFullName();
	}
	
}
