package com.dadino.quickstart.core.widgets;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

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

