package BusinessLayer;

public class Employee extends User{


    public Employee(int id,String username, String password, String address, String role) {
        super(id,username, password, address);
        this.setRole("Employee");
    }
}
