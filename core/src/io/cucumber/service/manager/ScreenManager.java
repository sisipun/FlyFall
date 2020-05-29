package io.cucumber.service.manager;

import io.cucumber.Game;
import io.cucumber.model.base.GameDifficulty;
import io.cucumber.model.level.LevelAssets;
import io.cucumber.view.ChooseLevelScreen;
import io.cucumber.view.GameDifficultyScreen;
import io.cucumber.view.GameOverScreen;
import io.cucumber.view.GameScreen;
import io.cucumber.view.HighScoreScreen;
import io.cucumber.view.StartScreen;

public class ScreenManager {

    private static StartScreen startScreen = null;
    private static ChooseLevelScreen chooseLevelScreen = null;
    private static GameScreen gameScreen = null;
    private static GameOverScreen gameOverScreen = null;
    private static GameDifficultyScreen gameDifficultyScreen = null;
    private static HighScoreScreen highScoreScreen = null;

    public static StartScreen getStartScreen(Game game, Integer bonusCount, Integer highScore,
                                             Boolean isSoundOn, Boolean isAcceleratorOn,
                                             LevelAssets levelAssets) {
        if (startScreen == null) {
            startScreen = new StartScreen(game, bonusCount, highScore, isSoundOn, isAcceleratorOn,
                    levelAssets);
            return startScreen;
        }
        return startScreen.init(bonusCount, highScore, isSoundOn, isAcceleratorOn,
                levelAssets);
    }

    public static ChooseLevelScreen getChooseLevelScreen(Game game, int bonusCount, int highScore,
                                                         boolean isSoundOn, boolean isAcceleratorOn,
                                                         LevelAssets levelAssets) {
        if (chooseLevelScreen == null) {
            chooseLevelScreen = new ChooseLevelScreen(game, bonusCount, highScore, isSoundOn,
                    isAcceleratorOn, levelAssets);
            return chooseLevelScreen;
        }
        return chooseLevelScreen.init(bonusCount, highScore, isSoundOn, isAcceleratorOn,
                levelAssets);
    }

    public static GameScreen getGameScreen(Game game, int bonusCount, int highScore,
                                           boolean isSoundOn, boolean isAcceleratorOn,
                                           GameDifficulty gameDifficulty, LevelAssets levelAssets) {
        if (gameScreen == null) {
            gameScreen = new GameScreen(game, bonusCount, highScore, isSoundOn,
                    isAcceleratorOn, gameDifficulty, levelAssets);
            return gameScreen;
        }
        return gameScreen.init(bonusCount, highScore, isSoundOn, isAcceleratorOn,
                gameDifficulty, levelAssets);
    }

    public static GameOverScreen getGameOverScreen(Game game, int score,
                                                   int bonusCount, int highScore,
                                                   boolean isSoundOn, boolean isAcceleratorOn,
                                                   GameDifficulty gameDifficulty, LevelAssets levelAssets) {
        if (gameOverScreen == null) {
            gameOverScreen = new GameOverScreen(game, score, bonusCount, highScore, isSoundOn,
                    isAcceleratorOn, gameDifficulty, levelAssets);
        }
        return gameOverScreen.init(score, bonusCount, highScore, isSoundOn, isAcceleratorOn,
                gameDifficulty, levelAssets);
    }

    public static GameDifficultyScreen getGameDifficultyScreen(Game game,
                                                               int bonusCount, int highScore,
                                                               boolean isSoundOn, boolean isAcceleratorOn,
                                                               LevelAssets levelAssets) {
        if (gameDifficultyScreen == null) {
            gameDifficultyScreen = new GameDifficultyScreen(game, bonusCount, highScore, isSoundOn,
                    isAcceleratorOn, levelAssets);
        }
        return gameDifficultyScreen.init(bonusCount, highScore, isSoundOn, isAcceleratorOn,
                levelAssets);
    }

    public static HighScoreScreen getHighScoreScreen(Game game, int bonusCount, int highScore,
                                                     boolean isSoundOn, boolean isAcceleratorOn,
                                                     LevelAssets levelAssets) {
        if (highScoreScreen == null) {
            highScoreScreen = new HighScoreScreen(game, bonusCount, highScore, isSoundOn,
                    isAcceleratorOn, levelAssets);
        }
        return highScoreScreen.init(bonusCount, highScore, isSoundOn, isAcceleratorOn, levelAssets);
    }
}
