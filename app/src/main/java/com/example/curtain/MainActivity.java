package com.example.curtain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //声明控件
    private Button mBtnLogin;
    private EditText mEtUser;
    private EditText mEtPassword;

    // 声音提醒
// 声音提醒
    public void showSound(int raw) {
        MediaPlayer mediaPlayer = null;
        mediaPlayer = MediaPlayer.create(getApplicationContext(), raw);
        mediaPlayer.setVolume(1, 1);
        mediaPlayer.start();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找到控件
        mBtnLogin = findViewById(R.id.btn_login);
        Button btn_voice= (Button) findViewById(R.id.btn_voice);
        mEtUser = findViewById(R.id.et_1);
        mEtPassword = findViewById(R.id.et_2);


        btn_voice.setOnClickListener(v -> showSound(R.raw.welcome));

//        //实现直接跳转
//        mBtnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =null;
//                intent = new Intent(MainActivity.this,FunctionActivity.class);
//                startActivity(intent);
//            }
//        });
        //匹配密码进行登录
        mBtnLogin.setOnClickListener(this);
    }
    // 声音提醒

    public void onClick(View v){
        //获取输入名和密码
        String username = mEtUser.getText().toString();
        String password = mEtPassword.getText().toString();
        Intent intent = null;
        //假设匹配的正确
        if (username.equals("xinyue")&&password.equals("123456")){
            //如果正确
            intent = new Intent(MainActivity.this, scene1.class);
            startActivity(intent);
        }else{
            //不正确
        }

    }


}
