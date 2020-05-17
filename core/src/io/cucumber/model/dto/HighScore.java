package io.cucumber.model.dto;

public class HighScore {

    private String name;
    private long value;

    public HighScore(String name, long value) {
        this.name = name;
        this.value = value;
    }

    public void init(String name, long value) {
        this.name = name;
        this.value = value;
    }
}
