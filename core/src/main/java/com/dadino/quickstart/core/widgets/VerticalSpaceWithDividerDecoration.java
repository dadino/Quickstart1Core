package com.dadino.quickstart.core.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;


public class VerticalSpaceWithDividerDecoration extends RecyclerView.ItemDecoration {


	private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
	private final int verticalSpace;
	private final Rect mBounds = new Rect();
	private Drawable mDivider;

	public VerticalSpaceWithDividerDecoration(Context context, int verticalSpace) {
		this.verticalSpace = verticalSpace;
		final TypedArray a = context.obtainStyledAttributes(ATTRS);
		mDivider = a.getDrawable(0);
		a.recycle();
	}

	/**
	 * Sets the {@link Drawable} for this divider.
	 *
	 * @param drawable Drawable that should be used as a divider.
	 */
	public void setDrawable(@NonNull Drawable drawable) {
		if (drawable == null) {
			throw new IllegalArgumentException("Drawable cannot be null.");
		}
		mDivider = drawable;
	}

	@Override
	public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
		if (parent.getLayoutManager() == null) {
			return;
		}
		drawVertical(c, parent);
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
	                           RecyclerView.State state) {
		//outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());

		outRect.bottom = verticalSpace;
		if (parent.getChildAdapterPosition(view) == 0) {
			outRect.top = verticalSpace;
		}
	}

	@SuppressLint("NewApi")
	private void drawVertical(Canvas canvas, RecyclerView parent) {
		canvas.save();
		final int left;
		final int right;
		if (parent.getClipToPadding()) {
			left = parent.getPaddingLeft();
			right = parent.getWidth() - parent.getPaddingRight();
			canvas.clipRect(left, parent.getPaddingTop(), right,
					parent.getHeight() - parent.getPaddingBottom());
		} else {
			left = 0;
			right = parent.getWidth();
		}

		final int childCount = parent.getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View child = parent.getChildAt(i);
			parent.getDecoratedBoundsWithMargins(child, mBounds);
			final int bottom = mBounds.bottom + Math.round(ViewCompat.getTranslationY(child)) -
			                   verticalSpace / 2;
			final int top = bottom - mDivider.getIntrinsicHeight();
			mDivider.setBounds(left, top, right, bottom);
			mDivider.draw(canvas);
		}
		canvas.restore();
	}
}