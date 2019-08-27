package com.dadino.quickstart.core.widgets;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {

	private final int horizontalSpace;

	public HorizontalSpaceItemDecoration(int horizontalSpace) {
		this.horizontalSpace = horizontalSpace;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
	                           RecyclerView.State state) {
		outRect.right = horizontalSpace;
		if (parent.getChildAdapterPosition(view) == 0) {
			outRect.left = horizontalSpace;
		}
	}
}

