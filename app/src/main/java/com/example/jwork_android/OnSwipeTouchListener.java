package com.example.jwork_android;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import org.json.JSONException;

/**
 * Class for On Swipe Touch Listener
 *
 * @author Ivan Widjanarko
 * @version 25-06-2021
 */
public class OnSwipeTouchListener implements OnTouchListener {

    private final GestureDetector gestureDetector;

    /**
     * Constructor for OnSwipeTouchListener
     * @param ctx Context
     */
    public OnSwipeTouchListener (Context ctx){
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }

    /**
     * Method when the page is touch
     * @param v View
     * @param event Event
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    /**
     * Method for Gesture Liner
     */
    private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        /**
         * Method when down
         * @param e Motion's Event
         */
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        /**
         * Method when fling
         * @param e1 Motion's Event 1
         * @param e2 Motion's Event 2
         * @param velocityX Horizontal Velocity
         * @param velocityY Vertical Velocity
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeLeft();
                        } else {
                            onSwipeRight();
                        }
                        result = true;
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeTop();
                    } else {
                        onSwipeBottom();
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    /**
     * Method when swiping the slide from right to the left
     * @throws JSONException JSON Exception
     */
    public void onSwipeRight() throws JSONException {
    }

    /**
     * Method when swiping the slide from left to the right
     * @throws JSONException JSON Exception
     */
    public void onSwipeLeft() throws JSONException {
    }

    /**
     * Method when swiping the slide from top to the bottom
     * @throws JSONException JSON Exception
     */
    public void onSwipeTop() {
    }

    /**
     * Method when swiping the slide from bottom to the top
     * Method when swiping the slide from bottom to the top
     * @throws JSONException JSON Exception
     */
    public void onSwipeBottom() {
    }
}
