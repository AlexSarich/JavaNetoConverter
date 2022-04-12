package ru.netology.graphics.image;

import ru.netology.graphics.image.TextColorSchema;

import java.util.HashMap;
import java.util.Map;

public class ColorSchema implements TextColorSchema {
    private static final char[] CHARS = {'#', '$', '@', '%', '*', '+', '-', '`'};

    @Override
    public char convert(int color) {

        if (color >= 0 && color <= 32) {
            return CHARS[0];
        } else if (color > 32 && color < 64) {
            return CHARS[1];
        } else if (color >=64 && color < 96) {
            return CHARS[2];
        } else if (color >= 96 && color < 128) {
            return CHARS[3];
        } else if (color >=128 && color < 160) {
            return CHARS[4];
        } else if (color > 160 && color < 192) {
            return CHARS[5];
        } else if (color >= 192 && color < 224) {
            return CHARS[6];
        } else {
            return CHARS[7];
        }
    }
}
