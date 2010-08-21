package org.nateperry.graphicpages;

/**
 * Tracks the current browsing position in the lot.
 * @author nperry
 *
 */
public class ImageLotCursor {
	
	private ImageLot _lot;
	private int _index;
	
	public ImageLotCursor(ImageLot lot) {
		_lot = lot;
	}
	
	public int getIndex() {
		return _index;
	}
	
	public void setIndex(int index) {
		_index = index;
	}
	
	public void moveIndex(int count) {
		/// TODO: check bounds here.
		_index += count;
	}
	
	public ImageLot getLot() {
		return _lot;
	}
	
}
