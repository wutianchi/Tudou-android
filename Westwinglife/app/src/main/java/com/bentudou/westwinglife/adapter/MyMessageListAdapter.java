package com.bentudou.westwinglife.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.json.MessageInfo;

import java.util.List;


public class MyMessageListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<MessageInfo> messageInfoList;
    private Context context;
    public MyMessageListAdapter(Context context, List<MessageInfo> messageInfoList) {
        mInflater = LayoutInflater.from(context);
        this.messageInfoList=messageInfoList;
        this.context=context;
    }

    @Override
    public int getCount() {
        return messageInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_message_info, null);
            holder.message_name = (TextView) convertView.findViewById(R.id.message_name);
            holder.message_time = (TextView) convertView.findViewById(R.id.message_time);
            holder.message_read = (TextView) convertView.findViewById(R.id.message_read);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final MessageInfo messageInfo = messageInfoList.get(position);
        if (messageInfo.getStatus()==0){
            holder.message_read.setVisibility(View.VISIBLE);
        }else {
            holder.message_read.setVisibility(View.GONE);
        }
        holder.message_name.setText(messageInfo.getTitle());
        holder.message_time.setText(messageInfo.getCreateTime());
        return convertView;
    }

    private static class ViewHolder {
        public TextView message_name;
        public TextView message_time;
        public TextView message_read;
    }
}