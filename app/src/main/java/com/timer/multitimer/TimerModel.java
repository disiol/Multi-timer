package com.timer.multitimer;

public class TimerModel {

    private final String timerTitle;
    private String hoursTextViewString;
    private String minutesTextViewS;
    private String secondsTextViewS;
    private long timerTime;


    public TimerModel(String hoursTextViewString, String minutesTextViewS, String secondsTextViewS, long timerTime, String timerTitle) {
        this.hoursTextViewString = hoursTextViewString;
        this.minutesTextViewS = minutesTextViewS;
        this.secondsTextViewS = secondsTextViewS;
        this.timerTime = timerTime;
        this.timerTitle = timerTitle;
    }

    public String getHoursTextViewString() {
        return hoursTextViewString;
    }

    public String getMinutesTextViewS() {
        return minutesTextViewS;
    }

    public String getSecondsTextViewS() {
        return secondsTextViewS;
    }

    public long getTimerTime() {
        return timerTime;
    }

    public String getTimerTitle() {
        return timerTitle;
    }

    @Override
    public String toString() {
        return "TimerModel{" +
                "timerTitle='" + timerTitle + '\'' +
                ", hoursTextViewString='" + hoursTextViewString + '\'' +
                ", minutesTextViewS='" + minutesTextViewS + '\'' +
                ", secondsTextViewS='" + secondsTextViewS + '\'' +
                ", timerTime=" + timerTime +
                '}';
    }
}
