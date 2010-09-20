package org.nateperry.graphicpages;

import java.util.Comparator;

public class WebComicPageComparator implements Comparator<WebComicPage> {

	public int compare(WebComicPage left, WebComicPage right) {
		return new Integer(left.GetId()).compareTo(right.GetId());
	}
	
}
