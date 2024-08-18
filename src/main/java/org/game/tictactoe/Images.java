/*
    Started by Ian Chen on 8/21/2024
    GitHub: https://github.com/IanC04
 */

package org.game.tictactoe;

import javafx.scene.image.Image;

import java.util.Map;
import java.util.Objects;

public enum Images {

    O("images/o.png"),
    X("images/x.png"),
    TICTACTOE("images/tictactoe.png"),
    BLANK();

    private static final Map<String, Images> IMAGES = Map.of(
            "O", O,
            "X", X,
            "tictactoe", TICTACTOE);

    private final Image image;

    Images() {
        image = null;
    }

    Images(String path) {
        image = new Image(Objects.requireNonNull(Application.class.getResourceAsStream(path)));
    }

    public static Image get(String name) {
        return IMAGES.getOrDefault(name, BLANK).image;
    }
}
