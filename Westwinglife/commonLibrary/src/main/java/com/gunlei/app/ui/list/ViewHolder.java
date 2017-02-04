package com.gunlei.app.ui.list;

import android.util.SparseArray;
import android.view.View;

/**
 * 全局可用的ViewHolder类。
 * 疑问：对动态变动的布局是否有效？？
 * 用法：
 * public View getView(int position, View convertView, ViewGroup parent) {
 if(convertView == null){
 convertView = mInflater.inflate(R.layout.item_lsv_main, null);
 //convertView.setTag(holder);//这一句将不再需要
 }
 TextView nameTxtv = ViewHolder.get(convertView, R.id.item_name_txtv);
 nameTxtv.setText(mTestList.get(position));
 return convertView;
 }

 */
public class ViewHolder {

    /**
     * 从View中获取子组件
     * @param view parent
     * @param id R.id.___
     * @param <T> result 类名
     * @return 结果组件
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id){
        //从convertView中获取tag，SparseArray里面存放View和View对应的id，通过View的id来查找View
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        //如果获取不到，则创建SparseArray对象，并将SparseArray对象放入convertView的tag中
        if(viewHolder == null){
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        //通过View的id来获取View，在SparseArray的内部使用的折半查找
        View childView = viewHolder.get(id);
        //如果在SparseArray中没有对应的id，则通过findViewById来获取View
        if(childView == null){
            childView = view.findViewById(id);
            //将获取到的View和与View对应的id存入SparseArray中
            viewHolder.put(id, childView);
        }
        //返回值是方法的第二个参数id对应的View
        return (T) childView;
    }
}