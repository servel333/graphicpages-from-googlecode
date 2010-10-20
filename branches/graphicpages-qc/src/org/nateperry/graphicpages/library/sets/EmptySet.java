package org.nateperry.graphicpages.library.sets;

import org.nateperry.graphicpages.library.IImageSet;

public class EmptySet implements IImageSet {

	public Integer getDefaultIndex() {
		return 0;
	}

	public String getFileName(Integer index) {
		return "";
	}

	public String getIndexName() {
		return "";
	}

	public String getName() {
		return "Empty";
	}

	public Integer getNewestIndex(Integer latest) {
		return 0;
	}

	public Integer getOldestIndex() {
		return 0;
	}

	public String getPageDescription(Integer index) {
		return "";
	}

	public String getPageName(Integer index) {
		return "";
	}

	public String getPageUrl(Integer index) {
		return "";
	}

	public String getUrl() {
		return "";
	}

	public Integer parseFileName(String filename) {
		return 0;
	}

}
