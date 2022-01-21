package com.fg331.donttapit.Main;

import com.fg331.donttapit.States.State;

public class GameTicks implements Runnable {


    private static int tickRate = 240;
    private Game game;
    private boolean running = false;
    private Thread thread;


    public GameTicks(Game game) {
        this.game = game;

    }

    private void tick() {
        if (State.getCurrentState() != null)
            State.getCurrentState().tick();
    }

    @Override
    public void run() {
        while (running) {
            tick();

            try {
                Thread.sleep(1000 / tickRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stop();
    }

    //
    synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
