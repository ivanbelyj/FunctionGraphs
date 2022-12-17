package ivanbelyj.functiongraphs.drawing;

import ivanbelyj.functiongraphs.models.IFunction;

// y = A*x^3 + B*x^2 + C*x + D
public class TaskFunction implements IFunction {
    private double _a;
    private double _b;
    private double _c;
    private double _d;
    public TaskFunction(double a, double b, double c, double d) {
        _a = a;
        _b = b;
        _c = c;
        _d = d;
    }

    public void setA(double a) {
        _a = a;
    }
    public void setB(double b) { _b = b; }
    public void setC(double c) {
        _c = c;
    }
    public void setD(double d) {
        _d = d;
    }

    @Override
    public double f(double x) {
        return _a * x * x * x + _b * x * x + _c * x + _d;
    }
}
