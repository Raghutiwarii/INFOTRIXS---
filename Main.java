import java.util.*;

class Employee {
    private static int nextEmployeeId = 1;

    private int employeeId;
    private String name;
    private String department;
    private double salary;
    private String joiningDate;
    private String level;

    public Employee(String name, String department, String joiningDate, String level) {
        this.employeeId = nextEmployeeId++;
        this.name = name;
        this.department = department;
        this.joiningDate = joiningDate;
        this.level = level;
        this.salary = calculateInitialSalary();
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public String getLevel() {
        return level;
    }

    private double calculateInitialSalary() {
        return 50000; // Initial salary of $50,000
    }

    public void updateSalary(double newSalary) {
        this.salary = newSalary;
    }

    public String generatePayStub() {
        return "Pay Stub for Employee " + name + " (ID: " + employeeId + ")\n" +
                "Salary: $" + salary + "\n";
    }
}

class PayrollSystem {
    private List<Employee> employees;
    private Map<String, List<Employee>> departmentEmployees;

    public PayrollSystem() {
        this.employees = new ArrayList<>();
        this.departmentEmployees = new HashMap<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        departmentEmployees.computeIfAbsent(employee.getDepartment(), k -> new ArrayList<>()).add(employee);
    }

    public void displayEmployeesByDepartment() {
        System.out.println("Employees by Department:");
        for (String department : departmentEmployees.keySet()) {
            System.out.println("Department: " + department);
            for (Employee employee : departmentEmployees.get(department)) {
                System.out.println("ID: " + employee.getEmployeeId() +
                        ", Name: " + employee.getName() +
                        ", Salary: $" + employee.getSalary() +
                        ", Joining Date: " + employee.getJoiningDate() +
                        ", Level: " + employee.getLevel());
            }
            System.out.println();
        }
    }

    public void displaySalaryByEmployee() {
        System.out.println("Salary by Employee:");
        for (Employee employee : employees) {
            System.out.println("ID: " + employee.getEmployeeId() +
                    ", Name: " + employee.getName() +
                    ", Salary: $" + employee.getSalary() +
                    ", Joining Date: " + employee.getJoiningDate() +
                    ", Level: " + employee.getLevel());
        }
    }

    public void displayEmployeeNames() {
        System.out.println("All Employee Names:");
        for (Employee employee : employees) {
            System.out.println("ID: " + employee.getEmployeeId() + ", Name: " + employee.getName());
        }
    }

    public void autoIncrementSalary() {
        for (Employee employee : employees) {
            double newSalary = employee.getSalary() * 1.05; // 5% increment
            employee.updateSalary(newSalary);
        }
    }

    public boolean deleteEmployee(int employeeId) {
        for (Iterator<Employee> iterator = employees.iterator(); iterator.hasNext(); ) {
            Employee employee = iterator.next();
            if (employee.getEmployeeId() == employeeId) {
                iterator.remove();
                departmentEmployees.get(employee.getDepartment()).remove(employee);
                return true;
            }
        }
        return false; 
    }

    public void generatePayStubs() {
        System.out.println("\nGenerating Pay Stubs:");
        for (Employee employee : employees) {
            System.out.println(employee.generatePayStub());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        PayrollSystem payrollSystem = new PayrollSystem();
        Scanner scanner = new Scanner(System.in);

        payrollSystem.addEmployee(new Employee("John Doe", "IT", "2022-01-01", "Junior"));
        payrollSystem.addEmployee(new Employee("Jane Smith", "HR", "2022-02-15", "Senior"));
        payrollSystem.addEmployee(new Employee("Bob Johnson", "Finance", "2022-03-10", "Mid"));
        payrollSystem.addEmployee(new Employee("Alice Williams", "Marketing", "2022-04-05", "Junior"));
        payrollSystem.addEmployee(new Employee("Charlie Brown", "IT", "2022-05-20", "Senior"));

        int choice;
        do {
            System.out.println("\nEmployee Payroll System Menu:");
            System.out.println("1. View Employees by Department");
            System.out.println("2. View Salary by Employee");
            System.out.println("3. Auto-Increment Salaries");
            System.out.println("4. Add New Employee");
            System.out.println("5. View All Employee Names");
            System.out.println("6. Delete Employee by ID");
            System.out.println("7. Generate Pay Stubs");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    payrollSystem.displayEmployeesByDepartment();
                    break;
                case 2:
                    payrollSystem.displaySalaryByEmployee();
                    break;
                case 3:
                    payrollSystem.autoIncrementSalary();
                    System.out.println("Salaries auto-incremented successfully.");
                    break;
                case 4:
                    addNewEmployee(scanner, payrollSystem);
                    break;
                case 5:
                    payrollSystem.displayEmployeeNames();
                    break;
                case 6:
                    deleteEmployee(scanner, payrollSystem);
                    break;
                case 7:
                    payrollSystem.generatePayStubs();
                    break;
                case 8:
                    System.out.println("Exiting the Employee Payroll System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }

        } while (choice != 8);

        scanner.close();
    }

    private static void addNewEmployee(Scanner scanner, PayrollSystem payrollSystem) {
        System.out.println("Enter details for the new employee:");

        System.out.print("Name: ");
        String name = scanner.next();

        System.out.print("Department: ");
        String department = scanner.next();

        System.out.print("Joining Date: ");
        String joiningDate = scanner.next();

        System.out.print("Level: ");
        String level = scanner.next();

        Employee newEmployee = new Employee(name, department, joiningDate, level);
        payrollSystem.addEmployee(newEmployee);
        System.out.println("New employee added successfully. Employee ID: " + newEmployee.getEmployeeId());
    }

    private static void deleteEmployee(Scanner scanner, PayrollSystem payrollSystem) {
        System.out.print("Enter the Employee ID to delete: ");
        int employeeIdToDelete = scanner.nextInt();

        boolean employeeDeleted = payrollSystem.deleteEmployee(employeeIdToDelete);

        if (employeeDeleted) {
            System.out.println("Employee with ID " + employeeIdToDelete + " deleted successfully.");
        } 
        else {
            System.out.println("Employee with ID " + employeeIdToDelete + " not found.");
        }
    }
}
