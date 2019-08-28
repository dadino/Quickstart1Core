package com.dadino.quickstart.core.adapters.holders;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener,
        View.OnLongClickListener {

    protected ClickListener clickListener;

    public BaseHolder(View itemView, boolean withClickListener) {
        super(itemView);
        if (withClickListener) {
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        bindView(itemView);
    }

    public BaseHolder(View itemView) {
        this(itemView, true);
    }

    public void bindView(View view) {

    }

    public abstract void bindItem(T item, int position);

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) clickListener.onClick(
                getClickedView() != null ? getClickedView() : v, getLayoutPosition(), false);
    }

    @Override
    public boolean onLongClick(View v) {
        if (clickListener != null) clickListener.onClick(
                getLongClickedView() != null ? getLongClickedView() : v, getLayoutPosition(),
                true);
        return true;
    }

    @Nullable
    protected View getClickedView() {
        return null;
    }

    @Nullable
    protected View getLongClickedView() {
        return null;
    }

    public interface ClickListener {

        void onClick(View v, int position, boolean isLongClick);
    }
}
