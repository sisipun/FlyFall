package io.cucumber.model.font;

public class FontInfo {

    private final int id;

    private final String fontPath;

    private final FontParams fontParams;

    public FontInfo(int id, String fontPath, FontParams fontParams) {
        this.id = id;
        this.fontPath = fontPath;
        this.fontParams = fontParams;
    }

    public int getId() {
        return id;
    }

    public String getFontPath() {
        return fontPath;
    }

    public FontParams getFontParams() {
        return fontParams;
    }
}
