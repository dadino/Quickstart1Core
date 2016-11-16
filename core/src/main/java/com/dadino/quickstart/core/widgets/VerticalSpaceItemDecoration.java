package com.dadino.quickstart.core.widgets;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

	private final int verticalSpace;

	public VerticalSpaceItemDecoration(int verticalSpace) {
		this.verticalSpace = verticalSpace;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
	                           RecyclerView.State state) {
		outRect.bottom = verticalSpace;
		if (parent.getChildAdapterPosition(view) == 0) {
			outRect.top = verticalSpace;
		}
	}
}

