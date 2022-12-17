package ivanbelyj.functiongraphs;

import ivanbelyj.functiongraphs.drawing.*;
import ivanbelyj.functiongraphs.models.Line;
import ivanbelyj.functiongraphs.models.Vector2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class Controller implements Initializable {
    private class TryGetTextFieldParamResult {
        private final boolean _isSuccessful;
        private final double _param;

        private TryGetTextFieldParamResult(boolean isSuccessful, double param) {
            _isSuccessful = isSuccessful;
            _param = param;
        }

        public boolean isSuccessful() {
            return _isSuccessful;
        }

        public double getParam() {
            return _param;
        }
    }

    @FXML
    private Canvas canvas;

    @FXML
    private TextField aParam;
    @FXML
    private TextField bParam;
    @FXML
    private TextField cParam;
    @FXML
    private TextField dParam;

    private ViewportDrawer _drawer;

    /** Трансформация области просмотра для смещения точки (0, 0) координатной сетки в центр области отрисовки **/
    private Transform _initialTransform;
    private Transform _actualTransform;

    private TaskFunction _function;
    private FunctionDrawer _funcDrawer;

    private static final double INITIAL_A = 0.3;
    private static final double INITIAL_B = 2;
    private static final double INITIAL_C = 3;
    private static final double INITIAL_D = 1;

    private static final double PIXELS_PER_UNIT = 50;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        _initialTransform = _actualTransform = new Transform();
        _initialTransform.setTranslate(canvas.getWidth() / 2, canvas.getHeight() / 2);

        // y = A*x^3 + B*x^2 + C*x + D
        initializeUIAndFuncDrawer();
        _drawer = new ViewportDrawer();

        // Добавление отрисовщиков
        _drawer.addDrawer(new CoordinateAxesDrawer(PIXELS_PER_UNIT));
        _drawer.addDrawer(_funcDrawer);
        // Тест рисования линий методом Брезенхема
//        Line line = new Line(new Vector2( 10, 10), new Vector2(100, 100), Color.MEDIUMVIOLETRED);
//        _drawer.addDrawer(new BresenhamLineDrawer(line));

        // Перерисовка canvas при изменении размера окна
        canvas.heightProperty().addListener((observableValue, oldVal, newVal) -> {
            redraw();
        });
        canvas.widthProperty().addListener((observableValue, oldVal, newVal) -> {
            redraw();
        });

        redraw();

        TransformController mouseHandler = new TransformController(canvas, _initialTransform);
        mouseHandler.addTransformChangedEventListener((Transform transform) -> {
            _actualTransform = transform;
            // _drawer.draw(canvas.getGraphicsContext2D(), canvas.getWidth(), canvas.getHeight(), transform);
            redraw();
        });
    }

    private void redraw() {
        _drawer.draw(canvas.getGraphicsContext2D(), canvas.getWidth(), canvas.getHeight(), _actualTransform);
    }

    private void initializeUIAndFuncDrawer() {
        aParam.setText(INITIAL_A + "");
        bParam.setText(INITIAL_B + "");
        cParam.setText(INITIAL_C + "");
        dParam.setText(INITIAL_D + "");

        _function = new TaskFunction(INITIAL_A, INITIAL_B, INITIAL_C, INITIAL_D);
        _funcDrawer = new FunctionDrawer(_function, PIXELS_PER_UNIT, Color.MEDIUMVIOLETRED);
    }

    private TryGetTextFieldParamResult tryGetParam(ActionEvent event) {
        String str = ((TextField)event.getSource()).getText();
        double param;
        try {
            param = Double.parseDouble(str);
        } catch (NumberFormatException exception) {
            return new TryGetTextFieldParamResult(false, 0);
        }
        return new TryGetTextFieldParamResult(true, param);
    }

    private void paramChanged(ActionEvent event, Consumer<Double> setParam) {
        TryGetTextFieldParamResult res = tryGetParam(event);
        if (res.isSuccessful()) {
            setParam.accept(res.getParam());
            redraw();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Некорректное значение параметра");
            alert.setHeaderText("Некорректное значение параметра");
            alert.setContentText("Пожалуйста, введите в поле ввода параметра число (дробные числа - через \".\")");
            alert.showAndWait();
        }
    }

    @FXML
    private void aParamChanged(ActionEvent event) {
        paramChanged(event, param -> {
            _function.setA(param);
        });
    }

    @FXML
    private void bParamChanged(ActionEvent event) {
        paramChanged(event, param -> {
            _function.setB(param);
        });
    }

    @FXML
    private void cParamChanged(ActionEvent event) {
        paramChanged(event, param -> {
            _function.setC(param);
        });
    }

    @FXML
    private void dParamChanged(ActionEvent event) {
        paramChanged(event, param -> {
            _function.setD(param);
        });
    }

}
