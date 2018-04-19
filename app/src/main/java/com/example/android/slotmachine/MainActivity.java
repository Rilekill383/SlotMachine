package com.example.android.slotmachine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Button;
import android.widget.Toast;
import android.os.Handler;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView Image1;
    private ImageView Image2;
    private ImageView Image3;
    private TextView points_display;
    private TextView Seekbar_display;
    private int SpeedValue = 1;
    private SeekBar seekbar;
    private Button button1;
    private Button button2;

    private Random Tguess;
    private UpdateB1 updateB1;
    private UpdateB2 updateB2;
    private UpdateB3 updateB3;
    private Handler ballhandler;

    private int[] treasures = {R.drawable.diamond, R.drawable.gold, R.drawable.oil, R.drawable.irs};

    private boolean wheelISrested;

    private int XP;
    private int SpinSubC1;
    private int SpinSubC2;
    private int SpinSubC3;
    private long I1rate;
    private long I2rate;
    private long I3rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Image1 = (ImageView) findViewById(R.id.Image_box1);
        Image1.setImageDrawable(getResources().getDrawable(treasures[0]));
        Image2 = (ImageView) findViewById(R.id.Image_box2);
        Image2.setImageDrawable(getResources().getDrawable(treasures[1]));
        Image3 = (ImageView) findViewById(R.id.Image_box3);
        Image3.setImageDrawable(getResources().getDrawable(treasures[3]));

        points_display = (TextView) findViewById(R.id.TextViewPoints);
        Seekbar_display = (TextView) findViewById(R.id.seek_bar_display);
        seekbar = (SeekBar) findViewById(R.id.Seek_bar_Slider);
        button1 = (Button) findViewById(R.id.button_start_stop);
        button2 = (Button) findViewById(R.id.button_rules);

        updateB1 = new UpdateB1();
        updateB2 = new UpdateB2();
        updateB3 = new UpdateB3();
        ballhandler = new Handler();
        Tguess = new Random();
        wheelISrested = true;
        SpinSubC1 = 0;
        SpinSubC2 = 1;
        SpinSubC3 = 3;


        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                I1rate=(seekbar.getProgress()+6)*Tguess.nextInt(2)+1;
                I2rate=(seekbar.getProgress()+6)*Tguess.nextInt(2)+1;
                I3rate=(seekbar.getProgress()+6)*Tguess.nextInt(2)+1;
                SpeedValue = progress;

                if (SpeedValue == 0) {
                    Seekbar_display.setText("Speed: Slow");
                }
                else if (SpeedValue == 1) {
                    Seekbar_display.setText("Speed: Medium");
                }
                else {
                    Seekbar_display.setText("Speed: Fast");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //The first button, to start and stop the spinning and shit
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wheelISrested == true) {
                    button1.setText("Stop");
                    wheelISrested = false;
                    I1rate = ((seekbar.getProgress()+1)*Tguess.nextInt(15)+1);
                    I2rate = ((seekbar.getProgress()+1)*Tguess.nextInt(10)+1);
                    I3rate = ((seekbar.getProgress()+1)*Tguess.nextInt(5)+1);
                    ballhandler.postDelayed(updateB1, I1rate*50);
                    ballhandler.postDelayed(updateB2, I2rate*50);
                    ballhandler.postDelayed(updateB3, I3rate*50);
                }
                else {
                    button1.setText("Start");
                    wheelISrested = true;
                    ballhandler.removeCallbacks(updateB1);
                    ballhandler.removeCallbacks(updateB2);
                    ballhandler.removeCallbacks(updateB3);
                    if (SpinSubC1 == SpinSubC2 && SpinSubC2 == SpinSubC3) {
                        Toast.makeText(getApplicationContext(), "JACKPOT! 100pts", Toast.LENGTH_LONG).show();
                        XP += 100;
                    }
                    else if (SpinSubC1 == 3 && SpinSubC2 == 3 && SpinSubC3 == 3) {
                        Toast.makeText(getApplicationContext(), "Hope you got a good lawyer...", Toast.LENGTH_SHORT).show();
                        XP -= 200;
                    }
                    else if (SpinSubC1 == 0 || SpinSubC2 == 0 || SpinSubC3 == 0) {
                        Toast.makeText(getApplicationContext(), "You win 30pts", Toast.LENGTH_SHORT).show();
                        XP += 30;
                    }
                    else if (SpinSubC1 == 1 || SpinSubC2 == 1 || SpinSubC3 == 1) {
                        Toast.makeText(getApplicationContext(), "You win 20pts", Toast.LENGTH_SHORT).show();
                        XP += 20;
                    }
                    else if (SpinSubC1 == 2 || SpinSubC2 == 2 || SpinSubC3 == 2) {
                        Toast.makeText(getApplicationContext(), "You win 10pts", Toast.LENGTH_SHORT).show();
                        XP += 10;
                    } else {
                        XP += 0;
                    }

                    points_display.setText("Points:" + "" + XP);
                    System.out.println(SpinSubC1);
                    System.out.println(SpinSubC2);
                    System.out.println(SpinSubC3);
                }
            }
        });

        //The button that sends you to the rules page, along with points
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send2rules = new Intent(getApplicationContext(), Main2Activity.class);
                send2rules.putExtra("current_Ps", XP);
                startActivity(send2rules);
            }
        });

        if (savedInstanceState != null)  {
            points_display.setText(savedInstanceState.getString("asdfgh"));
            XP = Integer.parseInt(points_display.getText().toString());
        }
    }

    //All the runnable implementations below
    private class UpdateB1 implements Runnable {
        @Override
        public void run() {
            if (SpinSubC1<3) {
                SpinSubC1+=1;
                Image1.setImageDrawable(getResources().getDrawable(treasures[SpinSubC1]));
            }
            else {
                SpinSubC1=0;
                Image1.setImageDrawable(getResources().getDrawable(treasures[0]));
            }
            ballhandler.postDelayed(updateB1, I1rate*100);
        }
    }

    private class UpdateB2 implements Runnable {
        @Override
        public void run() {
            if (SpinSubC2<3) {
                SpinSubC2+=1;
                Image2.setImageDrawable(getResources().getDrawable(treasures[SpinSubC2]));
            }
            else {
                SpinSubC2=0;
                Image2.setImageDrawable(getResources().getDrawable(treasures[0]));
            }
            ballhandler.postDelayed(updateB2, I2rate*100);
        }
    }

    private class UpdateB3 implements Runnable {
        @Override
        public void run() {
            if (SpinSubC3<3) {
                SpinSubC3+=1;
                Image3.setImageDrawable(getResources().getDrawable(treasures[SpinSubC3]));
            }
            else {
                SpinSubC3=0;
                Image3.setImageDrawable(getResources().getDrawable(treasures[0]));
            }
            ballhandler.postDelayed(updateB3, I3rate*100);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("asdfgh", points_display.getText().toString());
    }
}

