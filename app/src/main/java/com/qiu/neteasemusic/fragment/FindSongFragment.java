package com.qiu.neteasemusic.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.qiu.neteasemusic.Base.AbstractBaseFragment;
import com.qiu.neteasemusic.R;
import com.qiu.neteasemusic.View.MyEditText;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by qiu on 2017/4/13.
 */

public class FindSongFragment extends AbstractBaseFragment {
    private MyEditText et_left;
    private MyEditText et_right;
    private String et_left_str;
    private String et_right_str;
    private final int HANDLER_LEFT = 1110;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HANDLER_LEFT:
                    break;
            }
        }
    };
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_find_song;
    }

    @Override
    protected void initView(View v) {
        et_left = (MyEditText) v.findViewById(R.id.et_left);
        et_right = (MyEditText) v.findViewById(R.id.et_right);
        et_left_str = "{\n" +
                "    \"code\": \"0\",\n" +
                "    \"msg\": \"ok\",\n" +
                "    \"time\": \"2016-10-13 16:13:48\"\n" +
                "}";
        et_right_str =  "{\n" +
                "    \"code\": \"0\",\n" +
                "    \"msg\": \"ok\",\n" +
                "    \"time\": \"2016-10-13 16:13:48\"\n" +
                "}";;
        et_left.setText(et_left_str);
        et_right.setText(et_right_str);

    }
    /**
     * 获取jsonMap
     * */
    private Map<String , String> getJsonMap(String str){
        Map<String , String> resultMap = new Gson().fromJson(str , LinkedHashMap.class);
        Log.d("qiuqiu" , resultMap.size()+"--"+resultMap.toString());
        return resultMap;
    }


    private MyTextWathcer textWathcer;
    private MyRightTextWathcer rightWathcer;
    @Override
    protected void initData() {
        textWathcer = new MyTextWathcer();
        rightWathcer = new MyRightTextWathcer();
        et_left.addTextChangedListener(textWathcer);
        et_right.addTextChangedListener(rightWathcer);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private class MyTextWathcer implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            et_left.removeTextChangedListener(this);
            et_left_str = charSequence.toString();
            SpannableString sp = new SpannableString(charSequence.toString());
            if(isGoodJson(charSequence.toString())){
                sp.setSpan(new BackgroundColorSpan(Color.TRANSPARENT),
                        0 ,charSequence.toString().length() - 1, Spannable.SPAN_MARK_MARK);
                et_left.setText(sp);
                et_right.setText(et_right_str);
                jsonChange();
            }else{
                sp.setSpan(new BackgroundColorSpan(Color.RED),
                        0 , charSequence.toString().length() - 1, Spannable.SPAN_MARK_MARK);
                et_left.setText(sp);

            }
            if(i2 == 1)
                et_left.setSelection(i + 1);
            else
                et_left.setSelection(i);

            et_left.addTextChangedListener(textWathcer);
        }
        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
    private class MyRightTextWathcer implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            et_right.removeTextChangedListener(this);
            et_right_str = charSequence.toString();
            SpannableString sp = new SpannableString(charSequence.toString());
            if(isGoodJson(charSequence.toString())){
                sp.setSpan(new BackgroundColorSpan(Color.TRANSPARENT),
                        0 ,charSequence.toString().length() - 1, Spannable.SPAN_MARK_MARK);
                et_right.setText(sp);
                et_left.setText(et_left_str);
                jsonChange();

            }else{
                sp.setSpan(new BackgroundColorSpan(Color.RED),
                        0 , charSequence.toString().length() - 1, Spannable.SPAN_MARK_MARK);
                et_right.setText(sp);

            }
            if(i2 == 1)
                et_right.setSelection(i + 1);
            else
                et_right.setSelection(i);

            et_right.addTextChangedListener(rightWathcer);
        }
        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private void jsonChange() {
        Map<String , String> left_map = new Gson().fromJson(et_left_str , LinkedHashMap.class);
        Map<String , String> right_map = new Gson().fromJson(et_right_str , LinkedHashMap.class);
        Map<String , Integer> time_map= new LinkedHashMap<String , Integer>();
        Iterator<Map.Entry<String ,String>> left_it = left_map.entrySet().iterator();
        Iterator<Map.Entry<String ,String>> right_it = right_map.entrySet().iterator();
        while(left_it.hasNext() && right_it.hasNext()){
            Map.Entry<String ,String> left_entry = left_it.next();
            Map.Entry<String ,String> right_entry = right_it.next();
            if(left_entry.getKey().equals(right_entry.getKey())&&
                    left_entry.getValue().equals(right_entry.getValue())){
            }else{
                int left_index = et_left_str.indexOf(left_entry.getKey());
                int right_index = et_right_str.indexOf(right_entry.getKey());
                SpannableString sp_left = new SpannableString(et_left_str);
                sp_left.setSpan(new BackgroundColorSpan(Color.RED),
                        left_index - left_entry.getKey().length() + 1,
                        left_index + left_entry.getValue().length() + 8, Spannable.SPAN_MARK_POINT);
                et_left.setText(sp_left);

                SpannableString sp_right = new SpannableString(et_right_str);
                sp_right.setSpan(new BackgroundColorSpan(Color.RED),
                        right_index - right_entry.getKey().length() + 1,
                        right_index + right_entry.getValue().length() + 8, Spannable.SPAN_MARK_POINT);
                et_right.setText(sp_right);
            }
        }
    }

    public static boolean isGoodJson(String json) {
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            return false;
        }
    }

}
