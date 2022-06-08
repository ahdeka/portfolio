package com.example.FootBallUp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class League extends Fragment {

    private View view;
    private TextView TextView_epl;
    private TextView TextView_laliga;
    private String msg, msg2;
    private String url = "https://www.google.com/search?q=epl+%EC%9D%BC%EC%A0%95&newwindow=1&rlz=1C1IBEF_koKR981KR981&biw=1536&bih=722&ei=HAWbYoHYKpKToASI7qXICw&ved=0ahUKEwiB1oTWnpP4AhWSCYgKHQh3CbkQ4dUDCA4&uact=5&oq=epl+%EC%9D%BC%EC%A0%95&gs_lcp=Cgdnd3Mtd2l6EAMyBQgAEIAEMgUIABCABDIFCAAQgAQyBQgAEIAEMgUIABCABDIECAAQHjIECAAQHjIGCAAQHhAFMgYIABAeEAUyBggAEB4QBToHCAAQRxCwAzoLCAAQgAQQsQMQgwE6BAguEAM6EQguEIAEELEDEIMBEMcBENEDOhEILhCABBCxAxCDARDHARCvAToECAAQAzoICAAQgAQQsQM6DgguEIAEELEDEIMBENQCOg4ILhCABBCxAxDHARDRAzoLCC4QgAQQsQMQgwE6CAguEIAEELEDOgcIABCABBAKOhQILhCABBCxAxCDARDHARDRAxDUAjoOCC4QgAQQsQMQxwEQowJKBAhBGABKBAhGGABQmwZYwiVgqiZoDnABeAGAAd4BiAHfEJIBBjEuMTUuMZgBAKABAbABAMgBCsABAQ&sclient=gws-wiz";
    final Bundle bundle = new Bundle();
    //private String url = "http://www.google.com/search?q="+srchString;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.league, container, false);

        TextView_epl = view.findViewById(R.id.TextView_epl);

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                Bundle bundle = msg.getData();
                TextView_epl.setText(bundle.getString("message"));
            }
        };

        new Thread(){
            @Override
            public void run(){
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).get();
                    Elements elements = doc.select(".ayRjaf"); //ayRjaf
                    msg = elements.text();
                    bundle.putString("message", msg);
                    Message msg = handler.obtainMessage();
                    msg.setData(bundle);
                    handler.sendMessage(msg);


                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }.start();


        /*webView = view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true); // wide view port를 유연하게 설정
        webView.getSettings().setLoadWithOverviewMode(true); // 컨텐츠가 웹뷰 범위에 벗어날 경우 크기아 맞게 조절
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClientClass());
        webView.loadUrl(url);
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    if(keyCode == KeyEvent.KEYCODE_BACK){
                        if(webView!=null){
                            if(webView.canGoBack()){
                                webView.goBack();
                            }else{
                                getActivity().onBackPressed();
                            }
                        }
                    }
                }
                return true;
            }
        });*/

        return view;
    }

    /*private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }*/
}
