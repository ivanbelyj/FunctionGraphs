package ivanbelyj.functiongraphs.models;

public class Vector2 {
    private final double _x;
    private final double _y;

    public Vector2(double x, double y) {
        _x = x;
        _y = y;
    }

    public double getX() {
        return _x;
    }

    public double getY() {
        return _y;
    }

    public static Vector2 zero() {
        return new Vector2(0, 0);
    }
}
