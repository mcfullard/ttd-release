package za.ttd.screens;

import za.ttd.game.Game;

public class ScreenController {

    private static ScreenController instance;
    private Game game;
    private AbstractScreen currentScreen;
    private Class previousScreen;

    private ScreenController() {
        this.game = Game.getInstance();
        currentScreen = null;
        previousScreen = null;
    }

    public static ScreenController getInstance() {
        if (instance == null)
            instance = new ScreenController();

        return instance;
    }

    public void setScreen(ScreenTypes screen) {

        try {
            previousScreen = currentScreen.getClass();
        }catch (Exception e) {}

        switch (screen) {
            case MAIN_MENU:
                currentScreen = new MainMenuScreen();
                break;
            case GAME:
                currentScreen = new GameScreen();
                break;
            case CONTROLS:
                currentScreen = new ControlsScreen();
                break;
            case CREDITS:
                currentScreen = new CreditScreen();
                break;
            case GAME_OVER:
                currentScreen = new GameOverScreen();
                break;
            case PAUSE_MENU:
                currentScreen = new PauseMenuScreen();
                break;
            case PLAYER_STATS:
                currentScreen = new PlayerStatisticsScreen();
                break;
            case SPLASH:
                currentScreen = new SplashScreen();
                break;
            case USER_INPUT:
                currentScreen = new UserInputScreen();
                break;
            case HIGH_SCORES:
                currentScreen = new HighScoresScreen();
                break;
            default:
                break;
        }
        game.setScreen(currentScreen);
    }

    public void previousScreen() throws Exception{

        currentScreen = (AbstractScreen) previousScreen.newInstance();

        game.setScreen(currentScreen);
    }
}
