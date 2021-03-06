package com.dadino.quickstart.core.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.dadino.quickstart.core.R;
import com.dadino.quickstart.core.adapters.BaseAdapter;


public class RecyclerLayout<T extends BaseAdapter, E extends RecyclerView.LayoutManager> extends
        SwipeRefreshLayoutWithEmpty {

    public RecyclerView mList;
    public TextView mEmptyText;
    protected T mAdapter;
    protected E mLayoutManager;
    private boolean mLoading;


    public RecyclerLayout(Context context) {
        super(context);
        init();
    }

    public RecyclerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        inflate(getContext(), R.layout.view_recycler_layout, this);
        mList = (RecyclerView) findViewById(R.id.list);
        mEmptyText = (TextView) findViewById(R.id.empty_text);
        setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorAccentLight,
                R.color.colorPrimaryLight);
        setClipToPadding(false);
        initialize();
        updateLoadingState();
    }

    protected void initialize() {
    }

    public void setListLoading(boolean loading) {
        this.mLoading = loading;
        post(() -> {
            setRefreshing(mLoading);
            updateEmptyTextVisibility();
        });
    }

    public void updateLoadingState() {
        setListLoading(mLoading);
    }

    private void updateEmptyTextVisibility() {
        final int visibility =
                ((mAdapter != null && mAdapter.getItemCount() != 0) || mLoading) ? INVISIBLE :
                        VISIBLE;
        mEmptyText.setVisibility(visibility);
    }

    public void setEmptyText(String text) {
        mEmptyText.setText(text);
    }

    public void setEmptyText(@StringRes int text) {
        post(() -> mEmptyText.setText(text));
    }


    public T getAdapter() {
        return mAdapter;
    }

    public void setAdapter(T adapter) {
        if (isInEditMode()) return;
        this.mAdapter = adapter;
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                updateLoadingState();
            }
        });
        mList.setAdapter(mAdapter);
        updateLoadingState();
    }


    public void setLayoutManager(E layoutManager) {
        this.mLayoutManager = layoutManager;
        mList.setLayoutManager(layoutManager);
    }

    public void setBottomPadding(int paddingInPixel) {
        mList.setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), paddingInPixel);
    }

    public void setListPadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        mList.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecorator) {
        mList.addItemDecoration(itemDecorator);
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        mList.setHasFixedSize(hasFixedSize);
    }
}
