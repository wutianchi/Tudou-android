package com.gunlei.app.ui.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gunlei.app.R;
import com.gunlei.app.ui.list.ViewHolder;
import com.gunlei.app.ui.view.IOSDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 警告提示工具类。
 * Usage:
 AlertUtil.showAlert(this, "标题", new String[]{"拍照", "相册"}, "退出", new AlertUtil.OnAlertSelectId() {
@Override
public void onClick(int whichButton) {
switch (whichButton) {
case 0:
AlertUtil.dialogMsg(TestEntryActivity.this, "拍照", "调试");
break;
case 1:
AlertUtil.dialogMsg(TestEntryActivity.this, "相册", "调试");
break;
}
}
});

 * Created by fengkang on 14/12/17.
 */
public class AlertUtil
{

    public interface OnAlertSelectId
    {
        void onClick(int whichButton);
    }

    private AlertUtil()
    {
    }

    public static AlertDialog showAlert(final Context context, final int msgId, final int titleId,
                                        final DialogInterface.OnClickListener lOk, final DialogInterface.OnClickListener lCancel)
    {
        if (context instanceof Activity && ((Activity) context).isFinishing())
        {
            return null;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle(titleId);
        builder.setMessage(msgId);
        builder.setPositiveButton("确定", lOk);
        builder.setNegativeButton("取消", lCancel);
        final AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    /**
     * 弹出对话框提示信息
     * @param title 标题
     * @param msg 消息字符串
     */
    public static final void dialogMsg(final Context context, String title, String msg) {
        if (context instanceof Activity && ((Activity) context).isFinishing())
        {
            return;
        }

        IOSDialog.Builder ibuilder = new IOSDialog.Builder(context);
        ibuilder.setTitle(title);
        ibuilder.setMessage(msg);
        ibuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        IOSDialog dialog = ibuilder.create();
        dialog.setCancelable(true);
        dialog.show();
    }


    public static Dialog showAlert(final Context context, final String title, final String[] items,
                                   String exit, final OnAlertSelectId alertDo)
    {
        return showAlert(context, title, items, exit, alertDo, null);
    }

    /**
     * @param context
     *            Context.
     * @param title
     *            The title of this AlertDialog can be null .
     * @param items
     *            button name list.
     * @param alertDo
     *            methods call Id:Button + cancel_Button.
     * @param exit
     *            Name can be null.It will be Red Color
     * @return A AlertDialog
     */
    public static Dialog showAlert(final Context context, final String title, final String[] items, String exit,
                                   final OnAlertSelectId alertDo, DialogInterface.OnCancelListener cancelListener)
    {
        String cancel = "";
        final Dialog dlg = new Dialog(context, R.style.AlertDialogCustom_menu);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.alert_dialog_menu_layout, null);
        final int cFullFillWidth = 10000;
        layout.setMinimumWidth(cFullFillWidth);
        final ListView list = (ListView) layout.findViewById(R.id.content_list);
        AlertAdapter adapter = new AlertAdapter(context, title, items, exit, cancel);
        list.setAdapter(adapter);
        list.setDividerHeight(0);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (!(title == null || title.equals("")) && position - 1 >= 0)
                {
                    alertDo.onClick(position - 1);
                    dlg.dismiss();
                    list.requestFocus();
                }
                else
                {
                    alertDo.onClick(position);
                    dlg.dismiss();
                    list.requestFocus();
                }

            }
        });
        // set a large value put it in bottom
        Window w = dlg.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        final int cMakeBottom = -1000;
        lp.y = cMakeBottom;
        lp.gravity = Gravity.BOTTOM;
        dlg.onWindowAttributesChanged(lp);
        dlg.setCanceledOnTouchOutside(true);
        if (cancelListener != null)
            dlg.setOnCancelListener(cancelListener);

        dlg.setContentView(layout);
        dlg.show();

        return dlg;
    }

}

/** 自定义UI的Alert对话框 */
class AlertAdapter extends BaseAdapter
{
    public static final int TYPE_BUTTON = 0;
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_EXIT = 2;
    public static final int TYPE_CANCEL = 3;
    private List<String> items;
    private boolean isTitle = false;
    private Context context;

    public AlertAdapter(Context context, String title, String[] items, String exit, String cancel)
    {
        if (items == null || items.length == 0) {
            this.items = new ArrayList<String>();
        } else {
            this.items = stringsToList(items);
        }
        this.context = context;
        if (title != null && !title.equals("")) {
            this.isTitle = true;
            this.items.add(0, title);
        }

        if (exit != null && !exit.equals("")) {
            // this.isExit = true;
            this.items.add(exit);
        }

        if (cancel != null && !cancel.equals("")) {
            // this.isSpecial = true;
            this.items.add(cancel);
        }
    }

    private List<String> stringsToList(String[] items) {
        if(null!=items){
            List<String> list  = new ArrayList<>(items.length);
            for(String str:items){
                list.add(str);
            }
            return list;
        }
        return null;
    }

    @Override
    public int getCount()
    {
        return items.size();
    }

    @Override
    public Object getItem(int position)
    {
        return items.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public boolean isEnabled(int position)
    {
        if (position == 0 && isTitle) {
            return false;
        } else {
            return super.isEnabled(position);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String textString = (String) getItem(position);

        if (convertView == null) {
            convertView=View.inflate(context, R.layout.alert_menu_item,null);
        }

        TextView text = ViewHolder.get(convertView, R.id.tv_alertmenu_text);
        text.setText(textString);
        return convertView;
    }

}
