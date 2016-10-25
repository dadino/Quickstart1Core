package com.dadino.quickstart.core.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dadino.quickstart.core.adapters.holders.BaseHolder;


public abstract class BaseSingleItemAdapter<ITEM, HOLDER extends BaseHolder<ITEM>> extends
		BaseAdapter<ITEM, HOLDER> {

	protected LayoutInflater layoutInflater;
	private   ITEM           item;


	public BaseSingleItemAdapter() {
		setHasStableIds(useStableId());
	}

	protected boolean useStableId() {
		return false;
	}

	protected LayoutInflater inflater(Context context) {
		if (layoutInflater == null) layoutInflater = LayoutInflater.from(context);
		return layoutInflater;
	}

	@Override
	public HOLDER onCreateViewHolder(ViewGroup parent, int viewType) {
		return getHolder(parent, viewType);
	}

	@Override
	public void onBindViewHolder(HOLDER holder, int position) {
		bindItem(holder, getItem(), position);
	}

	@Override
	public abstract long getItemId(int position);


	public boolean isLastItem(int position) {
		return position == getItemCount() - 1;
	}

	public void bindItem(HOLDER holder, ITEM item, int position) {
		holder.bindItem(item, position);
	}

	protected ITEM getItem() {
		return item;
	}

	public void setItem(ITEM item) {
		this.item = item;
		notifyDataSetChanged();
	}

	protected abstract HOLDER getHolder(ViewGroup parent, int viewType);
}
