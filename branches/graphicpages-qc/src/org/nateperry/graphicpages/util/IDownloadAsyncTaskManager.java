package org.nateperry.graphicpages.util;

import org.nateperry.graphicpages.util.eDownloadStatus;

public interface IDownloadAsyncTaskManager {

	public eDownloadStatus getDownloadStatus();
	public void setDownloadStatus(eDownloadStatus status);
	public DownloadItemInfo getDownloadItemInfo();
	public void onStatusChanged(eDownloadStatus status);
	public void onDownloadedChanged(int downloaded);
	public void onSizeChanged(int size);

}
