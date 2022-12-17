package ivanbelyj.functiongraphs.drawing;

import ivanbelyj.functiongraphs.Transform;
import javafx.scene.canvas.GraphicsContext;
import ivanbelyj.functiongraphs.models.*;
import javafx.scene.paint.Color;

public class RectDrawer implements IDrawer {
    private Rectangle _rect;
    public RectDrawer(Rectangle rect) {
        _rect = rect;
    }
    @Override
    public void draw(GraphicsContext ctx, ViewportContext viewportContext) {
        Vector2 transformed = viewportContext.getTransform().transform(new Vector2(_rect.getX(), _rect.getY()));
        ctx.setFill(Color.SLATEGREY);
        ctx.fillRect(transformed.getX(), transformed.getY(), _rect.getWidth(), _rect.getHeight());
    }
}
