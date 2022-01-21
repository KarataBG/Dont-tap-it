package com.fg331.donttapit.Main;

import com.fg331.donttapit.States.*;
import com.fg331.donttapit.Utils.MouseListener;
import com.fg331.donttapit.Utils.Timer;

import java.awt.*;
import java.awt.image.BufferStrategy;

public final class Game implements Runnable {

    /**
     * броя на кадри в секунда за рисуване на играта.
     * виж метода render()
     */
    private final int fps = 60;
    /**
     * Големина на матрицата за игрални плочки.
     */
    private final int matrixSize = 4;
    /**
     * броят на първите плочки в играта.
     */
    private final int tileAmount = 3;
    /**
     * броя буфери (предварително създадени кадри).
     */
    private final int bufferAmount = 3;
    /**
     * Инстанция на класа GameState1 унаследяващ State.
     * Стадии в който се извършват рисуване и актуализация.
     * Може да се възложи след класа StartState.
     * Нуждае се от парамертър Handler виж класа Handler.
     */
    private GameState1 gameState1;
    /**
     * Инстанция на класа GameState2 унаследяващ State.
     * Стадии в който се извършват първата версия на рисуване и актуализация.
     * Може да се възложи след класа StartState.
     * Нуждае се от парамертър Handler виж класа Handler.
     */
    private GameState2 gameState2;
    /**
     * Инстанция на класа GameState3 унаследяващ State.
     * Стадии в който се извършват рисуване и актуализация.
     * Може да се възложи след класа StartState.
     * Нуждае се от парамертър Handler виж класа Handler.
     */
    private GameState3 gameState3;
    /**
     * не се изпозлва @deprecated
     * Инстанция на класа EndState унаследяващ State.
     * Стадии в който се извършват рисуване и актуализация.
     * Може да се възложи след класа StartState.
     * Нуждае се от парамертър Handler виж класа Handler.
     */
    private EndState endState;
    /**
     * стадии за избиране на стадии за игране с 1 сега (3 може би бъдеще) "бутони" (картини с вързано проверяване на мишка).
     * Инстанция на класа GameState1 унаследяващ State.
     * Стадии в който се извършват рисуване и актуализация.
     * Може да се възложи след класа StartState.
     * Нуждае се от парамертър Handler виж класа Handler.
     */
    private StartState startState;
    /**
     * булев израз от който зависи дали се циклира (продължава) играта.
     */
    private boolean running = false;
    /**
     * нишката на която прави русиването.
     */
    private Thread thread;
    /**
     * широчина на екрана.
     */
    private int width;
    /**
     * височина на екрана.
     */
    private int height;
    /**
     * Инстанция на класа Display.
     * Контролира и създава екрана.
     * JFrame и Canvas.
     */
    private Display display;
    /**
     * Класа който отговаря за връзки за променливи и методи между класове.
     * Нуждае се от параметър Game виж този клас.
     */
    private Handler handler;
    /**
     * Класа който отговаря за инпута на мишката.
     */
    private MouseListener mouseListener;
    /**
     * матрицата по която се играя играта; булеви изрази - true има плоча, false няма плоча.
     */
    private boolean[][] matrix = new boolean[matrixSize][matrixSize];
    /**
     * класа който отговаря за таймерите в играта.
     */
    private Timer t;

    /**
     * Конструктор който инициализира екрана инпута и таймерът
     * и матрицата и стадиите.
     */
    public Game() {
        width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(); //взима широчината на екрана и я възлага на променливата
        height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight(); //взима височината на екрана и я възлага на променливата
        handler = new Handler(this); //дефиниране на променливата и подаване на този клас за понататъчно дефиниране на локални променливи

        display = new Display(width, height); //дефиниране на променливата и подаване на променливите за размер на екрана
        mouseListener = new MouseListener(); //дефиниране на променливата
        display.getCanvas().addMouseListener(mouseListener); // добавяне на слушателя към платното
        t = new Timer(); //дефиниране на таймера
        new GameTicks(this).start(); //създаване на инстанция на нишката за актуализация

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                matrix[i][j] = false;       //дефиниране и изчистване на матрицата
            }
        }


        int a;
        int b; //малки променливи
        for (int i = 0; i < tileAmount; i++) {
            if (!matrix[a = (int) (Math.random() * matrixSize)][b = (int) (Math.random() * matrixSize)]) { //ако матрицата със "случайно" избрани координати е false да се сложи плочка там
                //стойности от 0 до 3 включително
                matrix[a][b] = true; //на случаен принцип възглагане на true(възлагане на позициите на плочки)
            } else {
                i--;
            } //ако се падне да сложи плочка където вече има се регресира цикъла
        }

        gameState1 = new GameState1(handler); //дефиниране на стадиите
        gameState2 = new GameState2(handler); //
        gameState3 = new GameState3(handler); //
        endState = new EndState(handler);     //
        startState = new StartState(handler); //

        State.currentState = startState; //възлагане на началния стадии
    }

    /**
     * Вика метода за рисуване на стадия като подава Graphics g за рисуване свързана с платното на екрана.
     */
    public void render() {
        BufferStrategy bs = display.getCanvas().getBufferStrategy(); //технологията за рисуване
        if (bs == null) {
            display.getCanvas().createBufferStrategy(bufferAmount); //създаване на технологията
            return;
        }
        Graphics g = bs.getDrawGraphics();

//        clear
        g.clearRect(0, 0, width, height); //чистене на екрана

        if (State.getCurrentState() != null) {
            State.getCurrentState().render(g); //викане на метода за рисуване на съответния стадии
        }
        bs.show(); //показване на последната будерирана картина
        g.dispose(); //изчистване на писалката
    }

    @Override
    public void run() {
        while (running) {
            render();
            try {
                Thread.sleep(1000 / fps);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stop();
    }

    synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     * Използва се от класа Handler и връща променливата
     */
    public MouseListener getMouseListener() {
        return mouseListener;
    }
    /**
     * @return
     * Използва се от класа Handler и връща променливата
     */
    public boolean[][] getMatrix() {
        return matrix;
    }
    /**
     * @return
     * Използва се от класа Handler и връща променлива
     */
    public int getWidth() {
        return width;
    }
    /**
     * @return
     * Използва се от класа Handler и връща променлива
     */
    public int getHeight() {
        return height;
    }
    /**
     * @return
     * Използва се от класа Handler и връща променлива
     */
    public Display getDisplay() {
        return display;
    }
    /**
     * @return
     * Използва се от класа Handler и връща променлива
     */
    public Timer getT() {
        return t;
    }

    public StartState getStartState() {
        return startState;
    }

    public GameState1 getGameState1() {
        return gameState1;
    }

    public GameState2 getGameState2() {
        return gameState2;
    }

    public GameState3 getGameState3() {
        return gameState3;
    }

    public EndState getEndState() {
        return endState;
    }
}
