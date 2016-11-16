package com.dadino.quickstart.core.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dadino.quickstart.core.adapters.holders.BaseHolder;
import com.dadino.quickstart.core.utils.Logs;

import java.util.Comparator;
import java.util.List;


public abstract class BaseListAdapter<ITEM, HOLDER extends BaseHolder<ITEM>> extends
		BaseAdapter<ITEM, HOLDER> {

	private static final int NOT_COUNTED = -1;
	protected LayoutInflater          layoutInflater;
	protected ClickListener<ITEM>     clickListener;
	protected LongClickListener<ITEM> longClickListener;
	private   List<ITEM>              items;
	private int count = NOT_COUNTED;


	public BaseListAdapter() {
		setHasStableIds(useStableId());
	}

	protected boolean useStableId() {
		return true;
	}

	public void setClickListener(ClickListener<ITEM> clickListener) {
		this.clickListener = clickListener;
	}

	public void setLongClickListener(LongClickListener<ITEM> longClickListener) {
		this.longClickListener = longClickListener;
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
		bindItem(holder, getItem(position), position);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position) != null ? getItemIdSafe(position) : -1;
	}

	@Override
	public int getItemCount() {
		if (count >= 0) return count;
		if (items != null) {
			count = items.size() + getHeadersCount() + getFootersCount();
			return count;
		} else {
			count = getHeadersCount() + getFootersCount();
			return count;
		}
	}

	protected abstract long getItemIdSafe(int position);

	public int getFootersCount() {
		return 0;
	}

	public int getHeadersCount() {
		return 0;
	}

	public boolean isLastItem(int position) {
		return position == getItemCount() - 1;
	}

	public int getPosition(long id) {
		if (getItemCount() > 0) {
			for (int i = 0; i < getItemCount(); i++) {
				if (getItemId(i) == id) return i;
			}
		}
		return -1;
	}

	public int getPosition(@Nullable ITEM item, Comparator<ITEM> comparator) {
		if (item == null) return -1;
		if (getItemCount() > 0) {
			for (int i = 0; i < getItemCount(); i++) {
				if (comparator.compare(getItem(i), item) == 0) return i;
			}
		}
		return -1;
	}

	public void bindItem(HOLDER holder, ITEM item, int position) {
		holder.bindItem(item, position);
		holder.setClickListener((v, pos, isLongClick) -> {
			if (!isLongClick) {
				// View v at position pos is clicked.
				Logs.ui("Item at pos" + pos + " clicked!");
				if (clickListener != null) clickListener.onClicked(v, getItem(pos));
			} else {
				// View v at position pos is long-clicked.
				Logs.ui("Item at pos" + pos + " long clicked!");
				if (clickListener != null) longClickListener.onLongClicked(v, getItem(pos));
			}
		});
	}

	@Nullable
	public ITEM getItem(int position) {
		if (position < getHeadersCount()) return null;
		final int adjustedPosition = position - getHeadersCount();
		if (adjustedPosition < (getItemCount() - getHeadersCount() - getFootersCount()))
			return items.get(adjustedPosition);
		else return null;
	}

	protected abstract HOLDER getHolder(ViewGroup parent, int viewType);

	public List<ITEM> getItems() {
		return items;
	}

	public void setItems(List<ITEM> items) {
		this.items = items;
		count = NOT_COUNTED;
		notifyDataSetChanged();
	}

	public interface ClickListener<ITEM> {

		void onClicked(View v, ITEM item);
	}

	public interface LongClickListener<ITEM> {

		void onLongClicked(View v, ITEM item);
	}
}
