package com.qiu.neteasemusic.fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.qiu.neteasemusic.Base.AbstractBaseFragment;
import com.qiu.neteasemusic.R;
import com.qiu.neteasemusic.Utils.ToastUtil;


/**
 * Created by qiu on 2017/4/11.
 * 动态
 */

public class StateFragment extends AbstractBaseFragment {
    private final int MESSAGE_NET_ID = 208;//扫码结果id
    private final String SQ_RESULT_CONTENT = "sq_result_content";//
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_state;
    }
    private Handler stateHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){
               case MESSAGE_NET_ID:
                   Bundle bundle = msg.getData();
                   AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(getContext());
                   String result = bundle.getString(SQ_RESULT_CONTENT);
                   ToastUtil.showToast(getContext(),result);
                   alertDialogBuilder.setTitle("扫码结果");
                   alertDialogBuilder.setMessage(result);
                   AlertDialog alertDialog = alertDialogBuilder.create();
                   alertDialog.show();//将dialog显示出来
                   break;
           }
        }
    };
    @Override
    protected void initView(View v) {
        Button button = (Button) v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new SQRunnable()).start();
            }
        });
    }
    @Override
    protected void initData() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null) {
            if(intentResult.getContents() == null) {
               ToastUtil.showToast(getContext(),R.string.SQ_content_null);
            } else {
                String ScanResult = intentResult.getContents();
                new Thread(new SQResultRunnable(ScanResult)).start();
            }
        } else {
            ToastUtil.showToast(getContext(),R.string.SQ_error);
        }
    }
    class SQResultRunnable implements Runnable{
        private String content;
        public SQResultRunnable(String content){
            this.content = content;
        }
        @Override
        public void run() {
            Message message = new Message();
            message.what = MESSAGE_NET_ID;
            Bundle bundle = new Bundle();
            bundle.putString(SQ_RESULT_CONTENT,content);
            message.setData(bundle);
            stateHandler.sendMessage(message);
        }
    }
    /**
     * 在其中执行扫码流程
     * */
    class SQRunnable implements Runnable{
        @Override
        public void run() {
            new IntentIntegrator(getActivity())
                    .setOrientationLocked(true)
                    .initiateScan(); // 初始化扫描
        }
    }
}
