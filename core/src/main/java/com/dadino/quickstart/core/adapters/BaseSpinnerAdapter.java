package com.dadino.quickstart.core.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.dadino.quickstart.core.adapters.holders.BaseHolder;

import java.util.List;


public abstract class BaseSpinnerAdapter<ITEM, HOLDER extends BaseHolder<ITEM>> extends
		BaseAdapter implements
		SpinnerAdapter {

	public static final int ID_NOT_FOUND = -1;
	private List<ITEM>     items;
	private int            count;
	private LayoutInflater inflater;

	public BaseSpinnerAdapter() {
		super();
	}


	protected LayoutInflater inflater(Context context) {
		if (inflater == null) inflater = LayoutInflater.from(context);
		return inflater;
	}

	public void setItems(List<ITEM> items) {
		this.items = items;
		count = -1;
		notifyDataSetChanged();
	}

	public int getAdditionalItemCount() {
		return 0;
	}

	public int getCount() {
		if (count >= 0) return count;
		if (items != null) {
			count = items.size() + getAdditionalItemCount();
			return count;
		} else {
			count = getAdditionalItemCount();
			return count;
		}
	}

	public ITEM getItem(int position) {
		return items.get(position);
	}

	public abstract long getItemId(int position);

	public View getView(int position, View convertview, ViewGroup parent) {
		HOLDER viewHolder;
		if (convertview == null) {
			convertview = inflateView(inflater(parent.getContext()), parent);
			viewHolder = createHolder(convertview);
			convertview.setTag(viewHolder);
		} else {
			viewHolder = (HOLDER) convertview.getTag();
		}
		viewHolder.bindItem(getItem(position), position);
		return convertview;
	}

	public int getPosition(long id) {
		if (getCount() == 0) return ID_NOT_FOUND;
		for (int i = 0; i < getCount(); i++) {
			if (id == getItemId(i)) return i;
		}
		return ID_NOT_FOUND;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return modifyDropDownView(getView(position, convertView, parent));
	}

	protected abstract View modifyDropDownView(View view);

	protected abstract HOLDER createHolder(View convertView);
	protected abstract View inflateView(LayoutInflater context, ViewGroup parent);


	protected View inflate(ViewGroup parent, @LayoutRes int layoutId) {
		return inflater(parent.getContext()).inflate(layoutId, parent, false);
	}

	public class ViewHolder {

		Spinner spinner;
	}
}
