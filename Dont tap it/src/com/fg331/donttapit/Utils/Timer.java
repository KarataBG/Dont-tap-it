package com.fg331.donttapit.Utils;

import java.util.TimerTask;

public class Timer {

    private boolean running = false;
    private Thread thread;
    private int fps = 60;
    private int counter = 0;
    private int max = 60;
    private boolean sign;
    private java.util.Timer t;


    public void startTimerUp() {
        t = new java.util.Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                counter++;
            }
        }, 1000, 1000);
    }

    public void startTimerDown() {
        counter = max;
        t = new java.util.Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                counter--;
            }
        }, 1000, 1000);
    }

    public void stop(){
        t.cancel();
    }


    public int getCounter() {
        return counter;
    }
    public void resetCounter(){
        counter = 40;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
