package com.example.FootBallUp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Schedule extends Fragment {

    private View view;
    private TextView TextView_schedule;
    private String msg;
    private String url = "https://www.google.com/search?q=%EB%8C%80%ED%95%9C%EB%AF%BC%EA%B5%AD+%EC%B6%95%EA%B5%AC+%EC%9D%BC%EC%A0%95&newwindow=1&rlz=1C1IBEF_koKR981KR981&ei=nQyeYoiXC5bBhwOO1aSAAg&ved=0ahUKEwjI2eWfgpn4AhWW4GEKHY4qCSAQ4dUDCA4&uact=5&oq=%EB%8C%80%ED%95%9C%EB%AF%BC%EA%B5%AD+%EC%B6%95%EA%B5%AC+%EC%9D%BC%EC%A0%95&gs_lcp=Cgdnd3Mtd2l6EAMyBQgAEMQCMgUIABCABDILCAAQgAQQsQMQgwE6BwgAEEcQsAM6BAgAEAM6EQguEIAEELEDEIMBEMcBENEDOgcILhDUAhADOgsILhCABBCxAxCDAToKCAAQsQMQgwEQQzoNCC4QsQMQgwEQ1AIQQzoOCC4QgAQQsQMQgwEQ1AI6CAgAEIAEELEDOg4ILhCABBDHARDRAxDUAjoECAAQHjoGCAAQHhAPOgYIABAeEAg6CAgAEB4QCBAKOgYIABAeEAVKBAhBGABKBAhGGABQoQZYlRtg1htoBXABeAGAAZkBiAHxFZIBBDAuMjOYAQCgAQHIAQrAAQE&sclient=gws-wiz";
    final Bundle bundle = new Bundle();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.schedule, container, false);
        TextView_schedule = view.findViewById(R.id.TextView_schedule);

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                Bundle bundle = msg.getData();
                TextView_schedule.setText(bundle.getString("message"));
            }
        };

        new Thread(){
            @Override
            public void run(){
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).get();
                    Elements elements = doc.select(".KAIX8d");
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

        return view;
    }

}
