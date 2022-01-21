package com.fg331.donttapit.States;

import java.awt.*;

public abstract class State {
    public static State currentState = null;

    public static State getCurrentState() {
        return currentState;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

}
