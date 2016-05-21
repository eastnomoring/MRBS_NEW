package com.yzy.mrbs.list;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzy.mrbs.R;
import com.yzy.mrbs.base.BaseList;
import com.yzy.mrbs.base.BaseUi;
import com.yzy.mrbs.model.Room;
import com.yzy.mrbs.util.AppCache;
import com.yzy.mrbs.util.AppFilter;

import java.util.ArrayList;

/**
 * Created by ZhiYuan on 2016/5/19.
 */
public class BlogList extends BaseList {

    private BaseUi ui;
    private LayoutInflater inflater;
    private ArrayList<Room> roomList;

    public final class BlogListItem {
        public ImageView face;
        public TextView content;
        public TextView uptime;
        public TextView comment;
    }

    public BlogList (BaseUi ui, ArrayList<Room> blogList) {
        this.ui = ui;
        this.inflater = LayoutInflater.from(this.ui);
        this.roomList = blogList;
    }

    @Override
    public int getCount() {
        return roomList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int p, View v, ViewGroup parent) {
        // init tpl
        BlogListItem  blogItem = null;
        // if cached expired
        if (v == null) {
            v = inflater.inflate(R.layout.tpl_list_blog, null);
            blogItem = new BlogListItem();
            blogItem.face = (ImageView) v.findViewById(R.id.tpl_list_blog_image_face);
            blogItem.content = (TextView) v.findViewById(R.id.tpl_list_blog_text_content);
            blogItem.uptime = (TextView) v.findViewById(R.id.tpl_list_blog_text_uptime);
            blogItem.comment = (TextView) v.findViewById(R.id.tpl_list_blog_text_comment);
            v.setTag(blogItem);
        } else {
            blogItem = (BlogListItem) v.getTag();
        }
        // fill data
        blogItem.uptime.setText(roomList.get(p).getUptime());
        // fill html data
        blogItem.content.setText(AppFilter.getHtml(roomList.get(p).getContent()));
        blogItem.comment.setText(AppFilter.getHtml(roomList.get(p).getComment()));
        // load face image
        String faceUrl = roomList.get(p).getFace();
        Bitmap faceImage = AppCache.getImage(faceUrl);
        if (faceImage != null) {
            blogItem.face.setImageBitmap(faceImage);
        }
        return v;
    }
}
