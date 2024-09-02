package com.example.curtain;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.friendlyarm.FriendlyThings.HardwareControler;


public class scene1 extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "SerialPort";
    private TextView fromTextView = null;

    private final int MAXLINES = 200;
    private StringBuilder remoteData = new StringBuilder(256 * MAXLINES);

    // NanoPC-T4 UART4
    private String devName = "/dev/ttyAMA3";
    private int speed = 115200;
    private int dataBits = 8;
    private int stopBits = 1;
    private int devfd = -1;


    @Override
    public void onDestroy() {

        timer.cancel();
        if (devfd != -1) {
            HardwareControler.close(devfd);
            devfd = -1;
        }
        super.onDestroy();
        super.onDestroy();
        
        
    }
    public void onPause(){
        if (devfd != -1) {
            HardwareControler.close(devfd);
            devfd = -1;
        }
        super.onPause();
    }
    public void onStop(){
        if (devfd != -1) {
            HardwareControler.close(devfd);
            devfd = -1;
        }
        super.onStop();
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scene1);//线性布局
        Button btn1 = (Button) findViewById(R.id.btn1);
        Button btn2 = (Button) findViewById(R.id.btn2);
        Button btn4 = (Button) findViewById(R.id.btn4);
        ((Button)findViewById(R.id.button1)).setOnClickListener(this);
        ((Button)findViewById(R.id.button2)).setOnClickListener(this);
        ((Button)findViewById(R.id.button3)).setOnClickListener(this);

        //天气预报
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://weather.cma.cn/");    //中国天气预报
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        //新闻
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://www.chinanews.com.cn/");    //中国新闻网
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(scene1.this,scene3.class);
                startActivity(intent1);
            }
    });
        public void onClick(View v)
        {


            switch (v.getId()) {
                case R.id.button1://舵机1
                    String str_1 ="0";

                    if (str_1.length() > 0) {
                        int ret = HardwareControler.write(devfd, str_1.getBytes());
                        if (ret > 0) {
                            remoteData.append(str_1);
                        }
                    }
                    remoteData.append(str_1);

                    break;




            }


            switch (v.getId()) {
                case R.id.button2://舵机2
                    String str_2 ="1";

                    if (str_2.length() > 0) {
                        int ret = HardwareControler.write(devfd, str_2.getBytes());
                        if (ret > 0) {
                            remoteData.append(str_2);
                        }
                    }
                    remoteData.append(str_2);

                    break;



            }

            switch (v.getId()) {
                case R.id.button3://舵机3
                    String str_3 ="2";
                    if (str_3.length() > 0) {
                        int ret = HardwareControler.write(devfd, str_3.getBytes());
                        if (ret > 0) {
                            remoteData.append(str_3);
                        }
                    }
                    remoteData.append(str_3);

                    break;



            }



        }
    }
    }
}