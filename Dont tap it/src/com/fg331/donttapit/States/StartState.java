package com.fg331.donttapit.States;

import com.fg331.donttapit.Main.Handler;
import com.fg331.donttapit.Utils.MouseListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StartState extends State {

    private Handler handler;
    private MouseListener mouseListener;
    private int button1X, button1Y;
    private int button2X, button2Y;
    private int buttonWidth = 200;
    private int buttonHeight = 80;
    private BufferedImage image, image1;


    public StartState(Handler handler) {
        this.handler = handler;
        mouseListener = handler.getMouseListener();
        button1X = handler.getGame().getDisplay().getFrame().getWidth() / 4;
        button1Y = handler.getGame().getDisplay().getFrame().getHeight() / 4;

        button2X = handler.getGame().getDisplay().getFrame().getWidth() / 4 + buttonWidth * 2;
        button2Y = handler.getGame().getDisplay().getFrame().getHeight() / 4;


        try {
            image = ImageIO.read(new File("res/FrenzyMode.png"));
            image1 = ImageIO.read(new File("res/EnduranceMode.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        handler.getGame().render();
    }

    @Override
    public void tick() {
        if (mouseListener.isCLick()) {
            if (new Rectangle(button1X, button1Y, buttonWidth, buttonHeight).contains(mouseListener.getPoint())) {
                mouseListener.setCLick(false);
                mouseListener.setPressed(false);
                handler.getGame().getT().setMax(30);
                handler.getGame().getT().startTimerDown();
                State.currentState = handler.getGame().getGameState1();
            } else if (new Rectangle(button2X, button2Y, buttonWidth, buttonHeight).contains(mouseListener.getPoint())) {
                mouseListener.setCLick(false);
                mouseListener.setPressed(false);
                handler.getGame().getT().setMax(10);
                handler.getGame().getT().startTimerDown();
                State.currentState = handler.getGame().getGameState2();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image, button1X, button1Y, null);
        g.drawImage(image1, button2X, button2Y, null);
    }
}
