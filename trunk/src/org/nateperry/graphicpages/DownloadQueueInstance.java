package org.nateperry.graphicpages;

import android.content.ContextWrapper;

public class DownloadQueueInstance {

	private static volatile DownloadQueue _queue;

	public static void InitializeQueue(ContextWrapper context) {

		if (_queue == null) {
			_queue = new DownloadQueue(context);
		}
	}

	public static DownloadQueue Queue() {

		return _queue;
	}

}
