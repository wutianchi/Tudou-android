package com.bentudou.westwinglife.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.activity.GiveUsMessageActivity;

/**
 * Created by yaoguang on 2016/7/5.
 */
public class CustomDialog extends Dialog {
    public CustomDialog(Context context) {
        super(context);
    }
    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }
    public static class Builder {
        private Context context;
        private String positiveButtonText;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        public Builder(Context context) {
            this.context = context;
        }
        public Builder setPositiveButton(int positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }
        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }
        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context, R.style.MyDialogStyle);
            View layout = inflater.inflate(R.layout.liuyan_menu, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.exitBtn0))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                    GiveUsMessageActivity.instance.finish();
                                }
                            });
                }
            dialog.setContentView(layout);
            return dialog;
        }
    }
}
