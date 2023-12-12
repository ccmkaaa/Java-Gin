package _1.Department;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;
import java.io.Serializable;

import _1.Employee.Employee;
import _1.Employee.Manager;
import _1.Employee.Worker;

public class HR implements Serializable {
    private List<Employee> employees = new ArrayList<>();

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public HR() {
        this.employees = new ArrayList<>();
    }

    public HR(List<Employee> employees) {
        this.employees = employees;
    }

    public void processData() {
        // Добавьте вызовы ваших методов обработки данных
        awardLongServiceLeave();
        penalizeTopFiveDefectPercentage();
        dismissEmployeesOver60();
        rewardLowestDefectPercentageWorkers();
        fireManagerWithLowestRating();
        promoteWorkerToManager();
        removeEmployeesByIndices(Arrays.asList(39, 59, 68));
        sortEmployeesByIndex();
    }

    // у кого стаж работы больше 25 лет, премируют увеличением отпуска на 5 дней

    public void awardLongServiceLeave() {
        for (Employee employee : employees) {
            if (employee.getExperience() > 25) {
                int currentVacationDays = employee.getVacationDays();
                employee.setVacationDays(currentVacationDays + 5);
                System.out.println("Сотруднику " + employee.getFullName() + " (" + employee.getIndex() + ") "
                        + "увеличен отпуск на 5 дней." + "("
                        + employee.getExperience() + ")");
            }
        }
    }

    // Первых 5 человек с наибольшим процентом брака штрафуют уменьшением оклада на
    // 10%

    public void penalizeTopFiveDefectPercentage() {
        // Сортировка сотрудников по проценту брака в возрастающем порядке
        Collections.sort(employees, new Employee.EmployeeDefectPercentageComparator());

        // Перебор первых 5 сотрудников и уменьшение оклада на 10%
        int count = 0;
        for (Employee employee : employees) {
            if (employee instanceof Worker && count < 5) {
                double currentSalary = employee.getSalary();
                double newSalary = currentSalary - (0.1 * currentSalary); // Уменьшение оклада на 10%
                employee.setSalary(newSalary);
                System.out.println("Сотруднику " + employee.getFullName() + " уменьшен оклад на 10%.");
                // System.out.println(((Worker) employee).getDefectPercentage());
                count++;
            } else {
                continue;
            }
        }
    }

    // Метод для добавления нового Worker и заполнения данных из консоли
    public void addNewWorkerFromConsole() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите данные нового сотрудника (Worker):");
        int index = generateNewIndex();
        System.out.print("ФИО (String): ");
        String fullName = scanner.nextLine();
        System.out.print("Должность: ");
        String position = scanner.nextLine();
        System.out.print("Возраст (int): ");
        int age = scanner.nextInt();
        System.out.print("Оклад (double): ");
        double salary = scanner.nextDouble();
        System.out.print("Стаж работы (int): ");
        int experience = scanner.nextInt();
        System.out.print("Продолжительность отпуска (int): ");
        int vacationDays = scanner.nextInt();
        double defectPercentage = 0.0;

        scanner.close();
        // Создаем нового Worker и добавляем его в список сотрудников
        Worker newWorker = new Worker(index, fullName, position, age, salary, experience, vacationDays,
                defectPercentage);
        employees.add(newWorker);

        System.out.println("Новый сотрудник успешно добавлен.");
    }

    // Метод для увольнения сотрудников старше 60 лет (с итератором)
    public void dismissEmployeesOver60() {
        Iterator<Employee> iterator = employees.iterator();

        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            int age = employee.getAge();

            if (age > 60) {
                // System.out.println(employee.getAge());
                iterator.remove(); // Удалить сотрудника из списка
                System.out.println("Сотрудник " + employee.getFullName() + " уволен, так как ему старше 60 лет.");
            }
        }
    }

    // Метод для премирования первых трех с наименьшим процентом брака

    public void rewardLowestDefectPercentageWorkers() {
        // Сортировка сотрудников по проценту брака в возрастающем порядке
        Collections.sort(employees, Collections.reverseOrder(new Employee.EmployeeDefectPercentageComparator()));

        // Перебор первых трех сотрудников и увеличение их оклада на 10%
        int count = 0;
        for (Employee employee : employees) {
            if (employee instanceof Worker && count < 3) {
                double currentSalary = employee.getSalary();
                double newSalary = currentSalary + (0.1 * currentSalary); // Увеличение оклада на 10%
                employee.setSalary(newSalary);
                System.out.println("Сотруднику " + employee.getFullName() + " увеличен оклад на 10%.");
                // System.out.println(((Worker) employee).getDefectPercentage());
                count++;
            } else {
                continue;
            }
        }
    }

    // Увольнение руководителя с наименьшим рейтингом

    public void fireManagerWithLowestRating() {
        // Сортировка сотрудников-менеджеров по рейтингу популярности
        Collections.sort(employees, new Employee.ManagerRatingComparator());

        // Поиск менеджера с наименьшим рейтингом
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            if (employee instanceof Manager) {
                employees.remove(i); // Увольнение менеджера
                System.out.println("Уволен менеджер " + employee.getFullName() + " с наименьшим рейтингом.");
                // System.out.println(((Manager) employee).getRating());
                break; // Выходим из цикла после увольнения первого менеджера
            }
        }
    }

    // перевести Worker с Наибольшим стажем в Manager

    public void promoteWorkerToManager() {
        // Находим сотрудника с наибольшим стажем в классе Worker
        Worker workerWithHighestExperience = null;
        int highestExperience = 0;

        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                Worker worker = (Worker) employee;
                if (worker.getExperience() > highestExperience) {
                    highestExperience = worker.getExperience();
                    workerWithHighestExperience = worker;
                }
            }
        }

        if (workerWithHighestExperience != null) {
            // Создаем нового менеджера с данными сотрудника с наибольшим стажем
            Manager newManager = new Manager(
                    generateNewIndex(), // Генерируем новый индекс
                    workerWithHighestExperience.getFullName(),
                    workerWithHighestExperience.getPosition(),
                    workerWithHighestExperience.getAge(),
                    workerWithHighestExperience.getSalary(),
                    workerWithHighestExperience.getExperience(),
                    workerWithHighestExperience.getVacationDays(),
                    0);

            // Добавляем нового менеджера в список сотрудников
            employees.add(newManager);

            // Удаляем сотрудника с наибольшим стажем из списка
            employees.remove(workerWithHighestExperience);

            System.out.println("Сотрудник " + workerWithHighestExperience.getFullName() + " переведен в менеджеры.");
        } else {
            System.out.println("Нет доступных сотрудников класса Worker для продвижения в менеджеры.");
        }
    }

    // Увольнение сотрудников по индексам

    public void removeEmployeesByIndices(List<Integer> indicesToRemove) {
        // Создаем копию списка индексов для безопасной работы с ним
        List<Integer> indicesCopy = new ArrayList<>(indicesToRemove);

        // Перебираем индексы и удаляем соответствующих сотрудников из списка
        for (Integer index : indicesCopy) {
            Employee employeeToRemove = null;
            for (Employee employee : employees) {
                if (employee.getIndex() == index) {
                    employeeToRemove = employee;
                    break;
                }
            }

            // Если сотрудник найден, удаляем его из списка
            if (employeeToRemove != null) {
                employees.remove(employeeToRemove);
                System.out.println("Сотрудник с индексом " + index + " уволен.");
            } else {
                System.out.println(
                        "Сотрудник с индексом " + index + " не найден и не может быть уволен. (или уже был уволен)");
            }
        }
    }

    // сортировка сотрудников по индексам с использованием компаратора

    public void sortEmployeesByIndex() {
        Collections.sort(employees, new Employee.EmployeeComparator());
    }

    // вывод сотрудников (доп)
    public void printEmployees() {
        for (Employee employee : employees) {
            System.out.println("Индекс: " + employee.getIndex() + ", ФИО: " + employee.getFullName());

        }
    }

    // Метод для создания нового индекса для сотрудника (доп)
    private int generateNewIndex() {
        int maxIndex = 0;

        // Найти максимальный индекс среди существующих сотрудников
        for (Employee employee : employees) {
            if (employee.getIndex() > maxIndex) {
                maxIndex = employee.getIndex();
            }
        }
        return maxIndex + 1;
    }
}