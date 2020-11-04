package com.dadino.quickstart.core;

import android.view.View;
import android.widget.TextView;

import com.dadino.quickstart.core.adapters.holders.BaseHolder;


public class SampleHolder extends BaseHolder<Sample> {

    private TextView sampleName;

    public SampleHolder(View itemView) {
        super(itemView, true);
    }

    public static int layout() {
        return R.layout.item_sample;
    }

    @Override
    public void bindView(View view) {
        super.bindView(view);
        sampleName = view.findViewById(R.id.sample_name);
    }

    @Override
    public void bindItem(Sample sample, int position) {
        sampleName.setText(sample.getName());
    }
}
