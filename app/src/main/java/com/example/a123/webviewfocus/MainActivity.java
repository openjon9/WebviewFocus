package com.example.a123.webviewfocus;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linear;
    private WebView webview;
    private WebSettings setting;
    private String url = "https://www.google.com/";
    private String url2 = "https://www.baidu.com/ ";
    private String test = "test";

    private MyHandler mhandler;
    private int i = 0;
    private MyTimerTask task;
    private Timer timer;
    private String str;
    private String[] str2;
    private boolean status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mhandler = new MyHandler();
        task = new MyTimerTask();
        timer = new Timer();
        webview = (WebView) findViewById(R.id.webview);
        setting = webview.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setDomStorageEnabled(true);
        webview.requestFocus();

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mhandler.sendEmptyMessage(1);

            }
        });
        webview.loadUrl(url);
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    timer.schedule(task, 0, 3000);
                    break;
                case 2:
                    Log.d("AA", i + "");
                    if (!status){
                        webview.loadUrl("javascript:setTimeout( function(){ try{ var t = document.getElementsByClassName('gLFyf')[0];t.focus();t.select();}catch(e){}},50);");
                        status = true;
                    }else {
                        str2 = str.split("null");
                        webview.loadUrl("javascript:setTimeout( function(){ try{ var t = document.getElementsByClassName('gLFyf')[0].value = '" + str2[1] + "';t.focus();t.select();}catch(e){}},50);");
                        i++;
                    }
                    break;
            }
        }
    }

    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            if (i == test.length()) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(webview.getWindowToken(), 0);

                task.cancel();
                timer.cancel();
                timer.purge();
                return;
            }
            if (status){
                str = str + test.charAt(i);
            }
            mhandler.sendEmptyMessage(2);

        }
    }
}
