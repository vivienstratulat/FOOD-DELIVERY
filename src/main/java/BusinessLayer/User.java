package BusinessLayer;

public class User {
    private int idUser;
    private String username;
    private String password;
    private String address;
    private  String role;

    public User(int id,String username, String password, String address) {
        this.idUser=id;
        this.username = username;
        this.password = password;
        this.address = address;
        //this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
