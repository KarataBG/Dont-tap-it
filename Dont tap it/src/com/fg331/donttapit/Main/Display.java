package com.fg331.donttapit.Main;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

public class Display {

    /**.
     *платното
     */
    private Canvas canvas;
    private JFrame frame; //рамката
    private GridBagConstraints c;
    //не използвано оформление

    private int width;
    private int height;
    //широчина и височина на рамката на екрана

    public Display(final int w, final int h) {
        width = w;
        height = h;

        frame();
    }

    private void frame() {
        frame = new JFrame("Don't Tap It");

        frame.setBounds(0, 0, width, height);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(width, height));
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setLocationRelativeTo(null);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();

    }

    public void addCanvas() {
        frame.add(canvas);
        frame.pack();
    }

    public void removeCanvas() {
        frame.remove(canvas);
        frame.pack();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getFrame() {
        return frame;
    }
}

