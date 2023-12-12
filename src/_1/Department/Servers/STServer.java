package _1.Department.Servers;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.io.IOException;

import _1.Department.*;
import _1.Employee.*;

public class STServer implements Runnable {
    private int port;

    public STServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер отдела статистики запущен на порту " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

                    // данные от клиента
                    DataPacket stDataPacket = (DataPacket) in.readObject();
                    List<Employee> stEmployees = stDataPacket.getEmployees();

                    // операции
                    List<Employee> stProcessedEmployees = processSTData(stEmployees);

                    // отправляем обработанный список клиенту
                    DataPacket stProcessedData = new DataPacket(stProcessedEmployees);
                    out.writeObject(stProcessedData);

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Employee> processSTData(List<Employee> stEmployees) {
        ST st = new ST(stEmployees);
        executeSTMethods(st);
        return st.getEmployees();
    }

    private void executeSTMethods(ST st) { // методы обработки
        st.findEmployeeWithLongestExperience();
        st.findOldestEmployee();
        st.findYoungestEmployee();
        st.findEmployeeWithLowestSalary();
        st.countYoungEmployeesAndPercentage();
        System.out.println("Методы ST выполнились");
        // st.printEmployees();
    }
}