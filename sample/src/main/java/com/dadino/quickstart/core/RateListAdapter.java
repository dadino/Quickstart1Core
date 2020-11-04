package com.dadino.quickstart.core;

import android.view.ViewGroup;

import com.dadino.quickstart.core.adapters.BaseListAdapter;


public class RateListAdapter extends BaseListAdapter<Sample, SampleHolder> {


    @Override
    protected long getItemIdSafe(int position) {
        return getItem(position).getId();
    }

    @Override
    protected SampleHolder getHolder(ViewGroup parent, int viewType) {
        return new SampleHolder(inflate(parent, SampleHolder.layout()));
    }
}
