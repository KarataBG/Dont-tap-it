package com.fg331.donttapit.States;

import com.fg331.donttapit.Main.Handler;
import com.fg331.donttapit.Utils.MouseListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameState1 extends State {

    private Handler handler;
    private BufferedImage image, arrow, redo;
    private Font font;

    private MouseListener mouseListener;
    private boolean[][] matrix;
    private boolean appearer, wronger;
    private int newX, newY;
    private int count = 21, bright;
    private int xOffset, yOffset;
    private int backgroundX = 800, backgroundY = 800;
    private int score = 0;
    private int arrowWidth = 200, arrowHeight, redoWidth = 200, redoHeight, tileSize = 200, redTileX, redTileY;
    private boolean playing = true;

    public GameState1(Handler handler) {
        this.handler = handler;
        mouseListener = handler.getGame().getMouseListener();
        matrix = handler.getGame().getMatrix();
        yOffset = (handler.getGame().getHeight() / 2) - (backgroundY / 2);
        xOffset = (handler.getGame().getWidth() / 2) - (backgroundX / 2);
        font = new Font("Arial", Font.PLAIN, 80);
        if (xOffset + backgroundX + arrowWidth + redoWidth > handler.getGame().getWidth()) {
            font = new Font("Arial", Font.PLAIN, 40);
            xOffset = handler.getGame().getWidth() - arrowWidth - redoWidth - backgroundX;
        }
        try {
//            image = ImageIO.read(new File("res/background.png"));
//            arrow = ImageIO.read(new File("res/arrow.png"));
//            redo = ImageIO.read(new File("res/redo.png"));

            String path = new File(".").getCanonicalPath();
            image = ImageIO.read(new File(path + "\\res\\" + "background.png"));
            arrow = ImageIO.read(new File(path + "\\res\\" + "arrow.png"));
            redo = ImageIO.read(new File(path + "\\res\\" + "redo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image, xOffset, yOffset, backgroundX, backgroundY, null);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (handler.getMatrix()[i][j]) {
                    g.setColor(Color.BLACK);
                    g.fillRect(xOffset + i * tileSize, yOffset + j * tileSize, tileSize, tileSize);
                }
            }
        }
        g.setFont(font);
        g.drawString("Timer: " + String.valueOf(handler.getGame().getT().getCounter()), xOffset, yOffset);
        g.drawString("Points: " + String.valueOf(score), xOffset + backgroundX / 2, yOffset);

        if (appearer) {
            bright -= 12;
            render2(g);
            count++;
            if (count == 21)
                appearer = false;
        }

        if (wronger) {
            render3(g);
        }

        if (!playing) {
            g.drawImage(arrow, handler.getGame().getWidth() - arrow.getWidth(), 0, arrow.getWidth(), arrow.getHeight(), null);
            g.drawImage(redo, handler.getGame().getWidth() - redo.getWidth() * 2, 0, redo.getWidth(), redo.getHeight(), null);
        }
    }

    public void render2(Graphics g) {
        g.setColor(new Color(255, 255, 255, bright));
        g.fillRect(xOffset + newX * tileSize, yOffset + newY * tileSize, tileSize, tileSize);
    }

    private void render3(Graphics g) {
        g.setColor(new Color(255, 0, 0, 255));
        g.fillRect(redTileX, redTileY, tileSize, tileSize);
    }

    @Override
    public void tick() {
        if (handler.getGame().getT().getCounter() == 0)
            playing = false;

        if (playing) {
            if (mouseListener.isPressed()) {
                if ((mouseListener.getX() - xOffset) / tileSize >= 0 && (mouseListener.getX() - xOffset) / tileSize <= 3 &&
                        (mouseListener.getY() - yOffset) / tileSize >= 0 && (mouseListener.getY() - yOffset) / tileSize <= 3)
                    if (matrix[(mouseListener.getX() - xOffset) / tileSize][(mouseListener.getY() - yOffset) / tileSize]) {
                        do {
                            newX = (int) (Math.random() * 4);
                            newY = (int) (Math.random() * 4);
                        } while (matrix[newX][newY]);
                        matrix[(mouseListener.getX() - xOffset) / tileSize][(mouseListener.getY() - yOffset) / tileSize] = false;
                        appearer = true;
                        bright = 255;
                        count = 0;
                        matrix[newX][newY] = true;
                        score++;
                    } else {
                        wronger = true;
                        redTileX = xOffset + ((mouseListener.getX() - xOffset) / tileSize) * tileSize;
                        redTileY = yOffset + ((mouseListener.getY() - yOffset) / tileSize) * tileSize;
                        playing = false;
                        handler.getGame().getT().stop();
                    }
                mouseListener.setPressed(false);
            }
        } else {
            if (mouseListener.isPressed()) {
                if (new Rectangle(handler.getGame().getWidth() - arrow.getWidth(), 0, arrow.getWidth(), arrow.getHeight()).contains(mouseListener.getPoint())) {
                    score = 0;
                    playing = true;
                    wronger = false;
                    handler.getMouseListener().setPressed(false);
                    State.currentState = handler.getGame().getStartState();
                } else if (new Rectangle(handler.getGame().getWidth() - redo.getWidth() * 2, 0, redo.getWidth(), redo.getHeight()).contains(mouseListener.getPoint())) {
                    score = 0;
                    wronger = false;
                    handler.getGame().getT().setMax(30);
                    handler.getGame().getT().startTimerDown();
                    handler.getMouseListener().setPressed(false);
                    playing = true;
                }
            }
        }
    }
}
