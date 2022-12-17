package ivanbelyj.functiongraphs.drawing;

import ivanbelyj.functiongraphs.Transform;

/** Отрисовщикам могут потребоваться данные о области просмотра, в которой они производят отрисовку **/
public class ViewportContext {
    private Transform _transform;
    private double _width;
    private double _height;

    public ViewportContext(Transform transform, double width, double height) {
        _transform = transform;
        _width = width;
        _height = height;
    }

    public Transform getTransform() {
        return _transform;
    }

    public double getWidth() {
        return _width;
    }

    public double getHeight() {
        return _height;
    }
}
