package _1.Department.Servers;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.io.IOException;

import _1.Employee.*;
import _1.Department.*;;

public class HRServer implements Runnable {
    private int port;

    public HRServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер отдела кадров запущен на порту " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

                    // получение списка сотрудников от клиента
                    DataPacket hrDataPacket = (DataPacket) in.readObject();
                    List<Employee> hrEmployees = hrDataPacket.getEmployees();

                    // операции
                    List<Employee> hrProcessedEmployees = processHRData(hrEmployees);

                    // отправляем обработанный список клиенту
                    DataPacket hrProcessedData = new DataPacket(hrProcessedEmployees);
                    out.writeObject(hrProcessedData);

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Employee> processHRData(List<Employee> hrEmployees) {
        HR hr = new HR(hrEmployees);
        executeHRMethods(hr);
        return hr.getEmployees();
    }

    private void executeHRMethods(HR hr) {
        // hr.printEmployees();
        System.out.println("");
        hr.awardLongServiceLeave();
        System.out.println("");
        hr.penalizeTopFiveDefectPercentage();
        System.out.println("");
        hr.dismissEmployeesOver60();
        System.out.println("");
        hr.rewardLowestDefectPercentageWorkers();
        System.out.println("");
        hr.fireManagerWithLowestRating();
        System.out.println("");
        hr.promoteWorkerToManager();
        System.out.println("");
        hr.removeEmployeesByIndices(Arrays.asList(39, 59, 68));
        hr.addNewWorkerFromConsole();
        System.out.println("");
        hr.sortEmployeesByIndex();
        System.out.println("");
        System.out.println("Методы HR выполнились");
    }

}