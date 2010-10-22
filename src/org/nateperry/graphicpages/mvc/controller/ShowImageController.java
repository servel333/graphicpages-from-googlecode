package org.nateperry.graphicpages.mvc.controller;

import org.nateperry.graphicpages.I;
import org.nateperry.graphicpages.mvc.model.ShowImageModel;
import org.nateperry.graphicpages.mvc.view.ShowImageView;
import org.nateperry.graphicpages.util.DownloadItemInfo;

public class ShowImageController {

	protected final ShowImageView _view;
	protected final ShowImageModel _model;
	protected final ViewListener _viewListener;

	public ShowImageController(
			ShowImageView view,
			ShowImageModel model) {

		_view = view;
		_viewListener = new ViewListener();
		_view.setListener(_viewListener);
		_model = model;

	}

	protected void updateView() {
		if (_model.isImageAvailable(_view)) {
			_view.setImageTitle(_model.getImageTitle());
			_view.setImage(_model.getImage(_view));
		} else {
			try {
				DownloadItemInfo info = new DownloadItemInfo(_model.getImageSet(), _model.getIndex());
				I.downloadManager.AddToQueue(info);
				_view.setImageTitle(_model.getImageTitle());
				_view.showToast("Downloading " + _model.getIndex() + "."); // EMBEDDED string
			} catch (Exception e) {
				_view.showToast("Download " + _model.getIndex() + " failed."); // EMBEDDED string
			}
		}
	}

	public class ViewListener implements ShowImageView.IListener {

		public void onNewerClicked(ShowImageView view) {
			_model.changeToNewerIndex();
			updateView();
		}

		public void onNewestClicked(ShowImageView view) {
			_model.changeToNewestIndex();
			updateView();
		}

		public void onOlderClicked(ShowImageView view) {
			_model.changeToOlderIndex();
			updateView();
		}

		public void onOldestClicked(ShowImageView view) {
			_model.changeToOldestIndex();
			updateView();
		}
		
	}

}
