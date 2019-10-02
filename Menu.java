package com.yujin.a20190809;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;


public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    //뒤로가기 키 눌렀을 때 (제대로 안됌ㅁ)
    public boolean onKeyDown (int KeyCode, KeyEvent event) {
        if(KeyCode==KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(KeyCode, event);
    }
}
