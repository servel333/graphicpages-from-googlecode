package org.nateperry.graphicpages;

public class DownloadQueueInstance {

	private static volatile DownloadQueue _queue;
	
	public static DownloadQueue Queue() {
		
		if (_queue == null) {
			_queue = new DownloadQueue();
		}
		
		return _queue;
	}
	
}
