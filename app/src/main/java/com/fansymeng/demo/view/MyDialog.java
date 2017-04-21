package com.fansymeng.demo.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fansymeng.demo.R;

/**
 * Created by pig on 2017/4/20.
 */

public class MyDialog extends Dialog {


    public MyDialog(@NonNull Context context) {
        super(context);
    }

    public MyDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected MyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    /**
     * 建造者
     *
     */
    public static class Builder{
        private Context context;
        private int topImageId;
        private String title;
        private String message;
        private Drawable drawable = null;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private OnClickListener ButtonClickListener;
        public EditText editText;
        private Button button;
        private boolean isInput = false;

        public String getText(){
            return editText.getText().toString();
        }

        public Builder(Context context) {
            this.context = context;
        }
        public Builder setTitle(String title){
            this.title = title;
            return this;
        }
        public Builder ButtonClickListener(OnClickListener listioner){
            this.ButtonClickListener =listioner;
            return this;
        }

        public MyDialog create(){
            final MyDialog dialog = new MyDialog(context, R.style.MyDialog);

            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.input_dialog,null);

            TextView titleView = (TextView) view.findViewById(R.id.tv_title);
            if (null == title){
                titleView.setText("请输入：");
            }else {
                titleView.setText(title);
            }
            editText = (EditText) view.findViewById(R.id.edit_input);

             button = (Button) view.findViewById(R.id.bt_sure);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    dialog.dismiss();


                }
            });


            ViewGroup.LayoutParams params = new LinearLayoutCompat
                    .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.addContentView(view,params);

            return dialog;
        }

    }

}
