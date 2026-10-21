import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) {
        try {
            try {
                LocateRegistry.createRegistry(1099);
            } catch (Exception e) {
                // Registry already started
            }

            // As per PDF
            MyRemote obj = new MyRemote();
            Naming.rebind("rmi://localhost/Add", obj);

            // As per Sample
            CalculatorImpl calcObj = new CalculatorImpl();
            Naming.rebind("rmi://localhost/Calculator", calcObj);

            System.out.println("Server is ready...");
            System.out.println("RMI server is running...");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
