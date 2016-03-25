package com.listviewheader.tuan.listviewheader;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int LIST_SIZE = 50;
    private ArrayList<String> mData = new ArrayList<>();
    private MyAdapter mAdapter;
    private TextView mBarTextView;
    private ListView mListView;
    private View fooTextView;
    private View mStaticEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        fooTextView = findViewById(R.id.foo_textview);
        mBarTextView = (TextView) findViewById(R.id.static_view);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listHeader = inflater.inflate(R.layout.listview_header, null);
        mStaticEmptyView = listHeader.findViewById(R.id.static_empty_view);
        mListView.addHeaderView(listHeader);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mListView.getFirstVisiblePosition() == 0) {
                    View topItem = mListView.getChildAt(0);
                    int topY = 0;
                    if(topItem != null) {
                        topY = topItem.getTop();
                    }

                    int staticTopY = mStaticEmptyView.getTop();
                    mBarTextView.setY(Math.max(0, staticTopY + topY));
                    fooTextView.setY(topY);
                }
            }
        });
    }


    class MyAdapter extends BaseAdapter {

        MyAdapter() {
            for(int i = 0; i <= LIST_SIZE; i++){
                mData.add(new Integer(i).toString());
            }
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mData.indexOf(mData.get(position));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder holder = null;
            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView == null) {
                holder = new MyViewHolder();
                convertView = inflater.inflate(R.layout.item_layout, null);
                convertView.setTag(holder);
            }
            holder = (MyViewHolder)convertView.getTag();
            holder.tView = (TextView) convertView.findViewById(R.id.text_view);
            holder.tView.setText(mData.get(position));
            return convertView;
        }
    }

    class MyViewHolder {
        TextView tView;
    }
}
