package ivanbelyj.functiongraphs.drawing;

import ivanbelyj.functiongraphs.Transform;
import javafx.scene.canvas.GraphicsContext;

public interface IDrawer {
    void draw(GraphicsContext ctx, ViewportContext context);
}
