package com.timer.multitimer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.timer.multitimer.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private CountDownTimer countDownTimer;
    private String timerData;
    private long timerTime;
    private String hoursTextViewString;
    private String minutesTextViewS;
    private String secondsTextViewS;
    private List<TimerModel> timerModels = new ArrayList<>();
    private SharedPreferences preferences;
    private static final String TIMES_LIST = "times list";
    public static final String MYLOG_TEG = "my log";
    private Ringtone ringtone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        preferences = getPreferences(MODE_PRIVATE);

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
            if (timerModels.size() > 0) {
                adTimerToList();
            } else {
                getDataForTimer();
                adTimerToList();
            }

        });
    }

    private void adTimerToList() {
        String timerTitle = timerModels.get(timerModels.size() - 1).getTimerTitle();
        String hoursTextViewString = timerModels.get(timerModels.size() - 1).getHoursTextViewString();
        String minutesTextViewS = timerModels.get(timerModels.size() - 1).getMinutesTextViewS();
        String secondsTextViewS = timerModels.get(timerModels.size() - 1).getSecondsTextViewS();

        TextView timerTitleTextView = new TextView(this);
        TextView hoursTextView = new TextView(this);
        binding.timersList.addView(timerTitleTextView);
        binding.timersList.addView(hoursTextView);
        timerTitleTextView.setTextColor(getResources().getColor(R.color.colorAccent));
        hoursTextView.setTextColor(getResources().getColor(R.color.colorAccent));

        timerTitleTextView.setText(timerTitle);
        hoursTextView.setText(String.format("%s:%s:%s", hoursTextViewString, minutesTextViewS, secondsTextViewS));
    }

    private void getDataForTimer() {
        hoursTextViewString = binding.hoursTextView.getText().toString();
        if (!hoursTextViewString.isEmpty()) {
            int hours = Integer.parseInt(hoursTextViewString) * 3600000;
            timerTime = timerTime + hours;
        } else {
            hoursTextViewString = "00";
        }

        minutesTextViewS = binding.minutesTextView.getText().toString();
        if (!minutesTextViewS.isEmpty()) {
            int minutes = Integer.parseInt(minutesTextViewS) * 60000;
            timerTime = timerTime + minutes;

        } else {
            minutesTextViewS = "00";
        }

        secondsTextViewS = binding.secondsTextView.getText().toString();
        if (!secondsTextViewS.isEmpty()) {
            int secs = Integer.parseInt(secondsTextViewS) * 1000;
            timerTime = timerTime + secs;

        } else {
            secondsTextViewS = "00";
        }

        String timerTitle = binding.timerTitleTextView.getText().toString();

        timerModels.add(new TimerModel(hoursTextViewString, minutesTextViewS, secondsTextViewS, timerTime, timerTitle));

        Log.d(MYLOG_TEG, "timerModels " + timerModels.get(timerModels.size() - 1));
        Log.d(MYLOG_TEG, "timerModels.size() " + timerModels.size());
        //preferences.edit().putString(TIMES_LIST, String.valueOf(timerModels));

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
                try {
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    ringtone.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //TODO add nyvedomlenie
            }
        };
    }

    public void showMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setNegativeButton("ОК",
                        (dialog, id) -> {
                            ringtone.stop();
                            dialog.cancel();
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
