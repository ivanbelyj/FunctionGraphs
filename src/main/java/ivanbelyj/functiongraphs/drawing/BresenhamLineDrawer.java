package ivanbelyj.functiongraphs.drawing;

import ivanbelyj.functiongraphs.models.ILine;
import ivanbelyj.functiongraphs.models.Vector2;
import javafx.scene.canvas.GraphicsContext;

public class BresenhamLineDrawer implements IDrawer {
    private ILine _line;
    public BresenhamLineDrawer(ILine line) {
        _line = line;
    }

    private double x0() {
        return _line.getLineStart().getX();
    }
    private double x1() {
        return _line.getLineEnd().getX();
    }
    private double y0() {
        return _line.getLineStart().getY();
    }
    private double y1() {
        return _line.getLineEnd().getY();
    }

    // Если проекция отрезка на ось x меньше проекции на ось y или начало и
    // конец отрезка переставлены местами, то алгоритм не будет работать.
    // Для этого при выполнении алгоритма требуется менять местами координаты отрезка,
    // поворачивать оси.
    // Метод возвращает корректные для работы алгоритма данные, не изменяя поля LineDrawer
    private BresenhamLineData getCorrectLineData() {
        boolean k = Math.abs(y1() - y0()) > Math.abs(x1() - x0());
        BresenhamLineData res = new BresenhamLineData();

        // Отражаем линию по диагонали, если угол наклона слишком большой
        // (проекция по x меньше проекции по y)
        if (k)
        {
            res.start = new Vector2(y0(), x0());
            res.end = new Vector2(y1(), x1());

            res.isAngleTooBig = true;
        } else {
            // Иначе входные данные неизменны
            res.start = new Vector2(x0(), y0());
            res.end = new Vector2(x1(), y1());
        }

        // Если линия растёт не слева направо, то меняем начало и конец отрезка местами
        if (x0() > x1())
        {
            res.start = new Vector2(x1(), x0());
            res.end = new Vector2(y1(), y0());
        }

        return res;
    }

    @Override
    public void draw(GraphicsContext ctx, ViewportContext context) {
        BresenhamLineData lineData = getCorrectLineData();
        ctx.setStroke(_line.getPaint());

        // Трансформация в соотв. с областью просмотра
        lineData.start = context.getTransform().transform(lineData.start);
        lineData.end = context.getTransform().transform(lineData.end);

        drawLine(ctx, lineData);
    }

    private class BresenhamLineData {
        Vector2 start;
        Vector2 end;
        boolean isAngleTooBig;

        public double x0() { return start.getX(); };
        public double y0() { return start.getY(); };
        public double x1() { return end.getX(); };
        public double y1() { return end.getY(); };
    }

    private void drawLine(GraphicsContext ctx, BresenhamLineData lineData) {
        double dx = lineData.x1() - lineData.x0();
        double dy = Math.abs(lineData.y1() - lineData.y0());
        double inaccuracy = dx / 2;  // Оптимизация с умножением на dx, чтобы избавиться от лишних дробей
        double yStep = (lineData.y0() < lineData.y1()) ? 1 : -1;  // Направление роста по y
        double y = lineData.y0();
        for (double x = lineData.x0(); x <= lineData.x1(); x++)
        {
            putPixel(lineData.isAngleTooBig ? y : x, lineData.isAngleTooBig ? x : y, ctx);
            inaccuracy -= dy;
            if (inaccuracy < 0)
            {
                y += yStep;
                inaccuracy += dx;
            }
        }
    }
    private void putPixel(double x, double y, GraphicsContext ctx) {
        ctx.strokeLine(x, y, x, y);
    }
}