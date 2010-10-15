package org.nateperry.graphicpages.managers;

import java.util.ArrayList;
import java.util.Stack;

import org.nateperry.graphicpages.util.DownloadAsyncTaskManager;
import org.nateperry.graphicpages.util.DownloadItemInfo;

public class DownloadManager {

	public static final int MAX_DOWNLOADS = 2;

	protected ArrayList<DownloadAsyncTaskManager> _downloaders;
	protected Stack<DownloadItemInfo> _queue;
	protected DownloadManagerChangedListener _listener;
	protected DownloadTaskListener _taskListener;

	public DownloadManager() {
		_downloaders = new ArrayList<DownloadAsyncTaskManager>();
		_queue = new Stack<DownloadItemInfo>();
		_taskListener = new DownloadTaskListener();
	}

	public void AddToQueue(DownloadItemInfo item) {
		_queue.push(item);
		updateQueue();
	}

	protected void updateQueue() {

		ArrayList<DownloadAsyncTaskManager> completedTasks = new ArrayList<DownloadAsyncTaskManager>();

		for (DownloadAsyncTaskManager task : _downloaders) {
			if (task.isDownloadCompleted()) {
				completedTasks.add(task);
			}
		}

		for (DownloadAsyncTaskManager task : completedTasks) {

			_downloaders.remove(task);
			task.clearOnChangedListener();
			if (task.wasError()) {
				onItemErrored(task.getDownloadItemInfo());
			} else {
				onItemCompleted(task.getDownloadItemInfo());
			}
		}

		while (_downloaders.size() < MAX_DOWNLOADS && 
				_queue.size() > 0) {

			DownloadItemInfo item = _queue.pop();
			DownloadAsyncTaskManager task = new DownloadAsyncTaskManager(item);
			task.setOnChangeListener(_taskListener);
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

	protected class DownloadTaskListener implements DownloadAsyncTaskManager.OnChangeListener {

		public void onDownloadedChanged(DownloadAsyncTaskManager task, int downloaded) {
			onItemDownloadedChanged(task.getDownloadItemInfo(), downloaded);
		}

		public void onSizeChanged(DownloadAsyncTaskManager task, int size) {
			onItemSizeChanged(task.getDownloadItemInfo(), size);
		}

		public void onStatusChanged(
				DownloadAsyncTaskManager task,
				boolean isPaused, 
				boolean isComplete, 
				boolean isError) {

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
