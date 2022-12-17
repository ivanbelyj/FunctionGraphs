package ivanbelyj.functiongraphs;

import ivanbelyj.functiongraphs.models.Vector2;

/** Представляет преобразования, которые могут быть применены к точке перед отрисовкой  **/
public class Transform {
    private Vector2 _translate;

    public Transform() {
        _translate = new Vector2(0, 0);
    }

    public Vector2 getTranslate() {
        return _translate;
    }
    public void setTranslate(double x, double y) {
        _translate = new Vector2(x, y);
    }

    public Vector2 transform(Vector2 v) {
        return new Vector2(v.getX() + _translate.getX(), v.getY() + _translate.getY());
    }

    /** Объект трансформации, не оказывающий эффект на отрисовку **/
    public static Transform none() {
        return new Transform();
    }
}
