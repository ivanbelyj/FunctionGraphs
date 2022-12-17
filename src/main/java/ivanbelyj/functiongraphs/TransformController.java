package ivanbelyj.functiongraphs;

import ivanbelyj.functiongraphs.models.Vector2;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.util.ArrayList;
import java.util.function.Consumer;

/** Класс, отвечающий за отслеживание изменений трансформации объектов рисуемой области **/
public class TransformController {
    private boolean _isDrag;
    private double _dragBeginX;
    private double _dragBeginY;

    private Transform _transform;
    private final ArrayList<Consumer<Transform>> _transformChangedEvent = new ArrayList<>();

    public TransformController(Canvas canvas, Transform initialTransform) {
        _transform = initialTransform;
        canvas.addEventFilter(MouseEvent.ANY, mouseEvent -> {
                if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                    _isDrag = true;
                    // До перетаскивания могло иметь место другое преобразование. В таком случае координаты мыши должны
                    // быть компенсированы, чтобы соответствовать преобразованной области просмотра
                    Vector2 transformedMouse = _transform.transform(new Vector2(mouseEvent.getX(), mouseEvent.getY()));
                    _dragBeginX = 2 * mouseEvent.getX() - transformedMouse.getX();
                    _dragBeginY = 2 * mouseEvent.getY() - transformedMouse.getY();
                } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED
                        || mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
                    _isDrag = false;
                } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                    if (_isDrag) {
                        double newX = mouseEvent.getX() - _dragBeginX;
                        double newY = mouseEvent.getY() - _dragBeginY;
                        _transform.setTranslate(newX, newY);

                        fireTransformChangedEvent(_transform);
                    }
                }
            }
        );
        canvas.setOnScroll((ScrollEvent event) -> {
            System.out.println(event.getDeltaY());
        });
    }

    public void addTransformChangedEventListener(Consumer<Transform> c) {
        _transformChangedEvent.add(c);
    }

    private void fireTransformChangedEvent(Transform transform) {
        for (Consumer<Transform> c : _transformChangedEvent) {
            c.accept(transform);
        }
    }
}
