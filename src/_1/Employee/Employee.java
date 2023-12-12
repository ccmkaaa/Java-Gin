package _1.Employee;

import java.util.Comparator;
import java.io.Serializable;

public class Employee implements Serializable {
    private int index;
    private String fullName;
    private String position;
    private int age;
    private double salary;
    private int experience;
    private int vacationDays;

    // Геттеры и сеттеры для полей
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(int vacationDays) {
        this.vacationDays = vacationDays;
    }

    // конструкторы

    public Employee() {

    }

    public Employee(int index, String fullName, String position, int age, double salary, int experience,
            int vacationDays) {
        this.index = index;
        this.fullName = fullName;
        this.position = position;
        this.age = age;
        this.salary = salary;
        this.experience = experience;
        this.vacationDays = vacationDays;
    }

    // сортировка сотрудников по индексам

    public static class EmployeeComparator implements Comparator<Employee> { // необходим static для применения в HR
        @Override
        public int compare(Employee employee1, Employee employee2) {
            return Integer.compare(employee1.getIndex(), employee2.getIndex());
        }
    }

    // компаратор для сортировки сотрудников по проценту брака
    public static class EmployeeDefectPercentageComparator implements Comparator<Employee> {
        @Override
        public int compare(Employee employee1, Employee employee2) {
            if (employee1 instanceof Worker && employee2 instanceof Worker) {
                double defectPercentage1 = ((Worker) employee1).getDefectPercentage();
                double defectPercentage2 = ((Worker) employee2).getDefectPercentage();
                return Double.compare(defectPercentage2, defectPercentage1);
            }
            // Обработка случая, когда один из сотрудников не является исполнителем
            return 0;
        }
    };

    // компаратор для сортировки руководителей по популярности
    public static class ManagerRatingComparator implements Comparator<Employee> {
        @Override
        public int compare(Employee employee1, Employee employee2) {
            if (employee1 instanceof Manager && employee2 instanceof Manager) {
                int rating1 = ((Manager) employee1).getRating();
                int rating2 = ((Manager) employee2).getRating();
                return Integer.compare(rating1, rating2);
            }
            // Обработка случая, когда один из сотрудников не является менеджером
            return 0;
        }
    }

}