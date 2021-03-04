package lab6;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Arrays;

public class Vector {
    private int defaultSize = 200;
    private double[] _Vector = new double[defaultSize];
    private boolean inited = false;
    private String name;

    public Vector(String name) {
        this.name = name;
    }

    private static double kahanSum(double... fa)
    {
        double sum = 0.0;
        double c = 0.0;
        for (double f : fa) {
            double y = f - c;
            double t = sum + y;
            c = (t - sum) - y;
            sum = t;
        }
        return sum;
    }

    public Vector initWithRandomValues() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(name));
            for (int i = 0; i < defaultSize; i++) {
                double randomValue = new Random().nextDouble() * (100000000.0);
                _Vector[i] = randomValue;
                bw.write(String.valueOf(randomValue) + ", ");
            }
            bw.flush();
        } catch (IOException e) {}
        inited = true;
        return this;
    }

    public Vector saveFinalResult(String newName) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(newName));
            for (int i = 0; i < defaultSize; i++) {
                bw.write(String.valueOf(_Vector[i]) + ", ");
            }
            bw.flush();
        } catch (IOException e) {}
        return this;
    }

    public Vector sort() {
        if (!inited) throw new Error("Can't be called before init");
        Arrays.sort(_Vector);
        return this;
    }

    public Vector multiplyWithMatrix(Matrix matrix, int from, int to) {
        if (!inited) throw new Error("Can't be called before init");
        double[][] matrixData = matrix.get_Matrix();
        for (int i = from; i < to; i++) {
            double sum = 0.0;
            for (int j = 0; j < defaultSize; j++) {
                sum = kahanSum(_Vector[i], _Vector[j] * matrixData[j][i]);
            }
            _Vector[i] = sum;
        }
        return this;
    }

    public Vector printResult() {
        System.out.println(Arrays.toString(_Vector));
        return this;
    }
}
