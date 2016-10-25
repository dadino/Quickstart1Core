package com.dadino.quickstart.core.adapters;

import android.support.v7.widget.RecyclerView;

import com.dadino.quickstart.core.adapters.holders.BaseHolder;

public abstract class BaseAdapter<ITEM, HOLDER extends BaseHolder<ITEM>> extends RecyclerView
		.Adapter<HOLDER> {

}
