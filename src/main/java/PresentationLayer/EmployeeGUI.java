package PresentationLayer;

import BusinessLayer.Employee;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

public class EmployeeGUI extends Observable {
    JFrame frame;
    JLabel order;




    public EmployeeGUI(){
       // System.out.println("employeee");
        frame=new JFrame();
        frame.setBounds(100,100,700,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        order=new JLabel("NEW ORDER");
        order.setFont(new Font("Tahoma", Font.PLAIN,44));
        order.setBounds(196,51,287,197);
        frame.getContentPane().add(order);
        frame.setVisible(true);

    }
}
