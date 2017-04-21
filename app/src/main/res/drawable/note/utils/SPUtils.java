package com.anew.note.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class SPUtils {
    public static final String FILE_NAME = "CarLoan_SP";
    private static SPUtils instance;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public static SPUtils getInstance(Context context) {
        if (instance == null && context != null) {
            instance = new SPUtils(context.getApplicationContext());
        }
        return instance;
    }

    @SuppressLint("CommitPrefEdits")
    private SPUtils(Context context) {
        sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    /**
     * 获取token
     *
     * @return
     */
    public String getTokn() {
        String key = "token";
        if (key != null && !key.equals("")) {
            return sp.getString(key, null);
        }
        return null;
    }

    public long getLongValue(String key) {
        if (key != null && !key.equals("")) {
            return sp.getLong(key, 0);
        }
        return 0;
    }

    public String getStringValue(String key) {
        if (key != null && !key.equals("")) {
            return sp.getString(key, null);
        }
        return null;
    }

    public int getIntValue(String key) {
        if (key != null && !key.equals("")) {
            return sp.getInt(key, 0);
        }
        return 0;
    }

    public boolean getBooleanValue(String key) {
        return !(key != null && !key.equals("")) || sp.getBoolean(key, true);
    }

    public float getFloatValue(String key) {
        if (key != null && !key.equals("")) {
            return sp.getFloat(key, 0);
        }
        return 0;
    }

    public void putStringValue(String key, String value) {
        if (key != null && !key.equals("")) {
            editor = sp.edit();
            editor.putString(key, value);
            SharedPreferencesCompat.apply(editor);
        }
    }

    public void putIntValue(String key, int value) {
        if (key != null && !key.equals("")) {
            editor = sp.edit();
            editor.putInt(key, value);
            SharedPreferencesCompat.apply(editor);
        }
    }

    public void putObject(String key, Object object) {
        try {
            if (key != null && object != null) {
                editor = sp.edit();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(outputStream);
                out.writeObject(object);
                String string = outputStream.toString("ISO-8859-1");
                string = URLEncoder.encode(string, "UTF-8");
                out.close();
                outputStream.close();
                editor.putString(key, string);
                editor.commit();
                SharedPreferencesCompat.apply(editor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getObject(String key) {
        if (key != null && key.length() > 0) {
            if (sp.contains(key)) {
                String string = sp.getString(key, null);
                if (string != null) {
                    String str = null;
                    ByteArrayInputStream byteArrayInputStream = null;
                    ObjectInputStream objectInputStream = null;
                    try {
                        str = URLDecoder.decode(string, "UTF-8");
                        byteArrayInputStream = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
                        objectInputStream = new ObjectInputStream(byteArrayInputStream);
                        return objectInputStream.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (byteArrayInputStream != null) {
                                byteArrayInputStream.close();
                            }
                            if (objectInputStream != null) {
                                objectInputStream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    public void putBooleanValue(String key, boolean value) {
        if (key != null && !key.equals("")) {
            editor = sp.edit();
            editor.putBoolean(key, value);
            SharedPreferencesCompat.apply(editor);
        }
    }

    public void putLongValue(String key, long value) {
        if (key != null && !key.equals("")) {
            editor = sp.edit();
            editor.putLong(key, value);
            SharedPreferencesCompat.apply(editor);
        }
    }

    public void putFloatValue(String key, Float value) {
        if (key != null && !key.equals("")) {
            editor = sp.edit();
            editor.putFloat(key, value);
            SharedPreferencesCompat.apply(editor);
        }
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void remove(String key) {
        editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {

        return sp.contains(key);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }
}
