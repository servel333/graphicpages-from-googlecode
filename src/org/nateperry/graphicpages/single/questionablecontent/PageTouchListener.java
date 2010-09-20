package org.nateperry.graphicpages.single.questionablecontent;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

// http://www.zdnet.com/blog/burnette/how-to-use-multi-touch-in-android-2-part-6-implementing-the-pinch-zoom-gesture/1847
public class PageTouchListener implements OnTouchListener {

    private static final String TAG = "Touch";

    // These matrices will be used to move and zoom image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // We can be in one of these 3 states
    static final int TOUCH_STATE_NONE = 0;
    static final int TOUCH_STATE_DRAG = 1;
    static final int TOUCH_STATE_ZOOM = 2;
    int touch_state = TOUCH_STATE_NONE;

    // Remember some things for zooming
    PointF touch_start = new PointF();
	PointF touch_mid = new PointF();
    float touch_oldDist = 1f;
    ImageView view = null;

    public void ResetTouch() {
        matrix = new Matrix();
        savedMatrix = new Matrix();
    	touch_start = new PointF();
    	touch_mid = new PointF();
    	touch_oldDist = 1f;
    	
    	if (null != view) {
    		view.setImageMatrix(matrix);
    	}
    }
    
    public boolean onTouch(View v, MotionEvent event) {

		view = (ImageView) v;
		
		// Dump touch event to log
		dumpEvent(event);

		// Handle touch events here...
		switch (event.getAction() & MotionEvent.ACTION_MASK) {	

			case MotionEvent.ACTION_DOWN:
				savedMatrix.set(matrix);
				touch_start.set(event.getX(), event.getY());
				Log.d(TAG, "touch_state=TOUCH_STATE_DRAG");
				touch_state = TOUCH_STATE_DRAG;
				break;

			case MotionEvent.ACTION_POINTER_DOWN:
				touch_oldDist = spacing(event);
				Log.d(TAG, "touch_oldDist=" + touch_oldDist);
				if (touch_oldDist > 10f) {
					savedMatrix.set(matrix);
					midPoint(touch_mid, event);
					touch_state = TOUCH_STATE_ZOOM;
					Log.d(TAG, "touch_state=TOUCH_STATE_ZOOM");
				}
				break;

			case MotionEvent.ACTION_POINTER_UP:
			case MotionEvent.ACTION_UP:
				touch_state = TOUCH_STATE_NONE;
				Log.d(TAG, "touch_state=TOUCH_STATE_NONE");
				break;

			case MotionEvent.ACTION_MOVE:
				if (touch_state == TOUCH_STATE_DRAG) {
					matrix.set(savedMatrix);
					matrix.postTranslate(event.getX() - touch_start.x, event.getY() - touch_start.y);
				} else if (touch_state == TOUCH_STATE_ZOOM) {
					float newDist = spacing(event);
					Log.d(TAG, "newDist=" + newDist);
					if (newDist > 10f) {
						matrix.set(savedMatrix);
						float scale = newDist / touch_oldDist;
						matrix.postScale(scale, scale, touch_mid.x, touch_mid.y);
					}
				}
				break; 

		}

		view.setImageMatrix(matrix);
		return true; // indicate event was handled
	}

    /** Show an event in the LogCat view, for debugging */
    private void dumpEvent(MotionEvent event) {
    	
       String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
             "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
       StringBuilder sb = new StringBuilder();
       int action = event.getAction();
       int actionCode = action & MotionEvent.ACTION_MASK;
       sb.append("event ACTION_").append(names[actionCode]);
       if (actionCode == MotionEvent.ACTION_POINTER_DOWN
             || actionCode == MotionEvent.ACTION_POINTER_UP) {
          sb.append("(pid ").append(
                action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
          sb.append(")");
       }
       sb.append("[");
       for (int i = 0; i < event.getPointerCount(); i++) {
          sb.append("#").append(i);
          sb.append("(pid ").append(event.getPointerId(i));
          sb.append(")=").append((int) event.getX(i));
          sb.append(",").append((int) event.getY(i));
          if (i + 1 < event.getPointerCount())
             sb.append(";");
       }
       sb.append("]");
       Log.d(TAG, sb.toString());
    }

    /** Determine the space between the first two fingers */
    private float spacing(MotionEvent event) {
    	
       float x = event.getX(0) - event.getX(1);
       float y = event.getY(0) - event.getY(1);
       return FloatMath.sqrt(x * x + y * y);
    }

    /** Calculate the mid point of the first two fingers */
    private void midPoint(PointF point, MotionEvent event) {
    	
       float x = event.getX(0) + event.getX(1);
       float y = event.getY(0) + event.getY(1);
       point.set(x / 2, y / 2);
    }

}
