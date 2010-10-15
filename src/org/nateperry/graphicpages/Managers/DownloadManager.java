package org.nateperry.graphicpages.Managers;

import java.util.ArrayList;
import java.util.Stack;

import org.nateperry.graphicpages.util.DownloadTask;
import org.nateperry.graphicpages.util.DownloadItemInfo;
import org.nateperry.graphicpages.util.DownloadTask.eStatus;

public class DownloadManager {

	public static final int MAX_DOWNLOADS = 2;

	protected ArrayList<DownloadTask> _downloaders;
	protected Stack<DownloadItemInfo> _queue;
	protected DownloadManagerChangedListener _listener;
	protected DownloadTaskListener _taskListener;

	public DownloadManager() {
		_downloaders = new ArrayList<DownloadTask>();
		_queue = new Stack<DownloadItemInfo>();
		_taskListener = new DownloadTaskListener();
	}

	public void AddToQueue(DownloadItemInfo item) {
		_queue.push(item);
		updateQueue();
	}

	protected void updateQueue() {

		ArrayList<DownloadTask> completedTasks = new ArrayList<DownloadTask>();

		for (DownloadTask task : _downloaders) {
			if (task.isDownloadCompleted()) {
				completedTasks.add(task);
			}
		}

		for (DownloadTask task : completedTasks) {

			_downloaders.remove(task);
			task.clearOnChangedListener();
			if (task.wasError()) {
				onItemErrored(task.getDownloadItem());
			} else {
				onItemCompleted(task.getDownloadItem());
			}
		}

		while (_downloaders.size() < MAX_DOWNLOADS && 
				_queue.size() > 0) {

			DownloadItemInfo item = _queue.pop();
			DownloadTask task = new DownloadTask(item);
			task.setOnChangedListener(_taskListener);
			task.startDownload();
			_downloaders.add(task);

		}

	}

	public void setDownloadManagerChangedListener(
			DownloadManagerChangedListener l) {

		_listener = l;
	}

	public void clearDownloadManagerChangedListener() {
		_listener = null;
	}

	public void onItemCompleted(DownloadItemInfo item) {
		if (_listener != null) {
			_listener.onItemCompleted(this, item);
		}
	}

	public void onItemErrored(DownloadItemInfo item) {
		if (_listener != null) {
			_listener.onItemErrored(this, item);
		}
	}

	public void onItemDownloadedChanged(DownloadItemInfo task, int downloaded) {
		if (_listener != null) {
			_listener.onItemDownloadedChanged(this, task, downloaded);
		}
	}

	public void onItemSizeChanged(DownloadItemInfo task, int size) {
		if (_listener != null) {
			_listener.onItemSizeChanged(this, task, size);
		}
	}

	protected class DownloadTaskListener implements DownloadTask.OnChangeListener {

		public void onDownloadedChanged(DownloadTask task, int downloaded) {
			onItemDownloadedChanged(task.getDownloadItem(), downloaded);
		}

		public void onSizeChanged(DownloadTask task, int size) {
			onItemSizeChanged(task.getDownloadItem(), size);
		}

		public void onStatusChanged(DownloadTask task, eStatus status) {
			updateQueue();
		}

	}


	public interface DownloadManagerChangedListener {
		public void onItemCompleted(DownloadManager manager, DownloadItemInfo item);
		public void onItemErrored(DownloadManager manager, DownloadItemInfo item);
		public void onItemSizeChanged(DownloadManager manager, DownloadItemInfo task, int downloaded);
		public void onItemDownloadedChanged(DownloadManager manager, DownloadItemInfo task, int downloaded);
	}

}
