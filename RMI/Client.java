import java.rmi.Naming;

public class Client {
    public static void main(String[] args) {
        try {
            // As per PDF:
            MyInterface obj = (MyInterface) Naming.lookup("rmi://localhost/Add");
            int result = obj.add(10, 20);
            System.out.println("Result = " + result);

            // As per Sample:
            Calculator calc = (Calculator) Naming.lookup("rmi://localhost/Calculator");
            System.out.println("Addition = " + calc.add(10, 20));
            System.out.println("Subtraction = " + calc.subtract(20, 10));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
