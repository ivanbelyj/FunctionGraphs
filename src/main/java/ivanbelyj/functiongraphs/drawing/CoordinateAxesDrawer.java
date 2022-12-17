package ivanbelyj.functiongraphs.drawing;

import ivanbelyj.functiongraphs.Transform;
import ivanbelyj.functiongraphs.models.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CoordinateAxesDrawer implements IDrawer {
    /** Длина "засечки", обозначающей деление на оси графика **/
    private static final double AXE_STEP_SERIF_LENGTH = 4;

    /** Установлен во время отрисовки **/
    private GraphicsContext _ctx;
    private ViewportContext _viewportContext;

    private final double _pixelsPerAxeUnit;

    public CoordinateAxesDrawer(double pixelsPerAxeUnit) {
        _pixelsPerAxeUnit = pixelsPerAxeUnit;
    }

    @Override
    public void draw(GraphicsContext ctx, ViewportContext viewportContext) {
        this._ctx = ctx;
        this._viewportContext = viewportContext;

        // Трансформация смещает точку O (0, 0) в области просмотра
        Vector2 transformedO = viewportContext.getTransform().transform(Vector2.zero());

        drawGraphAxeSteps(transformedO.getX(), transformedO.getY(), false, _pixelsPerAxeUnit, viewportContext.getWidth());
        drawGraphAxeSteps(transformedO.getY(), transformedO.getX(), true, _pixelsPerAxeUnit, viewportContext.getHeight());

        // Рисование осей
        ctx.setStroke(Color.BLACK);
        ctx.setLineWidth(1);

        // Ось OX
        ctx.strokeLine(0, transformedO.getY(), viewportContext.getWidth(), transformedO.getY());

        // Ось OY
        ctx.strokeLine(transformedO.getX(), 0, transformedO.getX(), viewportContext.getHeight());
    }

    /** Отрисовывает деления оси графика.
     * zeroPos - координаты точки на canvas, которая соответствует точке 0 на отображаемой координатной оси.
     * axeCoord - координата оси. Для OX это y, для OY - x **/
    private void drawGraphAxeSteps(double zeroPos, double axeCoord, boolean isHorizontal, double pixelsPerStep, double viewportSize) {
        // Должны быть отрисованы только видимые деления (и, возможно, некоторые невидимые)
        for (double pos = 0; pos < Math.abs(zeroPos) + viewportSize; pos += pixelsPerStep) {
            drawLine(zeroPos + pos, isHorizontal);
            drawLine(zeroPos - pos, isHorizontal);

            drawDivisionOnPos(zeroPos - pos, axeCoord, isHorizontal);
            drawDivisionOnPos(zeroPos + pos, axeCoord, isHorizontal);
        }
    }

    private void drawDivisionOnPos(double pos, double axeCoord, boolean isHorizontal) {
        if (isHorizontal) {
            drawDivision(axeCoord,pos, true);
        } else {
            drawDivision(pos, axeCoord, false);
        }
    }

    private void drawDivision(double x, double y, boolean isHorizontal) {
        _ctx.setLineWidth(1);

        _ctx.setStroke(Color.LIGHTGRAY);
        double halfSerif = -AXE_STEP_SERIF_LENGTH / 2;
        if (isHorizontal) {
            _ctx.setStroke(Color.BLACK);
            _ctx.strokeLine( x - halfSerif,  y,  x + halfSerif,  y);
        } else {
            _ctx.setStroke(Color.BLACK);
            _ctx.strokeLine( x,  y - halfSerif,  x,  y + halfSerif);
        }
    }

    private void drawLine(double pos, boolean isHorizontal) {
        _ctx.setLineWidth(1);
        _ctx.setStroke(Color.LIGHTGRAY);

        if (isHorizontal) {
            // Линия по оси OX
            _ctx.strokeLine(0, pos, _viewportContext.getWidth(), pos);
        } else {
            // Линия по оси OY
            _ctx.strokeLine(pos, 0, pos, _viewportContext.getHeight());
        }
    }
}
