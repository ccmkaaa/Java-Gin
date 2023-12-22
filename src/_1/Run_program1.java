package _1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import _1.Employee.Employee;
import _1.Employee.Manager;
import _1.Employee.Worker;
import _1.Department.DataPacket;
import _1.Department.HR;
import _1.Department.ST;
import _1.Department.Clients.*;
import _1.Department.Servers.HRServer;
import _1.Department.Servers.STServer;

public class Run_program1 {

    private static final int HR_SERVER_PORT = 12345;
    private static final int ST_SERVER_PORT = 12346;

    public static void runProgram1() {
        withServer();
    }

    public static void withoutServer() {
        List<Employee> employees = new ArrayList<>();
        employees = createEmployees();

        HR hr = new HR(employees);
        hr.printEmployees();
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
        hr.removeEmployeesByIndices(Arrays.asList(17, 59, 68));
        hr.sortEmployeesByIndex();
        System.out.println("");

        // (через консоль)
        // hr.printEmployees();
        // hr.addNewWorkerFromConsole();

        System.out.println("");
        ST st = new ST(hr.getEmployees());
        st.findEmployeeWithLongestExperience();
        st.findOldestEmployee();
        st.findYoungestEmployee();
        st.findEmployeeWithLowestSalary();
        st.countYoungEmployeesAndPercentage();
        st.printEmployees();
    }

    // запуск серверов
    public static void withServer() {

        Thread hrServerThread = new Thread(new HRServer(HR_SERVER_PORT));
        hrServerThread.start();

        Thread stServerThread = new Thread(new STServer(ST_SERVER_PORT));
        stServerThread.start();

        waitForServersToStart();

        try {
            List<Employee> employees = createEmployees();

            // работа с перенаправлением списка сотрудников
            List<Employee> hrProcessedEmployees = Client.sendToHRServer(employees);

            List<Employee> stProcessedEmployees = Client.sendToSTServer(hrProcessedEmployees);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Employee> createEmployees() {
        List<Employee> employees = new ArrayList<>();

        Random random = new Random();

        // Создание 25 менеджеров
        for (int i = 1; i <= 25; i++) {
            Manager manager = new Manager();
            manager.setIndex(i + 1);
            manager.setFullName("Manager " + (i + 1));
            manager.setPosition("Manager");
            manager.setAge(random.nextInt(20) + 30); // Возраст от 30 до 49 лет
            manager.setSalary(50000 + random.nextInt(30000)); // Оклад от 50,000 до 79,999
            manager.setExperience(random.nextInt(31)); // Стаж от 0 до 25 лет
            manager.setVacationDays(30); // У всех менеджеров 30 дней отпуска
            manager.setRating(random.nextInt(11)); // Рейтинг популярности от 0 до 10
            employees.add(manager);
        }

        // Создание 50 рабочих
        for (int i = 26; i < 75; i++) {
            Worker worker = new Worker();
            worker.setIndex(i); // Индексы рабочих начинаются с 26
            worker.setFullName("Worker " + (i));
            worker.setPosition("Worker");
            worker.setAge(random.nextInt(40) + 30); // Возраст от 20 до 59 лет
            worker.setSalary(30000 + random.nextInt(20000)); // Оклад от 30,000 до 49,999
            worker.setExperience(random.nextInt(41)); // Стаж от 0 до 40 лет
            worker.setVacationDays(20); // У всех рабочих 20 дней отпуска
            worker.setDefectPercentage(random.nextDouble() * 10); // Процент брака от 0 до 10%
            employees.add(worker);
        }

        return employees;

    }

    private static void waitForServersToStart() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
