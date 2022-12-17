package ivanbelyj.functiongraphs.models;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public interface ILine {
    Vector2 getLineStart();
    Vector2 getLineEnd();
    Paint getPaint();
}
