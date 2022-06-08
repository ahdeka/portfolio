package com.example.FootBallUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Article article;
    private Schedule schedule;
    private League league;
    private Favorite favorite;
    private Menu menu;
    private long backKeyPressedTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_article:
                        setFrag(0);
                        break;
                    case R.id.action_schedule:
                        setFrag(1);
                        break;
                    case R.id.action_league:
                        setFrag(2);
                        break;
                    case R.id.action_favorite:
                        setFrag(3);
                        break;
                    case R.id.action_menu:
                        setFrag(4);
                        break;
                }

                return true;
            }
        });
        article = new Article();
        schedule = new Schedule();
        league = new League();
        favorite = new Favorite();
        menu = new Menu();
        setFrag(0); // 실행 시 첫 프래그먼트 화면을 무엇으로 지정할 것인지 선택


    }


    // 프래그먼트 교체가 일어나는 실행문(함수)
    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n){
            case 0:
                ft.replace(R.id.mainFrame, article);
                ft.commit();
                break;
            case 1 :
                ft.replace(R.id.mainFrame, schedule);
                ft.commit();
                break;
            case 2 :
                ft.replace(R.id.mainFrame, league);
                ft.commit();
                break;
            case 3 :
                ft.replace(R.id.mainFrame, favorite);
                ft.commit();
                break;
            case 4 :
                ft.replace(R.id.mainFrame, menu);
                ft.commit();
                break;

        }


    }

    @Override
    public void onBackPressed(){
        // 기존의 뒤로가기 버튼의 기능 제거
        // super.onBackPressed();

        // 2000 milliseconds = 2 seconds
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2초 이내에 뒤로가기 한번 더 클릭시 finish()(앱종료)
        if(System.currentTimeMillis() <=backKeyPressedTime + 2000){
            finish();
        }
    }


}