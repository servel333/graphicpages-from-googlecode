package org.nateperry.graphicpages;

import org.nateperry.graphicpages.managers.DownloadManager;
import org.nateperry.graphicpages.managers.FileManager;

/**
 * Single Instance Classes. 
 */
public class I {

	public static final DownloadManager downloadManager = new DownloadManager();
	public static final FileManager fileManager = new FileManager();

}
