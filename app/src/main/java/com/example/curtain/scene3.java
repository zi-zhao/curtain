package com.example.curtain;

import android.app.Activity;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.util.Log;
import android.text.Html;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;
import com.friendlyarm.FriendlyThings.HardwareControler;
import com.friendlyarm.FriendlyThings.BoardType;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Context;
import android.content.Intent;

public class scene3 extends Activity implements OnClickListener {
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
    MediaPlayer mediaPlayer = null;

    public void showSound(int raw) {

        mediaPlayer = MediaPlayer.create(getApplicationContext(), raw);
        mediaPlayer.setVolume(1, 1);
        mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        if (devfd != -1) {
            HardwareControler.close(devfd);
            devfd = -1;
        }
        super.onDestroy();
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
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
        setContentView(R.layout.scene3);//线性布局


        String winTitle = devName + "," + speed + "," + dataBits + "," + stopBits;
        setTitle(winTitle);

        ((Button)findViewById(R.id.send1)).setOnClickListener(this);
        ((Button)findViewById(R.id.send2)).setOnClickListener(this);
        ((Button)findViewById(R.id.button1)).setOnClickListener(this);
        ((Button)findViewById(R.id.button2)).setOnClickListener(this);
        ((Button)findViewById(R.id.button3)).setOnClickListener(this);

        fromTextView = (TextView)findViewById(R.id.fromTextView);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(scene3.this,scene2.class);
                startActivity(intent);
            }
        });




        devfd = HardwareControler.openSerialPort( devName, speed, dataBits, stopBits );
        if (devfd >= 0) {
            timer.schedule(task, 0, 500);
        } else {
            devfd = -1;
            fromTextView.append("Fail to open " + devName + "!");
        }
    }

    private final int BUFSIZE = 512;
    private byte[] buf = new byte[BUFSIZE];
    private Timer timer = new Timer();
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (HardwareControler.select(devfd, 0, 0) == 1) {
                        int retSize = HardwareControler.read(devfd, buf, BUFSIZE);
                        if (retSize > 0) {
                            String str = new String(buf, 0, retSize);
                            remoteData.append(str);

                            //Log.d(TAG, "#### LineCount: " + fromTextView.getLineCount() + ", remoteData.length()=" + remoteData.length());
                            if (fromTextView.getLineCount() > MAXLINES) {
                                int nLineCount = fromTextView.getLineCount();
                                int i = 0;
                                for (i = 0; i < remoteData.length(); i++) {
                                    if (remoteData.charAt(i) == '\n') {
                                        nLineCount--;

                                        if (nLineCount <= MAXLINES) {
                                            break;
                                        }
                                    }
                                }
                                remoteData.delete(0, i);
                                //Log.d(TAG, "#### remoteData.delete(0, " + i + ")");
                                fromTextView.setText(remoteData.toString());
                            } else {
                                fromTextView.append(str);
                            }

                            ((ScrollView)findViewById(R.id.scroolView)).fullScroll(View.FOCUS_DOWN);
                        }
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private TimerTask task = new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.send1://颜色传感器
                String str_r ="A";

                if (str_r.length() > 0) {
                    int ret = HardwareControler.write(devfd, str_r.getBytes());
                    if (ret > 0) {
                        remoteData.append(str_r);
                    }
                }
                remoteData.append(str_r);
                showSound(R.raw.test);
                break;


        }
        switch (v.getId()) {
            case R.id.send2://距离传感器
                String str_0 ="R";

                if (str_0.length() > 0) {
                    int ret = HardwareControler.write(devfd, str_0.getBytes());
                    if (ret > 0) {
                        remoteData.append(str_0);
                    }
                }
                remoteData.append(str_0);
                showSound(R.raw.test);
                break;



        }


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
                showSound(R.raw.test);
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
                showSound(R.raw.test);
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
                showSound(R.raw.test);
                break;



        }



    }
}


