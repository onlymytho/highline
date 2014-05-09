package com.hlresidential.aceyourexam;

import com.hlresidential.aceyourexam.ToReviewFragment.TouchAnimationEndListener;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

/**
 * This code was extracted from the Transition3D sample activity found in the Android ApiDemos. The
 * animation is made of two smaller animations: the first half rotates the list by 90 degrees on the
 * Y axis and the second half rotates the picture by 90 degrees on the Y axis. When the first half
 * finishes, the list is made invisible and the picture is set visible.
 */
public class ViewSwitcher {
	
	private static final String	TAG					= "ViewSwitcher";

	private ViewGroup			mContainer;
	private View				mFrontside;
	private View				mBackside;
	private Activity            mActivity;

	private long				mDuration			= 300;
	private float				mDepthOfRotation	= 300f;
//	TextView tv_screen_status;

	public ViewSwitcher(ViewGroup container) {
//		Log.e("VS", "ViewSwitcher");

		this.mActivity = mActivity;
//		mContainer = (ViewGroup) container
//				.findViewById(R.id.container);
		mContainer = container;
//		mFrontside = container.getChildAt(0);
//		mBackside = container.getChildAt(1);
		mFrontside = mContainer.findViewById(R.id.linear_layout_even);
		mBackside = mContainer.findViewById(R.id.linear_layout_odd);
//		tv_screen_status = (TextView) mContainer.findViewById(
//				R.id.tv_screen_status);

		// Since we are caching large views, we want to keep their cache
		// between each animation
		mContainer.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);

	}

	public void setDuration(long duration) {
//		Log.e("VS", "setDuration");
		mDuration = duration;
	}

	public void swap() {
		float start, end;
//		tv_screen_status = (TextView) mContainer.findViewById(
//				R.id.tv_screen_status);
//		String current_swap_screen = tv_screen_status.getText().toString();
//		Log.e("VS", "current_swap_screen TAG: " + current_swap_screen);
		if (isFrontsideVisible()) {
//			Log.v(TAG, "SWAP: turning to the backside!");
			start = 0;
			end = 90;
		} else {
//			Log.v(TAG, "SWAP: turning to the frontside!");
			start = 180;
			end = 90;
		}

		Rotate3dAnimation rotation = new Rotate3dAnimation(start, end,
				mContainer.getWidth() / 2.0f, mContainer.getHeight() / 2.0f, mDepthOfRotation, true);
		rotation.setDuration(mDuration / 2);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(new TurnAroundListener());

		mContainer.startAnimation(rotation);
	}

	public boolean isFrontsideVisible() {
		return mFrontside.getVisibility() == View.VISIBLE;
	}

	public boolean isBacksideVisible() {
		return mBackside.getVisibility() == View.VISIBLE;
	}

	/**
	 * Listen for the end of the first half of the animation. Then post a new action that
	 * effectively swaps the views when the container is rotated 90 degrees and thus invisible.
	 */
	private final class TurnAroundListener implements Animation.AnimationListener {

		public void onAnimationStart(Animation animation) {
//			((TouchAnimationEndListener) mActivity).onAnimationTouchToggler(false); // Turn off touch
			mContainer.requestDisallowInterceptTouchEvent(true);
		}

		public void onAnimationEnd(Animation animation) {
			mContainer.post(new SwapViews());
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}
	/**
	 * Listen for the end of the  animation and re-enable touch for the activity.
	 */
	private final class AnimDoneListener implements Animation.AnimationListener {

		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
//			((TouchAnimationEndListener) mActivity).onAnimationTouchToggler(true); // Turn on touch
			mContainer.requestDisallowInterceptTouchEvent(false);
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}
	/**
	 * Swapping the views and start the second half of the animation.
	 */
	private final class SwapViews implements Runnable {

		public void run() {
			
			final float centerX = mContainer.getWidth() / 2.0f;
			final float centerY = mContainer.getHeight() / 2.0f;
			Rotate3dAnimation rotation;
			Log.e("VS", " in SwapViews");
			if (isFrontsideVisible()) {
//				Log.e("VS", " make backside visible");
				mFrontside.setVisibility(View.GONE);
				mBackside.setVisibility(View.VISIBLE);
				unmirrorTheBackside();
				mBackside.requestFocus();

				rotation = new Rotate3dAnimation(90, 180, centerX, centerY, mDepthOfRotation, false);
			} else {
//				Log.e("VS", " make frontside visible");
				mBackside.setVisibility(View.GONE);
				mBackside.clearAnimation(); // remove the mirroring
				mFrontside.setVisibility(View.VISIBLE);
				mFrontside.requestFocus();

				rotation = new Rotate3dAnimation(90, 0, centerX, centerY, mDepthOfRotation, false);
//				rotation = new Rotate3dAnimation(90, 180, centerX, centerY, mDepthOfRotation, false);
			}

			rotation.setDuration(mDuration / 2);
			rotation.setFillAfter(true);
			rotation.setInterpolator(new DecelerateInterpolator());

			rotation.setAnimationListener(new AnimDoneListener());
			mContainer.startAnimation(rotation);
		}
		
	}

	private void unmirrorTheBackside() {
		Rotate3dAnimation rotation = new Rotate3dAnimation(0, 180, mContainer.getWidth() / 2.0f,
				mContainer.getHeight() / 2.0f, mDepthOfRotation, false);
		rotation.setDuration(0);
		rotation.setFillAfter(true);
		
		rotation.setAnimationListener(new AnimDoneListener());
		mBackside.startAnimation(rotation);
	}




}
