package com.dadino.quickstart.core.adapters;

import androidx.recyclerview.widget.RecyclerView;

import com.dadino.quickstart.core.adapters.holders.BaseHolder;

public abstract class BaseAdapter<ITEM, HOLDER extends BaseHolder<ITEM>> extends RecyclerView
		.Adapter<HOLDER> {

}
