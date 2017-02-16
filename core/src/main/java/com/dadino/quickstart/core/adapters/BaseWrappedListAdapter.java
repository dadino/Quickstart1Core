package com.dadino.quickstart.core.adapters;

import android.support.annotation.NonNull;

import com.dadino.quickstart.core.adapters.holders.BaseHolder;

import java.util.List;


public abstract class BaseWrappedListAdapter<WRAPPER, ITEM, HOLDER extends BaseHolder<ITEM>> extends
		BaseListAdapter<ITEM, HOLDER> {

	private WRAPPER item;

	public WRAPPER getWrapper() {
		return item;
	}

	public void setItem(WRAPPER item) {
		this.item = item;
		if (item != null) setItems(getWrappedItems(item));
	}

	protected abstract List<ITEM> getWrappedItems(@NonNull WRAPPER wrapper);
}
