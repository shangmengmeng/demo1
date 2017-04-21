# demo1
请求
![这里写图片描述](http://img.blog.csdn.net/20170421112026933?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMjIyMzA5MzU=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)



自定义dialog
==
## 首先它继承自dialog ##
## 然后创建buider建造者 ##
## 在建造者中创建Buider 的方法用于回调 ##

```


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
        private OnClickListener ButtonClickListener;
        public EditText editText;
        private Button button;
		
		//用于取到editText中的数据
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
	       //创建视图，加载自己定义的xml（view）
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
	                //这里很重要
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

```


----------


----------

## 调用的方式 ##

```
     final MyDialog.Builder builder = new MyDialog.Builder(this);
        builder.ButtonClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String a =builder.getText();
                edit_name.setText(a);
                Log.e("1111111",a);
                Toast.makeText(MainActivity.this,a,Toast.LENGTH_SHORT).show();

            }
        });
        builder.create().show();
```
