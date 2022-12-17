package ivanbelyj.functiongraphs.drawing;

import ivanbelyj.functiongraphs.models.IFunction;
import ivanbelyj.functiongraphs.models.Line;
import ivanbelyj.functiongraphs.models.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

/** Отрисовщик графика математической функции **/
public class FunctionDrawer implements IDrawer {
    private IFunction _function;
    /** Скольким пикселям будет соответствовать цена деления графика **/
    private double _pixelsPerUnit;

    private ViewportContext _viewportCtx;
    private GraphicsContext _ctx;

    private Paint _paint;

    public FunctionDrawer(IFunction function, double pixelsPerUnit, Paint paint) {
        _function = function;
        _pixelsPerUnit = pixelsPerUnit;
        _paint = paint;
    }
    @Override
    public void draw(GraphicsContext ctx, ViewportContext viewportContext) {
        _viewportCtx = viewportContext;
        _ctx = ctx;

        ctx.setStroke(_paint);
        ctx.setLineWidth(2);
        Vector2 zero = viewportContext.getTransform().getTranslate();

        Vector2 lastLeftDrawn = null;
        Vector2 lastRightDrawn = null;
        for (double pos = 0 - zero.getX(); pos < viewportContext.getWidth() - zero.getX(); pos += 1) {
            lastLeftDrawn = drawGraphLine(zero.getX() + pos, zero.getX(), lastLeftDrawn);
            lastRightDrawn = drawGraphLine(zero.getX() - pos, zero.getX(), lastRightDrawn);
        }
    }

    private Vector2 drawGraphLine(double pos, double zeroPos, Vector2 prevPoint) {
        double x = (pos - zeroPos) / _pixelsPerUnit;
        double y = _function.f(x);
        double screenY = _viewportCtx.getTransform().transform(new Vector2(0, -y * _pixelsPerUnit)).getY();
        double lastX = prevPoint != null ? prevPoint.getX() : pos;
        double lastY = prevPoint != null ? prevPoint.getY() : screenY;

        drawLine(lastX, lastY, pos, screenY);

        return new Vector2(pos, screenY);
    }

    private void drawLine(double x0, double y0, double x1, double y1) {
//        BresenhamLineDrawer drawer = new BresenhamLineDrawer(new Line(new Vector2(x0, y0), new Vector2(x1, y1), _paint));
//        drawer.draw(_ctx, _viewportCtx);
        _ctx.strokeLine(x0, y0, x1, y1);
    }
}
