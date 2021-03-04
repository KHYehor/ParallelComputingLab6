package lab6;
import java.util.concurrent.RecursiveAction;

public class MatrixParallel extends RecursiveAction {
    String operation;
    int from;
    int to;
    Matrix matrix1;
    Matrix matrix2;
    MatrixParallel(String operation, int from, int to, Matrix matrix1, Matrix matrix2) {
        this.operation = operation;
        this.to = to;
        this.from = from;
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
    }

    @Override
    protected void compute() {
        if (operation == "sort") {
            matrix1.sort();
            return;
        }
        if ((to - from) < 2) {
            switch (operation) {
                case "sum":
                    matrix1.sumWithMatrix(matrix2, from, to);
                    break;
                case "mul":
//                    System.out.println("Am I here?");
//                    System.out.println(from);
//                    System.out.println(to);
                    matrix1.multiplyWithMatrix(matrix2, from, to);
                    break;
                default:
                    break;
            }
        } else {
            int mid = (from + to) / 2;
            invokeAll(
                    new MatrixParallel(operation, from, mid, matrix1, matrix2),
                    new MatrixParallel(operation, mid, to, matrix1, matrix2)
            );
        }
    }
}
