package com.timer.multitimer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.timer.multitimer.databinding.ActivityMainBinding;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private CountDownTimer countDownTimer;
    private String timerData;
    private long timerTime;
    private String hoursTextViewString;
    private String minutesTextViewS;
    private String secondsTextViewS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.startButton.setOnClickListener(v -> {




            getDataForTimer();


            setTimer(timerTime);
            countDownTimer.start();
            timerTime = 0;


        });

        binding.stopButton.setOnClickListener(v -> {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            timerTime = 0;
        });

        binding.resetButton.setOnClickListener(v -> {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            timerTime = 0;
            binding.hoursTextView.getText().clear();
            binding.minutesTextView.getText().clear();
            binding.secondsTextView.getText().clear();
            setTimer(timerTime);
        });


        binding.addNewTimerButton.setOnClickListener(v -> {
            TextView hoursTextView = new TextView(this);
            binding.newTimerLinearLayout.addView(hoursTextView);
            hoursTextView.setText(String.format("%s:%s:%s"));
            setTimer(timerTime);

        });
    }

    private void getDataForTimer() {
         hoursTextViewString = binding.hoursTextView.getText().toString();
        if (!hoursTextViewString.isEmpty()) {
            int hours = Integer.parseInt(hoursTextViewString) * 3600000;
            timerTime = timerTime + hours;
        }

         minutesTextViewS = binding.minutesTextView.getText().toString();
        if (!minutesTextViewS.isEmpty()) {
            int minutes = Integer.parseInt(minutesTextViewS) * 60000;
            timerTime = timerTime + minutes;

        }

         secondsTextViewS = binding.secondsTextView.getText().toString();
        if (!secondsTextViewS.isEmpty()) {
            int secs = Integer.parseInt(secondsTextViewS) * 1000;
            timerTime = timerTime + secs;

        }
    }



    private void setTimer(long time) {
        countDownTimer = new CountDownTimer(time, 1) {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {
                binding.secondsTextView.setText(String.format("%d", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                binding.minutesTextView.setText(String.format("%d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))));

                binding.hoursTextView.setText(String.format("%d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished)));

            }


            @Override
            public void onFinish() {

                showMessage(binding.timerTitleTextView.getText().toString() + " " + "finish");
                //TODO add saud and mesege
            }
        };
    }

    public void showMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setNegativeButton("ОК",
                        (dialog, id) -> {
                            dialog.cancel();
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
