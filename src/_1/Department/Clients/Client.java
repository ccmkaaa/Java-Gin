package _1.Department.Clients;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import _1.Department.DataPacket;
import _1.Employee.Employee;
import _1.Employee.Manager;
import _1.Employee.Worker;

public class Client {

    private static final String HR_SERVER_HOST = "127.0.0.1";
    private static final int HR_SERVER_PORT = 12345;
    private static final int ST_SERVER_PORT = 12346;

    public static List<Employee> sendToHRServer(List<Employee> employees) {
        return sendDataToServer(HR_SERVER_HOST, HR_SERVER_PORT, employees);
    }

    public static List<Employee> sendToSTServer(List<Employee> employees) {
        return sendDataToServer(HR_SERVER_HOST, ST_SERVER_PORT, employees);
    }

    private static List<Employee> sendDataToServer(String serverHost, int serverPort, List<Employee> employees) {
        try (Socket socket = new Socket(serverHost, serverPort);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // отправляем список сотрудников на сервер
            DataPacket dataPacket = new DataPacket(employees);
            out.writeObject(dataPacket);

            // получаем обработанный список сотрудников от сервера
            DataPacket processedData = (DataPacket) in.readObject();
            return processedData.getEmployees();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
