package za.ttd.screens;

import za.ttd.game.Game;

public final class ScreenController {

    private static ScreenController instance;
    private Game game;

    private ScreenController() {

    }

    public static ScreenController getInstance() {
        if (instance == null)
            instance = new ScreenController();

        return instance;
    }


    public void initialize(Game game) {
        this.game = game;
    }



}
