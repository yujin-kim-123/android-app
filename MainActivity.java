package com.yujin.a20190809;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebBackForwardList;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LinearLayout LF1, LF2, LU1, LU2, under_btn1, under_btn2;
    EditText text1, text2;
    ImageButton btn1,btn2;
    ImageButton back1, back2, forward1, forward2, home1, home2, set1, set2;
    WebView web1, web2;

    Animation translate_left;
    Animation translate_right;

    SharedPreferences pref;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"WrongViewCast", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //화면 좌우 드래그에 따른 애니메이션 객체 로딩
        translate_left = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        translate_right = AnimationUtils.loadAnimation(this, R.anim.translate_right);
        translate_left.setAnimationListener(new SlidingPageAnimationListener());
        translate_right.setAnimationListener(new SlidingPageAnimationListener());

        LF1 = (LinearLayout) findViewById(R.id.Linear_Full1);
        LF2 = (LinearLayout) findViewById(R.id.Linear_Full2);
        LU1 = (LinearLayout) findViewById(R.id.Linear_url1);
        LU2 = (LinearLayout) findViewById(R.id.Linear_url2);
        under_btn1 = (LinearLayout) findViewById(R.id.under_btn1);
        under_btn2 = (LinearLayout) findViewById(R.id.under_btn2);
        text1 = (EditText) findViewById(R.id.editText1);
        text2 = (EditText) findViewById(R.id.editText2);
        btn1 = (ImageButton) findViewById(R.id.button1);
        btn2 = (ImageButton) findViewById(R.id.button2);
        back1 = (ImageButton) findViewById(R.id.back1);
        back2 = (ImageButton) findViewById(R.id.back2);
        forward1 = (ImageButton) findViewById(R.id.forward1);
        forward2 = (ImageButton) findViewById(R.id.forward2);
        home1 = (ImageButton) findViewById(R.id.home1);
        home2 = (ImageButton) findViewById(R.id.home2);
        set1 = (ImageButton) findViewById(R.id.set1);
        set2 = (ImageButton) findViewById(R.id.set2);
        web1 = (WebView) findViewById(R.id.web1);
        web2 = (WebView) findViewById(R.id.web2);

        web1.setOnTouchListener(touchListener);
        web2.setOnTouchListener(touchListener);

        final String url1 = "https://www.google.com/";
        final String url2 = "https://www.daum.net/";

        pref = getSharedPreferences("VER", 0);

        web(web1, url1, text1);
        web(web2, url2, text2);

        //주소 검색 버튼 터치
        btn1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                String url = text1.getText().toString();
                String url_http = url.substring(0,4); //앞 부분 http 잘라서 저장

                if (url_http.equals("http"))    web(web1, url, text1);
                else    web(web1, "http://" + url, text1);

                return false;
            }
        });

        btn2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                String url = text2.getText().toString();
                String url_http = url.substring(0,4); //앞 부분 http 잘라서 저장

                if(url_http.equals("http")) web(web2, url, text2);
                else    web(web2, "http://" + url, text2);

                return false;
            }
        });

        //하단바의 뒤로가기 버튼 클릭시
        back1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(web1.canGoBack()) {
                    //뒤 페이지 주소 알아내기
                    WebBackForwardList list = web1.copyBackForwardList();
                    String back_url = list.getItemAtIndex(list.getCurrentIndex() - 1).getUrl();
                    web1.goBack();
                    text1.setText(back_url);
                }

                return false;
            }
        });

        back2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(web2.canGoBack()) {
                    //뒤 페이지 주소 알아내기
                    WebBackForwardList list = web2.copyBackForwardList();
                    String back_url = list.getItemAtIndex(list.getCurrentIndex() - 1).getUrl();
                    web2.goBack();
                    text2.setText(back_url);
                }
                return false;
            }
        });

        //하단바 앞으로가기 버튼 클릭시
        forward1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(web1.canGoForward()) {
                    //앞 페이지 주소 알아내기
                    WebBackForwardList list = web1.copyBackForwardList();
                    String forward_url = list.getItemAtIndex(list.getCurrentIndex() + 1).getUrl();
                    web1.goForward();
                    text1.setText(forward_url);
                }
                return false;
            }
        });

        forward2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(web2.canGoForward()) {
                    //앞 페이지 주소 알아내기
                    WebBackForwardList list = web2.copyBackForwardList();
                    String forward_url = list.getItemAtIndex(list.getCurrentIndex() + 1).getUrl();
                    web2.goForward();
                    text2.setText(forward_url);
                }
                return false;
            }
        });

        //하단바 홈 버튼 클릭시
        home1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                callurl();
                return false;
            }
        });

        home2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                callurl();
                return false;
            }
        });

        //하단의 설정 터치
        set1.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Menu.class);
                startActivity(intent);
            }
        });

        set2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Menu.class);
                startActivity(intent);
            }
        });

        //처음 설치 또는 업데이트 시 화면에 보여줌. 확인 누르면 다음 업데이트까지 XX
        try {
            PackageManager pm = this.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            final int  VERSION = packageInfo.versionCode;
            int old_ver = pref.getInt("version", 0);

            if(old_ver<VERSION) {
                LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dialog_custom, null);


                TextView custom1 = (TextView)view.findViewById(R.id.custom1);


                //입력하라눙눙
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(view);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences.Editor edit = pref.edit();
                        edit.putInt("version", VERSION);
                        edit.commit();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
           }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    //설정 액티비티에서 나왔을때
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onRestart() {
        super.onRestart();

        callurl();
    }

    //어플에서 나온 후 다시 실행했을때
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    protected void onResume() {
        super.onResume();

        callurl();
    }



    //웹뷰 터치
    WebView.OnTouchListener touchListener = new View.OnTouchListener() {
        final PointF mFirstPoint = new PointF();
        final PointF mLastPoint = new PointF();
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()) {
                case R.id.web1:
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mFirstPoint.set(event.getX(), event.getY()); //터치 좌표
                            Log.i("1.좌표확인", "" + mFirstPoint);
                            break;

                        case MotionEvent.ACTION_UP:
                            mLastPoint.set(event.getX(), event.getY()); //터치 좌표
                            //mVelocityTracker.computeCurrentVelocity(1000);
                            Log.i("2.좌표확인", "" + mLastPoint);

                            //int v = (int) mVelocityTracker.getXVelocity(); //x축 이동 속도를 구함
                            int Xgap = (int) (mFirstPoint.x - mLastPoint.x); //x축 드래그 이동거리

                            Log.i("이동 속도, 이동거리확인", "이동 속도:" + "이동 거리:" + Xgap);
                            Log.i("y 절반", "" + v.getHeight()/2);

                            //화면 반이상 드래그 했으면 화면 전환
                            if(Xgap > v.getWidth() / 2)  LF1.startAnimation(translate_left);

                            //url, 언더바 보임과 안보임
                            if(mFirstPoint.y < mLastPoint.y) {
                                LU1.setVisibility(View.VISIBLE);
                                under_btn1.setVisibility(View.VISIBLE);
                            } else {
                                LU1.setVisibility(View.GONE);
                                under_btn1.setVisibility(View.GONE);
                            }
                    } break;

                case R.id.web2:
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mFirstPoint.set(event.getX(), event.getY()); //터치 좌표
                            Log.i("1.좌표확인", "" + mFirstPoint);
                            break;

                        case MotionEvent.ACTION_UP:
                            mLastPoint.set(event.getX(), event.getY()); //터치 좌표
                            //mVelocityTracker.computeCurrentVelocity(1000);
                            Log.i("2.좌표확인", "" + mLastPoint);

                            //int v = (int) mVelocityTracker.getXVelocity(); //x축 이동 속도를 구함
                            int Xgap = (int) (mLastPoint.x - mFirstPoint.x); //드래그 이동거리

                            Log.i("이동 속도, 이동거리확인", "이동 속도:" + "이동 거리:" + Xgap);

                            //화면 반이상 드래그 했으면 화면 전환
                            if(Xgap > v.getWidth() / 2)   LF2.startAnimation(translate_left);

                            //url창, 언더바 보임과 안보임
                            if(mFirstPoint.y < mLastPoint.y) {
                                LU2.setVisibility(View.VISIBLE);
                                under_btn2.setVisibility(View.VISIBLE);
                            } else {
                                LU2.setVisibility(View.GONE);
                                under_btn2.setVisibility(View.GONE);
                            }
                    } break;
            }

            return false;
        }
    };

    //설정 액티비티에서 나왔을 때 실행될 메소드
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void callurl() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String url1 = pref.getString("input_url1", "");
        String url2 = pref.getString("input_url2", "");

        //메뉴에서 입력된 주소가 있으면서 앞에 http가 없을 때
        if(!url1.equals("") && !"http".equals(url1.substring(0,4))) {
            url1 = "http://" + url1;
        }
        if(!url2.equals("") && !"http".equals(url2.substring(0,4))) {
            url2 = "http://" + url2;
        }

        //입력된 주소가 있을때
        if(!url1.equals("")) {
            setting_web(web1, url1, text1);
        }
        if(!url2.equals("")) {
            setting_web(web2, url2, text2);
        }
    }


    //웹뷰 설정, 띄우기 메소드
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void web(final WebView web, String url, final EditText text) {
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());
        web.loadUrl(url);
        text.setText(url);

        //웹뷰 성능 향상
        web.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        web.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        web.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        web.getSettings().setEnableSmoothTransition(true);
        web.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        web.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                text.setText(url);

                return true;
            }
        });
    }

    //clearhistory가 필요한 경우에만
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void setting_web(final WebView web, String url, EditText text) {
        web(web, url, text);
        web.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                web.clearHistory();
                super.onPageFinished(view, url);
            }
        });
    }


    //애니메이션 리스너 정의
        private class SlidingPageAnimationListener implements Animation.AnimationListener {
        int isPage = 0; //0이면 첫페이지 1이면 두번째 페이지
        @Override
        public void onAnimationStart(Animation animation) { }

        //애니메이션이 끝날 때 호출되는 메소드
        @Override
        public void onAnimationEnd(Animation animation) {
            //현재 첫번째 페이지 일 때
            if (isPage == 0) {
                LF1.setVisibility(View.GONE);
                LF2.setVisibility(View.VISIBLE);
                isPage = 1;
            } //현재 두번째 페이지 일 때
            else if (isPage == 1) {
                LF2.setVisibility(View.GONE);
                LF1.setVisibility(View.VISIBLE);
                isPage = 0;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) { }
    }

    //뒤로가기 키 눌렀을 때
    public boolean onKeyDown (int KeyCode, KeyEvent event) {
        if(KeyCode==KeyEvent.KEYCODE_BACK && web1.canGoBack()) {
            web1.goBack();
            return true;
        }
        if(KeyCode==KeyEvent.KEYCODE_BACK && web2.canGoBack()) {
            web2.goBack();
            return true;
        }
        return super.onKeyDown(KeyCode, event);
    }

    //앱최초실행확인
    public boolean CheckAppFirstExecute() {
        SharedPreferences pref = getSharedPreferences("IsFirst", Activity.MODE_PRIVATE);
        boolean isFirst = pref.getBoolean("isFirst", false);
        if(!isFirst) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirst", true);
            editor.commit();
        }
        return !isFirst;
    }
}