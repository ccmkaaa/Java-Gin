package _1.Department;

import java.io.Serializable;
import java.util.List;
import _1.Employee.*;

public class DataPacket implements Serializable {
    private List<Employee> employees;

    public DataPacket(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
