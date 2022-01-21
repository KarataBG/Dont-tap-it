package com.fg331.donttapit.Utils;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener {

    private boolean isCLick;
    private boolean isPressed;
    private int x, y;

    @Override
    public void mouseClicked(MouseEvent e) {
        isCLick = true;

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //едно поле е 200 на 200
        isPressed = true;
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isPressed = false;
        isCLick = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public boolean isCLick() {
        return isCLick;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setCLick(boolean CLick) {
        isCLick = CLick;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point getPoint(){
        return new Point(x,y);
    }
}
