package com.jx372.gugudanfighter.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import com.jx372.gugudanfighter.R;

public class TimerTaskActivity extends AppCompatActivity {

    private Timer timer = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_task);
        timer.schedule(new PlayGameTimerTask(), 1000, 1000);
    }

    private class PlayGameTimerTask extends TimerTask {
        private int seconds;
        @Override
        public void run() {
            ++seconds;
            if(seconds >=  30){
                timer.cancel();
                finish();
                return;
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((TextView)findViewById(R.id.textView)).setText((30-seconds) + "초");
                }
            });

           // System.out.println(seconds);
        }
    }
}
