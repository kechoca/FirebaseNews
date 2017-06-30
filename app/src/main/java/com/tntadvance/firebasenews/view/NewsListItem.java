package com.tntadvance.firebasenews.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tntadvance.firebasenews.R;

import static com.tntadvance.firebasenews.R.drawable.default1;


/**
 * Created by thana on 3/30/2017 AD.
 */

public class NewsListItem extends BaseCustomViewGroup {

    private TextView tvHeader;
    private TextView tvDate;
    private TextView tvContent;
    private ImageView imgView;
    private TextView tvId;

    public NewsListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public NewsListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public NewsListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public NewsListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.news_list_item, this);
    }

    private void initInstances() {
        tvId = (TextView) findViewById(R.id.tvId);
        tvHeader = (TextView) findViewById(R.id.tvHeader);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvContent = (TextView) findViewById(R.id.tvContent);
        imgView = (ImageView) findViewById(R.id.imgView);

    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

        try {

        } finally {
            a.recycle();
        }
        */
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        BundleSavedState savedState = new BundleSavedState(superState);
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        // Restore State from bundle here
    }

    public void setTvId(String text) {
        tvId.setText(text);
    }

    public void setTvHeader(String text) {
        tvHeader.setText(text);
    }

    public void setTvDate(String text) {
        tvDate.setText(text);
    }

    public void setTvContent(String text) {
        tvContent.setText(text);
    }

    public void setImgView(String path) {
        Glide.with(getContext())
                .load(path)
                .placeholder(default1)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .skipMemoryCache(true)
                .into(imgView);

    }

}
