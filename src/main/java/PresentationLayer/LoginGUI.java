package PresentationLayer;

import BusinessLayer.*;
import BusinessLayer.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.AnnotatedArrayType;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginGUI {
    JFrame frame;
    JLabel labelUser,labelPassword,labelSignUp,labelUser2,labelPassword2,labelAddress;
    JTextField tfUser,tfPassword,tfUser2,tfPassword2,tfAddress;
    JButton btnLogin,btnSignUp;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ArrayList<Client> clients=new ArrayList<Client>();
        ArrayList<Employee> angajati=new ArrayList<>();
        Employee e1=new Employee(1,"angajat1","123","cluj","employee");
        Employee e2=new Employee(2,"angajat2","000","turda","employee");
        Employee e3=new Employee(3,"angajat3","333","bucuresti","employee");
        angajati.add(e1);
        ArrayList<BusinessLayer.MenuItem> menuItems=new ArrayList<MenuItem>();

        HashMap<Order,ArrayList<MenuItem>> hashMap=new HashMap<Order,ArrayList<MenuItem>>();
        DeliveryService deliveryService=new DeliveryService(menuItems,clients,hashMap);
        LoginGUI login=new LoginGUI(deliveryService,angajati);
        //deliveryService.importProducts();




    }

    public void reset(){
        tfAddress.setText("");
        tfPassword.setText("");
        tfPassword2.setText("");
        tfUser.setText("");
        tfUser2.setText("");
    }




    public LoginGUI(DeliveryService deliveryService,ArrayList<Employee> angajati) throws IOException, ClassNotFoundException {
        frame=new JFrame();
        frame.setBounds(200,200,700,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // deliveryService.setClients(Serializator.clientDeserialization());
        //deliveryService.setMenuItems(Serializator.menuItemsDeserialization());
        // deliveryService.setHashMap(Serializator.orderDeserialization());

        for(Client c:deliveryService.getClients()){
            System.out.println(c.getUsername()+" "+c.getIdUser());
        }

        for(Order or:deliveryService.getHashMap().keySet()){
            System.out.println("Order: "+or.getIdOrder());
            for(MenuItem item:deliveryService.getHashMap().get(or)){
                System.out.println(" Item:"+item.getTitle());
            }
        }

        // Serializator.menuItemsSerialization(deliveryService.getMenuItems());
        //  Serializator.clientSerialization(deliveryService.getClients());

        labelUser=new JLabel("USERNAME:");
        labelUser.setBounds(30,50,130,20);
        frame.getContentPane().add(labelUser);

        tfUser=new JTextField();
        tfUser.setBounds(100,50,170,30);
        tfUser.setColumns(10);
       // tfUser.setBackground(Color);
        frame.getContentPane().add(tfUser);




        labelPassword=new JLabel("PASSWORD:");
        labelPassword.setBounds(280,50,130,20);
        frame.getContentPane().add(labelPassword);

        tfPassword=new JTextField();
        tfPassword.setBounds(360,50,170,30);
        tfPassword.setColumns(10);
        frame.getContentPane().add(tfPassword);

        btnLogin=new JButton("LOGIN");
        btnLogin.setBounds(540,40,90,40);
        btnLogin.setBackground(Color.LIGHT_GRAY);
        frame.getContentPane().add(btnLogin);

        labelSignUp=new JLabel("SIGN UP");
        labelSignUp.setBounds(280,140,130,20);
        labelSignUp.setFont(new Font("Times New Roman", Font.BOLD,20));
        frame.getContentPane().add(labelSignUp);

        labelUser2=new JLabel("USERNAME");
        labelUser2.setBounds(30,200,130,20);
        frame.getContentPane().add(labelUser2);

        tfUser2=new JTextField();
        tfUser2.setBounds(100,200,170,30);
        tfUser2.setColumns(10);
        frame.getContentPane().add(tfUser2);

        labelPassword2=new JLabel("PASSWORD");
        labelPassword2.setBounds(25,260,130,20);
        frame.getContentPane().add(labelPassword2);

        tfPassword2=new JTextField();
        tfPassword2.setBounds(100,260,170,30);
        tfPassword2.setColumns(10);
        frame.getContentPane().add(tfPassword2);

        labelAddress=new JLabel("ADDRESS");
        labelAddress.setBounds(25,320,130,20);
        frame.getContentPane().add(labelAddress);

        tfAddress=new JTextField();
        tfAddress.setBounds(100,320,170,30);
        tfAddress.setColumns(10);
        frame.getContentPane().add(tfAddress);

        btnSignUp=new JButton("REGISTER");
        btnSignUp.setBounds(110,380,90,40);
        btnSignUp.setBackground(Color.LIGHT_GRAY);
        frame.getContentPane().add(btnSignUp);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = tfUser.getText();
                String password = tfPassword.getText();
                boolean ok = false;
                if (user.equals("") || password.equals("")) {
                    JOptionPane.showMessageDialog(null,"DATE INCORECTE");
                } else {
                    if (user.equals("admin") && password.equals("admin")) {
                        AdminGUI admin = new AdminGUI(deliveryService);
                        ok = true;
                       // reset();
                    }else {
                        //System.out.println("clllllllllll");
                        for (Client c : deliveryService.getClients()) {
                            System.out.println(c.getUsername());
                            if (user.equals(c.getUsername()) && password.equals(c.getPassword())) {
                                ClientGUI client = new ClientGUI(deliveryService, c);
                                ok = true;
                               // reset();
                            }
                        }
                    }
                    if (ok == false) {

                        for (Employee em : angajati) {
                            if (user.equals(em.getUsername()) && password.equals(em.getPassword())) {
                                EmployeeGUI employee = new EmployeeGUI();
                               // reset();

                            }
                        }
                    }
                }
            }
        });
        btnLogin.setBackground(Color.PINK);
        btnSignUp.setBackground(Color.PINK);
        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // reset();
                String user2=tfUser2.getText();
                String pass2=tfPassword2.getText();
                String addr=tfAddress.getText();
               // if(user2.equals("")||pass2.equals("")||addr.equals("")){
                   // JOptionPane.showMessageDialog(null,"DATE INCORECTE");
              //  }
               // else{
                int id=deliveryService.getClients().size();
                Client c=new Client(id,user2,pass2,addr);
                deliveryService.getClients().add(c);
                for(Client cl:deliveryService.getClients()){
                    System.out.println("clientul "+cl.getIdUser()+" "+cl.getUsername());

                }
              /*  try{
                    Serializator.clientSerialization(deliveryService.getClients());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }*/
            }
        });















        frame.setVisible(true);
    }
}


