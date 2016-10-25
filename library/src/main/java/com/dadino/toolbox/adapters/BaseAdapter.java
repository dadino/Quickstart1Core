package com.dadino.toolbox.adapters;

import android.support.v7.widget.RecyclerView;

import com.dadino.toolbox.adapters.holders.BaseHolder;

public abstract class BaseAdapter<ITEM, HOLDER extends BaseHolder<ITEM>> extends RecyclerView
		.Adapter<HOLDER> {

}
