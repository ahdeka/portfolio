package com.example.FootBallUp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Article extends Fragment{

    private View view;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> localDataSet= new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    RequestQueue queue;



     public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.article, container, false);
        super.onCreate(savedInstanceState);


        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.Recycler_view); // 리사이클러뷰 아이디 연결
        mRecyclerView.setHasFixedSize(true); // setHasFixedSize : 어댑터의 내용을 변경해도 높이나 너비가 변경되지 않도록 하여 레이아웃을 그리는 비용이 많이드는 작업을 피한다.

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        queue = Volley.newRequestQueue(getActivity());

        News n = new News();
        n.getNews();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                n.getNews();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    class News extends Activity{

         public void getNews(){

             //String url = "https://newsapi.org/v2/top-headlines?q=축구&country=kr&apiKey=856f601c4b3e4248b88dcadd2eeac502";
             //String url = "https://newsapi.org/v2/everything?q=soccer&sortBy=publishedAt&apiKey=856f601c4b3e4248b88dcadd2eeac502";
             String url = "https://newsapi.org/v2/everything?q=football NOT NFL&domains=bbc.co.uk,espn.com&sortBy=publishedAt&apiKey=856f601c4b3e4248b88dcadd2eeac502";

             StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                     new Response.Listener<String>() {
                         @Override
                         public void onResponse(String response) {

                             try {
                                 JSONObject jsonObject = new JSONObject(response);

                                 JSONArray arrayArticles = jsonObject.getJSONArray("articles");

                                 // response -> NewsData 클래스에 분류한다
                                 ArrayList<NewsData> news = new ArrayList<>();

                                 for(int i = 0, j = arrayArticles.length(); i < j; i++){
                                     JSONObject obj = arrayArticles.getJSONObject(i);

                                     Log.d("NEWS", obj.toString());

                                     NewsData newsData = new NewsData();
                                     newsData.setTitle(obj.getString("title"));
                                     newsData.setUrlToImage(obj.getString("urlToImage"));
                                     newsData.setContent(obj.getString("content"));

                                     news.add(newsData);
                                 }

                                 mAdapter = new MyAdapter(news, getActivity(), new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         Object obj = view.getTag();
                                        if(obj != null){
                                            int position = (int)obj;
                                            ((MyAdapter)mAdapter).getNews(position).getContent();
                                            Intent intent = new Intent(News.this,NewsDetailActivity.class);
                                            intent.putExtra("news", ((MyAdapter)mAdapter).getNews(position));
                                            startActivity(intent);
                                        }
                                     }
                                 });
                                 mRecyclerView.setAdapter(mAdapter);

                             } catch (JSONException e) {
                                 e.printStackTrace();
                             }

                         }
                     }, new Response.ErrorListener() {
                 @Override
                 public void onErrorResponse(VolleyError error) { }
             }) {
                 @Override
                 public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("User-Agent", "Mozilla/5.0");

                    return headers;
                }
             };
             queue.add(stringRequest);

         }

    }
}
