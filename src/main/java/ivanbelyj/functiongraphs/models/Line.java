package ivanbelyj.functiongraphs.models;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Line implements ILine {
    private final Vector2 _start;
    private final Vector2 _end;
    private final Paint _paint;

    public Line(Vector2 lineStart, Vector2 lineEnd, Paint paint) {
        _start = lineStart;
        _end = lineEnd;
        _paint = paint;
    }

    @Override
    public Vector2 getLineStart() {
        return _start;
    }

    @Override
    public Vector2 getLineEnd() {
        return _end;
    }

    @Override
    public Paint getPaint() {
        return _paint;
    }

    public double x0() { return getLineStart().getX(); };
    public double y0() { return getLineStart().getY(); };
    public double x1() { return getLineEnd().getX(); };
    public double y1() { return getLineEnd().getY(); };
}
