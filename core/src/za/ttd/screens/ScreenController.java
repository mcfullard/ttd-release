package za.ttd.screens;

public final class ScreenController {

    private static ScreenController instance;

    private ScreenController() {

    }

    public static ScreenController getInstance() {
        if (instance == null)
            instance = new ScreenController();
        return instance;
    }


    



}
