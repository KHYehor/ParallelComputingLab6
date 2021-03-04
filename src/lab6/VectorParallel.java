package lab6;
import java.util.concurrent.RecursiveAction;

public class VectorParallel extends RecursiveAction {
    String operation;
    int from;
    int to;
    Vector vector;
    Matrix matrix;
    VectorParallel(String operation, int from, int to, Vector vector, Matrix matrix) {
        this.operation = operation;
        this.to = to;
        this.from = from;
        this.vector = vector;
        this.matrix = matrix;
    }

    @Override
    protected void compute() {
        if (operation == "sort") {
            vector.sort();
            return;
        }
        if ((to - from) < 2) {
            switch (operation) {
                case "mul":
                    vector.multiplyWithMatrix(matrix, from, to);
                    break;
                default:
                    break;
            }
        } else {
            int mid = (from + to) / 2;
            invokeAll(
                    new VectorParallel(operation, from, mid, vector, matrix),
                    new VectorParallel(operation, mid, to, vector, matrix)
            );
        }
    }
}
