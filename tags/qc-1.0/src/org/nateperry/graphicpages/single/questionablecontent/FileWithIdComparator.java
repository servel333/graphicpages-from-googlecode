package org.nateperry.graphicpages.single.questionablecontent;

import java.io.File;
import java.util.Comparator;

public class FileWithIdComparator implements Comparator<File> {

	public int compare(File left, File right) {

		Integer leftId = WebComicInstance.GetComic().ParseFileName(left.getName());
		Integer rightId = WebComicInstance.GetComic().ParseFileName(right.getName());

		return new Integer(leftId.compareTo(rightId));
	}

}
