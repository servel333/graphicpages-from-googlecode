//package org.nateperry.graphicpages.managers;
//
//import org.nateperry.graphicpages.library.IImageSet;
//
//public class ImageSetManager {
//
//	protected Integer _index;
//	protected Integer _newestIndex;
//	protected IImageSet _set;
//
//	public ImageSetManager(IImageSet set) {
//		_set = set;
//		_index = set.getDefaultIndex();
//		_newestIndex = set.getDefaultIndex();
//	}
//
//	public IImageSet getImageSet() {
//		return _set;
//	}
//
//	public void setImageSet(IImageSet set) {
//		_set = set;
//	}
//
//	public Integer getIndex() {
//		return _index;
//	}
//
//	public void setIndex(Integer index) {
//		_index = index;
//	}
//
//	public Integer getDefaultIndex() {
//		return _set.getDefaultIndex();
//	}
//
//	public String getFileName() {
//		return _set.getFileName(_index);
//	}
//
//	public String getIndexName() {
//		return _set.getIndexName();
//	}
//
//	public String getName() {
//		return _set.getName();
//	}
//
//	public Integer getNewestIndex() {
//		_newestIndex = _set.getNewestIndex(_newestIndex); 
//		return _newestIndex; 
//	}
//
//	public Integer getOldestIndex() {
//		return _set.getOldestIndex();
//	}
//
//	public String getPageDescription() {
//		return _set.getPageDescription(_index);
//	}
//
//	public String getPageName() {
//		return _set.getPageName(_index);
//	}
//
//	public String getPageUrl() {
//		return _set.getPageUrl(_index);
//	}
//
//	public String getUrl() {
//		return _set.getUrl();
//	}
//
//	public Integer parseFileName(String filename) {
//		return _set.parseFileName(filename);
//	}
//
//}
