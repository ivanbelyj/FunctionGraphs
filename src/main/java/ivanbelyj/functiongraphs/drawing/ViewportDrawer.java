package ivanbelyj.functiongraphs.drawing;

import ivanbelyj.functiongraphs.Transform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class ViewportDrawer {
    private static final Paint BACKGROUND_PAINT = Color.WHITE;

    private ArrayList<IDrawer> _drawers = new ArrayList<>();
    public void draw(GraphicsContext ctx, double viewWidth, double viewHeight, Transform transform) {
        ctx.setFill(BACKGROUND_PAINT);
        ctx.fillRect(0, 0, viewWidth, viewHeight);
        for (IDrawer drawer : _drawers) {
            drawer.draw(ctx, new ViewportContext(transform, viewWidth, viewHeight));
        }
    }

    public void addDrawer(IDrawer drawable) {
        _drawers.add(drawable);
    }
}
