package ivanbelyj.functiongraphs.models;

public class SinFunction implements IFunction {
    @Override
    public double f(double x) {
        return Math.sin(x);
    }
}
