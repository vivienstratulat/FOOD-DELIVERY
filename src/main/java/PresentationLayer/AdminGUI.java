package PresentationLayer;

import BusinessLayer.*;
import BusinessLayer.MenuItem;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class AdminGUI {
    JFrame frame;
    JTable table;
    JTextField tfCompName,tfNoOrdersProd,tfValue,tfNoOrders,tfDay,tfprice,tfname,tfrating,tfcalories,tfprotein,tffat,tfsodium,tfstart,tfend,tf;
    JButton btnImport,btnShow,btnDelete,btnAddTo,btnAddComposit,btnAddBase,btnEdit,btnDayReport,btnTimeReport,btnClientReport,btnProductReport;

    public AdminGUI(DeliveryService deliveryService){
        frame=new JFrame();
        frame.setBounds(100,100,989,631);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        ArrayList<BaseProduct> composite=new ArrayList<>();
        for(Order or: deliveryService.getHashMap().keySet()){
            System.out.println("Comanda ID: "+or.getIdOrder()+"data "+or.getDate());
            for(MenuItem item:deliveryService.getHashMap().get(or)){
                System.out.println("Produs "+item.getTitle());
            }
        }

        JScrollPane scrollPane=new JScrollPane();
        scrollPane.setBounds(10,11,550,410);
        frame.getContentPane().add(scrollPane);

        table=new JTable();
        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(new Object[][]{},new String[]{"Title","Rating","Calories","Protein","Fat","Sodium","Price"}));

        btnImport=new JButton("Import products");
        btnImport.setBackground(Color.PINK);
        btnImport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    deliveryService.importProducts();

                    for(MenuItem item:deliveryService.getMenuItems()){
                        System.out.println(item.getTitle()+"heheh");
                    }


                    // Serializator.menuItemsSerialization(deliveryService.getMenuItems());
                   // deliveryService.getMenuItems().removeAll(deliveryService.getMenuItems()); DE CE???????
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnImport.setBounds(10,515,150,50);
        frame.getContentPane().add(btnImport);

        btnShow=new JButton("SHOW PRODUCTS");
        btnShow.setBackground(Color.PINK);
        btnShow.setBounds(174,514,154,49);
        btnShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*try{
                    deliveryService.setMenuItems(Serializator.menuItemsDeserialization());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }*/
                for(MenuItem item:deliveryService.getMenuItems()){
                    System.out.println("Title: "+item.getTitle());
                }
                Object[] row=new Object[7];
                DefaultTableModel model=(DefaultTableModel) table.getModel();
                int noRow=model.getRowCount();
                for(int i=noRow-1;i>=1;i--){
                    model.removeRow(i);
                }
                try{
                    for(MenuItem item:deliveryService.getMenuItems()){
                        if(item instanceof BaseProduct){
                            row[0]=item.getTitle();
                            row[1]=((BaseProduct) item).getRating();
                            row[2]=((BaseProduct) item).getCalories();
                            row[3]=((BaseProduct) item).getProteins();
                            row[4]=((BaseProduct) item).getFats();
                            row[5]=((BaseProduct) item).getSodium();
                            row[6]=((BaseProduct) item).getPrice();
                            model.addRow(row);
                        }
                        else if (item instanceof CompositeProduct){
                            row[0]=item.getTitle();
                            row[1]=((CompositeProduct) item).getRating();
                            row[2]=((CompositeProduct) item).getCalories();
                            row[3]=((CompositeProduct) item).getProteins();
                            row[4]=((CompositeProduct) item).getFats();
                            row[5]=((CompositeProduct) item).getSodium();
                            row[6]=((CompositeProduct) item).getPrice();
                            model.addRow(row);
                        }
                    }
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });
        frame.getContentPane().add(btnShow);

        btnDelete=new JButton("DELETE");
        btnDelete.setBackground(Color.PINK);
        btnDelete.setBounds(338,514,154,49);
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object row1=table.getModel().getValueAt(table.getSelectedRow(),0);
                deliveryService.deleteItem(row1.toString());
                 /*try{
                     Serializator.menuItemsSerialization(deliveryService.getMenuItems());
                 } catch (IOException ex) {
                     ex.printStackTrace();
                 }*/
            }
        });
        frame.getContentPane().add(btnDelete);

        tfprice=new JTextField();
        tfprice.setText("Introduce price");
        tfprice.setBounds(573,11,184,31);
        frame.getContentPane().add(tfprice);

        tfname=new JTextField();
        tfname.setText("Introduce name");
        tfname.setBounds(573,57,184,31);
        frame.getContentPane().add(tfname);

        tfrating=new JTextField();
        tfrating.setText("Rating");
        tfrating.setBounds(573,109,184,31);
        frame.getContentPane().add(tfrating);

        tfcalories=new JTextField();
        tfcalories.setText("Calories");
        tfcalories.setColumns(10);
        tfcalories.setBounds(573,165,184,31);
        frame.getContentPane().add(tfcalories);

        tfprotein=new JTextField();
        tfprotein.setText("Proteins");
        tfprotein.setColumns(10);
        tfprotein.setBounds(573,219,184,31);
        frame.getContentPane().add(tfprotein);

        tffat=new JTextField();
        tffat.setText("Fats");
        tffat.setColumns(10);
        tffat.setBounds(573,272,184,31);
        frame.getContentPane().add(tffat);

        tfsodium=new JTextField();
        tfsodium.setText("Sodium");
        tfsodium.setColumns(10);
        tfsodium.setBounds(573,333,184,31);
        frame.getContentPane().add(tfsodium);

        tfCompName=new JTextField();
        tfCompName.setText("CompositeProduct Name ");
        tfCompName.setBounds(573,388,184,31);
        frame.getContentPane().add(tfCompName);

        btnAddTo=new JButton("ADD TO COMPOSITE");
        btnAddTo.setBackground(Color.PINK);
        btnAddTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object row1=table.getModel().getValueAt(table.getSelectedRow(),0);
                BaseProduct baseProduct=(BaseProduct) deliveryService.searchItem((String)row1);
                composite.add(baseProduct);
            }
        });

        btnAddTo.setBounds(10,440,200,49);
        frame.getContentPane().add(btnAddTo);

        btnAddComposit=new JButton("ADD COMPOSITE");
        btnAddComposit.setBackground(Color.PINK);
        btnAddComposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CompositeProduct comp=new CompositeProduct(tfCompName.getText(),composite,Float.parseFloat(tfrating.getText()));
                for(BaseProduct product: comp.getProducts()){
                    System.out.println(product.getTitle());
                }
                deliveryService.getMenuItems().add(comp);
              /*  try{
                    Serializator.menuItemsSerialization(deliveryService.getMenuItems());

                } catch (IOException ex) {
                    ex.printStackTrace();
                }*/
                composite.removeAll(composite);
            }
        });

        btnAddComposit.setBounds(248,440,200,49);
        frame.getContentPane().add(btnAddComposit);

        btnAddBase=new JButton("Add Base Product");
        btnAddBase.setBackground(Color.PINK);
        btnAddBase.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deliveryService.getMenuItems().add(new BaseProduct(tfname.getText(),Float.parseFloat(tfrating.getText()),Integer.parseInt(tfcalories.getText()),Integer.parseInt(tfprotein.getText()),Integer.parseInt(tffat.getText()),Integer.parseInt(tfsodium.getText()),Integer.parseInt(tfprice.getText())));
              /*  try{
                    Serializator.menuItemsSerialization(deliveryService.getMenuItems());

                } catch (IOException ex) {
                    ex.printStackTrace();
                }*/

            }
        }));
        btnAddBase.setBounds(486,440,200,49);
        frame.getContentPane().add(btnAddBase);

        btnEdit=new JButton("Edit product");
        btnEdit.setBackground(Color.PINK);
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object row1=table.getModel().getValueAt(table.getSelectedRow(),0);
                BaseProduct item=new BaseProduct(tfname.getText(),Float.parseFloat(tfrating.getText()),Integer.parseInt(tfprice.getText()),Integer.parseInt(tfcalories.getText()),Integer.parseInt(tfprotein.getText()),Integer.parseInt(tffat.getText()),Integer.parseInt(tfsodium.getText()));
                deliveryService.editProduct(row1.toString(),item);
                /*try{
                    Serializator.menuItemsSerialization(deliveryService.getMenuItems());

                } catch (IOException ex) {
                    ex.printStackTrace();
                }*/

            }

        });
        btnEdit.setBounds(502,514,184,49);
        frame.getContentPane().add(btnEdit);

        btnDayReport=new JButton("GENERATE DAY REPORT");
        btnDayReport.setBackground(Color.PINK);
        btnDayReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int day=Integer.parseInt(tfDay.getText());
                LocalDateTime date=LocalDateTime.of(2022,5,day,14,40);
                System.out.println("start");
                System.out.println(date);
                try{
                    deliveryService.generateDayReport(date);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnDayReport.setBounds(763,452,200,49);
        frame.getContentPane().add(btnDayReport);

        btnTimeReport=new JButton("GENERATE TIME REPORT");
        btnTimeReport.setBackground(Color.PINK);
        btnTimeReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int startHour=Integer.parseInt(tfstart.getText());
                int endHour=Integer.parseInt(tfend.getText());
                LocalTime startDate = LocalTime.of(startHour,0);
                LocalTime endDate=LocalTime.of(endHour,0);
                System.out.println(startDate);
                System.out.println(endDate);
                try{
                    deliveryService.generateTimeReport(startDate,endDate);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnTimeReport.setBounds(763,15,200,49);
        frame.getContentPane().add(btnTimeReport);

        btnClientReport=new JButton("GENERATE CLIENTS REPORT");
        btnClientReport.setBackground(Color.PINK);
        btnClientReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    deliveryService.generateClientsReport(Integer.parseInt(tfNoOrders.getText()),Integer.parseInt(tfValue.getText()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnClientReport.setBounds(763,189,200,49);
        frame.getContentPane().add(btnClientReport);

        btnProductReport=new JButton("GENERATE PRODUCTS REPORT");
        btnProductReport.setBackground(Color.PINK);
        btnProductReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deliveryService.generateProductsReport(Integer.parseInt(tfNoOrdersProd.getText()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnProductReport.setBounds(763,350,200,49);
        frame.getContentPane().add(btnProductReport);

        tfstart = new JTextField();
        tfstart.setText("Introduce Start Hour");
        tfstart.setColumns(10);
        tfstart.setBounds(767, 75, 184, 31);
        frame.getContentPane().add(tfstart);

        tfend = new JTextField();
        tfend.setText("Introduce End Hour");
        tfend.setColumns(10);
        tfend.setBounds(767, 129, 184, 31);
        frame.getContentPane().add(tfend);

        tfNoOrders = new JTextField();
        tfNoOrders.setText("Introduce Number of Orders");
        tfNoOrders.setColumns(10);
        tfNoOrders.setBounds(767, 249, 184, 31);
        frame.getContentPane().add(tfNoOrders);

        tfValue= new JTextField();
        tfValue.setText("Introduce Value");
        tfValue.setColumns(10);
        tfValue.setBounds(767, 308, 184, 31);
        frame.getContentPane().add(tfValue);

        tfNoOrdersProd = new JTextField();
        tfNoOrdersProd.setText("Introduce Number of Orders");
        tfNoOrdersProd.setColumns(10);
        tfNoOrdersProd.setBounds(763, 410, 184, 31);
        frame.getContentPane().add(tfNoOrdersProd);

        tfDay = new JTextField();
        tfDay.setText("Introduce Day ");
        tfDay.setColumns(10);
        tfDay.setBounds(763, 514, 184, 31);
        frame.getContentPane().add(tfDay);









        frame.setVisible(true);

    }
}
