package _1.Department;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

import _1.Employee.Employee;

public class ST implements Serializable {
    private List<Employee> employees;

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public ST() {
        this.employees = new ArrayList<>();
    }

    public ST(List<Employee> employees) {
        this.employees = employees;
    }

    // Метод для нахождения самого старого сотрудника
    public Employee findOldestEmployee() {
        Employee oldestEmployee = null;
        int maxAge = Integer.MIN_VALUE;

        for (Employee employee : employees) {
            if (employee.getAge() > maxAge) {
                maxAge = employee.getAge();
                oldestEmployee = employee;
            }
        }
        System.out.println("Сотрудник " + oldestEmployee.getFullName() + " самый старый." + " ("
                + oldestEmployee.getAge() + ")");
        return oldestEmployee;
    }

    // Метод для нахождения самого молодого сотрудника
    public Employee findYoungestEmployee() {
        Employee youngestEmployee = null;
        int minAge = Integer.MAX_VALUE;

        for (Employee employee : employees) {
            if (employee.getAge() < minAge) {
                minAge = employee.getAge();
                youngestEmployee = employee;
            }
        }
        System.out.println("Сотрудник " + youngestEmployee.getFullName() + " самый молодой." + " ("
                + youngestEmployee.getAge() + ")");
        return youngestEmployee;
    }

    // Метод для нахождения сотрудника с самым большим стажем работы
    public Employee findEmployeeWithLongestExperience() {
        Employee employeeWithLongestExperience = null;
        int maxExperience = Integer.MIN_VALUE;

        for (Employee employee : employees) {
            if (employee.getExperience() > maxExperience) {
                maxExperience = employee.getExperience();
                employeeWithLongestExperience = employee;
            }
        }
        System.out.println(
                "Сотрудник " + employeeWithLongestExperience.getFullName() + " имеет самый большой стаж." + " ("
                        + employeeWithLongestExperience.getExperience() + ")");
        return employeeWithLongestExperience;
    }

    // Метод для нахождения сотрудника с наименьшим окладом
    public Employee findEmployeeWithLowestSalary() {
        Employee employeeWithLowestSalary = null;
        double minSalary = Double.MAX_VALUE;

        for (Employee employee : employees) {
            if (employee.getSalary() < minSalary) {
                minSalary = employee.getSalary();
                employeeWithLowestSalary = employee;
            }
        }
        System.out.println(
                "Сотрудник " + employeeWithLowestSalary.getFullName() + " имеет самый маленький оклад" + " ("
                        + employeeWithLowestSalary.getSalary() + ")");
        return employeeWithLowestSalary;
    }

    // Метод для подсчета числа молодых сотрудников и их процента
    public void countYoungEmployeesAndPercentage() {
        int totalYoungEmployees = 0;

        for (Employee employee : employees) {
            if (employee.getAge() < 35) {
                totalYoungEmployees++;
            }
        }

        double percentage = ((double) totalYoungEmployees / employees.size()) * 100;

        System.out.println("Число молодых сотрудников: " + totalYoungEmployees);
        System.out.println("Процент молодых сотрудников: " + percentage + "%");
    }

    // вывод сотрудников (доп)
    public void printEmployees() {
        for (Employee employee : employees) {
            System.out.println("Индекс: " + employee.getIndex() + ", ФИО: " + employee.getFullName() + ", Возраст: "
                    + employee.getAge() + " ");
            // Вывод других полей сотрудника
        }
    }

}
