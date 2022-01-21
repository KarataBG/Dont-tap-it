package com.fg331.donttapit.Main;

import com.fg331.donttapit.Utils.MouseListener;

public class Handler {

    private Game game;

    public Handler(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public boolean[][] getMatrix() {
        return game.getMatrix();
    }

    public MouseListener getMouseListener(){
        return game.getMouseListener();
    }
}
