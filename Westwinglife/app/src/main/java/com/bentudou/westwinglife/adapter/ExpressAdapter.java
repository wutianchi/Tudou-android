package com.bentudou.westwinglife.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.json.ExpressMessage;
import com.bentudou.westwinglife.json.GrowInfo;
import com.bentudou.westwinglife.utils.VerifitionUtil;

import java.util.List;

/**
 *Created by lzz on 2016/3/2.
 * 成长记录适配器
 */
public class ExpressAdapter extends BaseAdapter {
    List<ExpressMessage> list;
    Context context;
    private StringBuffer stringBuffer;
    public ExpressAdapter(List<ExpressMessage> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (list==null) {
            return 0;
        } else {
            return list.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (list==null) {
            return null;
        } else {
            return list.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ClassHolder classHolder;
        if(convertView==null){
            classHolder = new ClassHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_express_info,null);
            classHolder.tv_up_line = (TextView)convertView.findViewById(R.id.tv_up_line);
            classHolder.tv_zhengc = (TextView)convertView.findViewById(R.id.tv_zhengc);
            classHolder.tv_now_states = (TextView)convertView.findViewById(R.id.tv_now_states);
            classHolder.tv_down_line = (TextView)convertView.findViewById(R.id.tv_down_line);
            classHolder.tv_wuliu_content = (TextView)convertView.findViewById(R.id.tv_wuliu_content);
            classHolder.tv_wuliu_time = (TextView)convertView.findViewById(R.id.tv_wuliu_time);
            classHolder.tv_bottom_line = (TextView)convertView.findViewById(R.id.tv_bottom_line);
            convertView.setTag(classHolder);
        }else {
            classHolder = (ClassHolder) convertView.getTag();
        }
        stringBuffer= new StringBuffer();
        if (position==0){
            classHolder.tv_up_line.setVisibility(View.INVISIBLE);
            classHolder.tv_zhengc.setVisibility(View.GONE);
            classHolder.tv_now_states.setVisibility(View.VISIBLE);
            classHolder.tv_wuliu_content.setTextColor(context.getResources().getColor(R.color.black_base));
            classHolder.tv_wuliu_content.setText(list.get(position).getExpressContent());
            final String num = VerifitionUtil.indexExpress(list.get(position).getExpressContent());
            Log.d("num", "-----getView: "+num);
            if (num.equals("")){}else {
                classHolder.tv_wuliu_content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        // 将文本内容放到系统剪贴板里。
                        cm.setText(num);
                        Toast.makeText(context, "国内复制成功", Toast.LENGTH_LONG).show();
                        return ;
                    }
                });
            }
            classHolder.tv_wuliu_time.setText(list.get(position).getCalloutDate());
            if (list.size()==1){
                classHolder.tv_down_line.setVisibility(View.INVISIBLE);
                classHolder.tv_bottom_line.setVisibility(View.INVISIBLE);
            }else {
                classHolder.tv_down_line.setVisibility(View.VISIBLE);
                classHolder.tv_bottom_line.setVisibility(View.VISIBLE);
            }
        }else {
            classHolder.tv_up_line.setVisibility(View.VISIBLE);
            classHolder.tv_zhengc.setVisibility(View.VISIBLE);
            classHolder.tv_now_states.setVisibility(View.GONE);
            classHolder.tv_wuliu_content.setText(list.get(position).getExpressContent());
            classHolder.tv_wuliu_time.setText(list.get(position).getCalloutDate());
            if (position==list.size()-1){
                classHolder.tv_down_line.setVisibility(View.INVISIBLE);
                classHolder.tv_bottom_line.setVisibility(View.INVISIBLE);
            }else {
                classHolder.tv_down_line.setVisibility(View.VISIBLE);
                classHolder.tv_bottom_line.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }

    static class ClassHolder{
        TextView tv_up_line,tv_zhengc,tv_now_states,tv_down_line,tv_wuliu_content,tv_wuliu_time,tv_bottom_line;
    }

}
