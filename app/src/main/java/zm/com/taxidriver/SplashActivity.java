package zm.com.taxidriver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

import zm.com.taxidriver.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final int TAG_BLACK = 1;
    private static final int TAG_YELLOW = 2;

    private int index;
    private Timer timer;
    private TimerTask timerTask;
    private RelativeLayout r1, r2, r3, r4, r5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        timer = new Timer();
        index = 0;
        r1 = findViewById(R.id.r1);
        r2 = findViewById(R.id.r2);
        r3 = findViewById(R.id.r3);
        r4 = findViewById(R.id.r4);
        r5 = findViewById(R.id.r5);
        r1.setTag(TAG_BLACK+"");
        r2.setTag(TAG_YELLOW+"");
        r3.setTag(TAG_BLACK+"");
        r4.setTag(TAG_YELLOW+"");
        r5.setTag(TAG_BLACK+"");
        initializeTimerTask();
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
    }

    private void initializeTimerTask() {

        Thread thread = new Thread() {
            @Override
            public void run() {
               while (!isInterrupted()) {
                   try {
                       Thread.sleep(1000);
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               index++;
                               if(Integer.valueOf(r1.getTag().toString()) == TAG_BLACK) {
                                   r1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                   r1.setTag(TAG_YELLOW+"");
                               } else {
                                   r1.setBackgroundColor(getResources().getColor(R.color.black));
                                   r1.setTag(TAG_BLACK+"");
                               }

                               if(Integer.valueOf(r2.getTag().toString()) == TAG_BLACK) {
                                   r2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                   r2.setTag(TAG_YELLOW+"");
                               } else {
                                   r2.setBackgroundColor(getResources().getColor(R.color.black));
                                   r2.setTag(TAG_BLACK+"");
                               }

                               if(Integer.valueOf(r3.getTag().toString()) == TAG_BLACK) {
                                   r3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                   r3.setTag(TAG_YELLOW+"");
                               } else {
                                   r3.setBackgroundColor(getResources().getColor(R.color.black));
                                   r3.setTag(TAG_BLACK+"");
                               }

                               if(Integer.valueOf(r4.getTag().toString()) == TAG_BLACK) {
                                   r4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                   r4.setTag(TAG_YELLOW+"");
                               } else {
                                   r4.setBackgroundColor(getResources().getColor(R.color.black));
                                   r4.setTag(TAG_BLACK+"");
                               }

                               if(Integer.valueOf(r5.getTag().toString()) == TAG_BLACK) {
                                   r5.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                   r5.setTag(TAG_YELLOW+"");
                               } else {
                                   r5.setBackgroundColor(getResources().getColor(R.color.black));
                                   r5.setTag(TAG_BLACK+"");
                               }
                               if(index == 2) {
                                   startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                                   startActivity(new Intent(SplashActivity.this, TestActivity.class));
                                   finish();
                               }
                           }
                       });
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
            }
        };

        thread.start();

        timerTask = new TimerTask() {
            @Override
            public void run() {

            }
        };
    }
}
