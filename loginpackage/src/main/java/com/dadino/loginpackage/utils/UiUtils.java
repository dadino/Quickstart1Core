package com.dadino.loginpackage.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;

public class UiUtils {

	public static int resolveAdjustedSize(int desiredSize, int measureSpec) {
		int result = desiredSize;
		int specMode = View.MeasureSpec.getMode(measureSpec);
		int specSize = View.MeasureSpec.getSize(measureSpec);
		switch (specMode) {
			case View.MeasureSpec.UNSPECIFIED:
				// Parent says we can be as big as we want. Just don't be larger
				// than max size imposed on ourselves.
				result = desiredSize;
				break;
			case View.MeasureSpec.AT_MOST:
				// Parent says we can be as big as we want, up to specSize.
				// Don't be larger than specSize, and don't be larger than
				// the max size imposed on ourselves.
				result = Math.min(desiredSize, specSize);
				break;
			case View.MeasureSpec.EXACTLY:
				// No choice. Do what we are told.
				result = specSize;
				break;
		}
		return result;
	}

	public static void animateReveal(View revealed, View source, boolean reveal) {
		final AnimatorListenerAdapter listener = new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				if (!reveal) revealed.setVisibility(View.INVISIBLE);
			}

			@Override
			public void onAnimationStart(Animator animation) {
				super.onAnimationStart(animation);
				if (reveal) revealed.setVisibility(View.VISIBLE);
			}
		};

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
			final int x = getRelativeLeft(source, revealed) + source.getWidth() / 2;
			final int y = getRelativeTop(source, revealed) + source.getHeight() / 2;

			final int a = Math.max(x, revealed.getWidth());
			final int b = Math.max(y, revealed.getHeight());

			final int radius = (int) Math.sqrt(a * a + b * b);

			// create the animator for this view (the start radius is zero)
			Animator anim = ViewAnimationUtils.createCircularReveal(revealed, x, y,
					reveal ? 0 : radius, reveal ? radius : 0);

			anim.addListener(listener);
			anim.setInterpolator(new DecelerateInterpolator());
			anim.start();
		} else {
			revealed.animate()
			        .alpha(reveal ? 1f : 0f)
			        .setListener(listener)
			        .setInterpolator(new DecelerateInterpolator())
			        .start();
		}
	}

	private static int getRelativeLeft(View view, View targetChildren) {
		if (view.getParent() == targetChildren.getParent()) return view.getLeft();
		else return view.getLeft() + getRelativeLeft((View) view.getParent(), targetChildren);
	}

	private static int getRelativeTop(View view, View targetChildren) {
		if (view.getParent() == targetChildren.getParent()) return view.getTop();
		else return view.getTop() + getRelativeTop((View) view.getParent(), targetChildren);
	}
}
