package com.tntadvance.firebasenews.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tntadvance.firebasenews.dao.NewsDao;
import com.tntadvance.firebasenews.util.DateUtil;
import com.tntadvance.firebasenews.view.NewsListItem;

import java.util.List;

/**
 * Created by thana on 6/28/2017 AD.
 */

public class NewsAdapter extends BaseAdapter {
    List<NewsDao> newsList;

    public NewsAdapter(List<NewsDao> newsList) {
        this.newsList = newsList;
    }

    @Override
    public int getCount() {
        if (newsList == null)
            return 0;
        return newsList.size();
//        return 20;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        NewsListItem item;
        if (view != null)
            item = (NewsListItem) view;
        else
            item = new NewsListItem(viewGroup.getContext());

        NewsDao dao = newsList.get(position);

        item.setTvId(dao.getNewsId());
        item.setTvHeader(dao.getHeader());
        DateUtil dateUtil = new DateUtil();
        item.setTvDate( dateUtil.timeStampToDateString(dao.getDate()));
        item.setTvContent(dao.getContent());
        item.setImgView(dao.getImagePath());

        return item;
    }
}
