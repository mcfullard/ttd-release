package za.ttd.screens;

import za.ttd.game.Game;

public class ScreenController {

    private static ScreenController instance;
    private Game game;
    private AbstractScreen currentScreen, previousScreen;

    private ScreenController(Game game) {
        this.game = game;
        currentScreen = null;
        previousScreen = null;
    }

    public static ScreenController getInstance(Game game) {
        if (instance == null)
            instance = new ScreenController(game);

        return instance;
    }

    public void setScreen(ScreenTypes screen) {

        previousScreen = currentScreen;

        switch (screen) {
            case MAIN_MENU:
                currentScreen = MainMenuScreen.getInstance(game);
                break;
            case GAME:
                currentScreen = GameScreen.getInstance(game);
                break;
            case CONTROLS:
                currentScreen = ControlsScreen.getInstance(game);
                break;
            case CREDITS:
                currentScreen = CreditScreen.getInstance(game);
                break;
            case GAME_OVER:
                currentScreen = GameOverScreen.getInstance(game);
                break;
            case LOADING:
                currentScreen = LoadingScreen.getInstance(game);
                break;
            case PAUSE_MENU:
                currentScreen = PauseMenu.getInstance(game);
                break;
            case PLAYER_STATS:
                currentScreen = PlayerStatisticsScreen.getInstance(game);
                break;
            case SPLASH:
                currentScreen = SplashScreen.getInstance(game);
                break;
            case USER_INPUT:
                currentScreen = UserInputScreen.getInstance(game);
                break;
            case HIGH_SCORES:
                currentScreen = HighScoresScreen.getInstance(game);
                break;
            default:
                break;
        }
        game.setScreen(currentScreen);
    }

    public void previousScreen() {
        AbstractScreen tempScreen = currentScreen;

        currentScreen = previousScreen;
        previousScreen = tempScreen;

        game.setScreen(currentScreen);
    }
}
