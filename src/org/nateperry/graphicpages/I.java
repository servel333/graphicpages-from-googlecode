package org.nateperry.graphicpages;

import org.nateperry.graphicpages.library.IImageSet;
import org.nateperry.graphicpages.library.sets.QuestionableContentSet;
import org.nateperry.graphicpages.managers.DownloadManager;
import org.nateperry.graphicpages.managers.FileManager;

/**
 * Single Instance Classes. 
 */
public class I {

	public static final DownloadManager downloadManager = new DownloadManager();
	public static final FileManager fileManager = new FileManager();
//	public static final IImageSet defaultImageSet = new EmptySet();
	public static final IImageSet defaultImageSet = new QuestionableContentSet();

}
