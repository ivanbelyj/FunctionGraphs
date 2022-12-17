package ivanbelyj.functiongraphs.models;

public class Rectangle {
    private double _x;
    private double _y;
    private double _width;
    private double _height;

    public Rectangle(double x, double y, double width, double height) {
        this._x = x;
        this._y = y;
        this._width = width;
        this._height = height;
    }

    public double getX() {
        return _x;
    }

    public double getY() {
        return _y;
    }

    public double getWidth() {
        return _width;
    }

    public double getHeight() {
        return _height;
    }
}
