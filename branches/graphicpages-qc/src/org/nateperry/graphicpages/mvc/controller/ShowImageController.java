package org.nateperry.graphicpages.mvc.controller;

import org.nateperry.graphicpages.mvc.model.ShowImageModel;
import org.nateperry.graphicpages.mvc.view.ShowImageView;

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
		if (_model.isImageAvailable()) {
			_view.setImageTitle(_model.getImageTitle());
			_view.setImage(_model.getImage(_view));
		} else {
			// EMBEDDED string
			_view.showToast("Downloading image");
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
