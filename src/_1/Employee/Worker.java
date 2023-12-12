package _1.Employee;

// import java.util.ArrayList;
// import java.util.Comparator;
// import java.util.List;
import java.io.Serializable;

public class Worker extends Employee implements Serializable {
    private double defectPercentage;

    public Worker() {
        super();
    }

    public Worker(int index, String fullName, String position, int age, double salary, int experience, int vacationDays,
            double defectPercentage) {
        super(index, fullName, position, age, salary, experience, vacationDays);
        this.defectPercentage = defectPercentage;
    }

    // Геттер и сеттер для defectPercentage
    public double getDefectPercentage() {
        return defectPercentage;
    }

    public void setDefectPercentage(double defectPercentage) {
        this.defectPercentage = defectPercentage;
    }

    // List<Worker> workers = new ArrayList<>();
    // // Заполните список объектами Worker
    // Collections.sort(workers, new Worker.DefectPercentageComparator());
}
