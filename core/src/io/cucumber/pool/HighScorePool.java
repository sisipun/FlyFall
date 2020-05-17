package io.cucumber.pool;

import io.cucumber.model.dto.HighScore;

public class HighScorePool extends BasePool<HighScore> {

    public HighScorePool() {
        super(10, 10);
    }

    @Override
    protected HighScore newObject() {
        return new HighScore("", 0);
    }
}
