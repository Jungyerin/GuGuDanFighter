package com.jx372.gugudanfighter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

//position 값이랑 버튼 값이랑 싱크가 안맞음... 내일 맞추기 position을 맨 바깥으로 빼서 미리 할당한 onclick안에서 값 비교해보기!

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private Timer timer = new Timer();
    private final int[] BUTTON_IDS = {
            R.id.button_0_0, R.id.button_0_1 ,R.id.button_0_2,
            R.id.button_1_0, R.id.button_1_1, R.id.button_1_2,
            R.id.button_2_0, R.id.button_2_1, R.id.button_2_2
    };
    private int count = 0, total=0;
    private int a, b, position, preposition=99, buttonval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        timer.schedule(new PlayGameTimerTask(), 1000, 1000);
        playgame();
        for(int i=0; i<9; i++){
            findViewById(BUTTON_IDS[i]).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        total++;
        int viewid = v.getId();
        String textButton = ((Button) v).getText().toString();
        buttonval=Integer.valueOf(textButton);
        playgame();
    }

    private void playgame(){
        int[] temp = {                                      //중복체크를 위해 버튼에 있는 숫자를 임시로 저장
                0,0,0,0,0,0,0,0,0
        };
        a= randomize(1,9);
        b= randomize(1,9);
        position = randomize(0,8);

        //문제 출력
        ((TextView)findViewById(R.id.textViewLeftOperand)).setText(Integer.toString(a));
        ((TextView)findViewById(R.id.textViewRightOperand)).setText(Integer.toString(b));

        //정답은 랜덤하게 미리 자리를 정해놓음
        temp[position] = a*b;
        ((Button)findViewById(BUTTON_IDS[position])).setText(Integer.toString(a*b));

        for(int i=0;i<9;i++){
            //미리 할당되어 있는 정답은 skip
            if(temp[i] > 0)
            {
                i++;
            }
            //중복체크 후 값을 받아와서 비어있는 버튼에 할당
            int result = check(temp);
            ((Button)findViewById(BUTTON_IDS[i])).setText(Integer.toString(result));
            temp[i]=result;
        }

//        System.out.println("==================================");
//        System.out.println("버튼정답 : "+buttonval);
//        System.out.println("position : "+position+" 정답 : "+temp[position]+" 이전정답 : "+preposition);

        answercheck(buttonval, preposition);
        ((TextView)findViewById(R.id.textViewScore)).setText(Integer.toString(count)+"/"+Integer.toString(total));
        preposition=temp[position];

    }

    //정답일 경우 count++
    private int answercheck(int buttonval, int prepositon){
        if(buttonval == preposition ){
            count++;
            System.out.println("정답 : "+count);
        }
        return count;
    }

    //버튼의 답 후보 중 중복인지 아닌지 체크
    private int check(int[] temp) {
        int val;
        while(true) {
            boolean a = true;
            val = randomize(1,9)*randomize(1,9);
            for (int i = 0; i < temp.length; i++) {
                if (temp[i] == val) {
                    a = false;
                    break;
                }
            }
            if(a==true){
                break;
            }
        }
        return val;
    }

    //from에서 to까지 숫자를 랜덤으로 출력
    public int randomize(int from, int to){

        return (int) (Math.random() * to) + from ;
    }

    // 타이머
    private class PlayGameTimerTask extends TimerTask {
        private int seconds;
        @Override
        public void run() {
            ++seconds;
            if(seconds >=  30){
                timer.cancel();
                finish();       //게임을 다시 하시겠습니까??
                startActivity( new Intent( GameActivity.this, EndActivity.class  ) );
                return;
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((TextView)findViewById(R.id.textView22)).setText((30-seconds) + "초");
                }
            });

            // System.out.println(seconds);
        }
    }
}
