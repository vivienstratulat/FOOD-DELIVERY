package PresentationLayer;

import BusinessLayer.*;
import BusinessLayer.MenuItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClientGUI implements Observer {
    private JFrame frame;
    private JTable table;
    private JTextField tfname,tfrating,tfcalories,tfprotein,tffats,tftfsodium,tfprice;
    JButton btnCriteria,btnSearchPrice,btnSearchProtein,btnSearchFats,btnSearchCalories,btnAllProducts,btnAddToOrder,btnOrder,btnSearchName,btnSearchRating;

    //public ClientGUI(DeliveryService d, Client c){
  //      System.out.println("clienttttttttttt");
    //}

    @Override
    public void notificare() {
        EmployeeGUI eg=new EmployeeGUI();
    }

    public ClientGUI(DeliveryService deliveryService,Client c){
        frame=new JFrame();
        frame.setBounds(100, 100, 907, 573);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        ArrayList<MenuItem> menuItems=new ArrayList<>();
        ArrayList<Order> orders=new ArrayList<>();

        JScrollPane scrollPane=new JScrollPane();
        scrollPane.setBounds(10,11,548,411);
        frame.getContentPane().add(scrollPane);

        table=new JTable();
        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(new Object[][]{},new String []{"Title","Rating","Calories","Protein","Fat","Sodium","Price"}));

        btnAllProducts=new JButton("SHOW ALL PRODUCTS");
        btnAllProducts.setBackground(Color.PINK);
        btnAllProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(MenuItem item:deliveryService.getMenuItems()){
                    System.out.println("Title: "+item.getTitle());
                }
                Object[] row=new Object[7];
                DefaultTableModel model= (DefaultTableModel) table.getModel();
                int nrRow=model.getRowCount();
                for(int i=nrRow-1;i>=1;i--){
                    model.removeRow(i);
                }
                for(MenuItem item:deliveryService.getMenuItems()){
                    if(item instanceof BaseProduct){
                        row[0]= item.getTitle();
                        row[1]= ((BaseProduct) item).getRating();
                        row[2]= ((BaseProduct) item).getCalories();
                        row[3]= ((BaseProduct) item).getProteins();
                        row[4]= ((BaseProduct) item).getFats();
                        row[5]= ((BaseProduct) item).getSodium();
                        row[6]= ((BaseProduct) item).getPrice();
                        model.addRow(row);
                    }else if(item instanceof CompositeProduct){
                        row[0]= item.getTitle();
                        row[1]= ((CompositeProduct) item).getRating();
                        row[2]= ((CompositeProduct) item).getCalories();
                        row[3]= ((CompositeProduct) item).getProteins();
                        row[4]= ((CompositeProduct) item).getFats();
                        row[5]= ((CompositeProduct) item).getSodium();
                        row[6]= ((CompositeProduct) item).getPrice();
                        model.addRow(row);

                    }
                }
            }
        });
        btnAllProducts.setBounds(10,490,175,30);
        frame.getContentPane().add(btnAllProducts);

        btnAddToOrder=new JButton("ADD TO ORDER");
        btnAddToOrder.setBackground(Color.PINK);
        btnAddToOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object row1=new Object();
                row1=table.getModel().getValueAt(table.getSelectedRow(),0);
                MenuItem menuItem=deliveryService.searchItem((String) row1);
                System.out.println(menuItem.getTitle()+"\n");
                menuItems.add(menuItem);

            }
        });
        btnAddToOrder.setBounds(190,490,175,30);
        frame.add(btnAddToOrder);

        btnOrder=new JButton("ORDER");
        btnOrder.setBackground(Color.PINK);
        btnOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               /* for(Order o:deliveryService.getHashMap().keySet()){
                    System.out.println("nu exista comenzi???");
                    System.out.println("ID order: "+o.getIdOrder()+ "Cu produsele:");
                    for(MenuItem item:deliveryService.getHashMap().get(o)){
                        System.out.println(item.getTitle());

                    }
                }*/
                System.out.println("Size ul hashmap ului "+deliveryService.getHashMap().size());
                Order order=new Order(c.getIdUser(),deliveryService.getHashMap().size(), LocalDateTime.now());
               // deliveryService.getHashMap().put(order,menuItems);
                deliveryService.addOrder(order,menuItems);
                try {
                    deliveryService.generateBill(order.getIdOrder());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                notificare();
            }
        });
        btnOrder.setBounds(380,490,180,32);
        frame.getContentPane().add(btnOrder);

        btnSearchName=new JButton("Search by name");
        btnSearchName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<MenuItem> menuItemList=deliveryService.searchProductName(tfname.getText());
                Object[] row=new Object[7];
                DefaultTableModel model= (DefaultTableModel) table.getModel();
                int rowCount=model.getRowCount();
                for(int i=rowCount-1;i>=0;i--){
                    model.removeRow(i);
                }
                for(MenuItem item:menuItemList){
                    if(item instanceof BaseProduct){
                        row[0]= item.getTitle();
                        row[1]= ((BaseProduct) item).getRating();
                        row[2]= ((BaseProduct) item).getCalories();
                        row[3]= ((BaseProduct) item).getProteins();
                        row[4]= ((BaseProduct) item).getFats();
                        row[5]= ((BaseProduct) item).getSodium();
                        row[6]= ((BaseProduct) item).getPrice();
                        model.addRow(row);
                    }else if(item instanceof CompositeProduct){
                        row[0]= item.getTitle();
                        row[1]= ((CompositeProduct) item).getRating();
                        row[2]= ((CompositeProduct) item).getCalories();
                        row[3]= ((CompositeProduct) item).getProteins();
                        row[4]= ((CompositeProduct) item).getFats();
                        row[5]= ((CompositeProduct) item).getSodium();
                        row[6]= ((CompositeProduct) item).getPrice();
                        model.addRow(row);
                    }
                }

                }

        });
        btnSearchName.setBounds(620,155,200,30);
        btnSearchName.setBackground(Color.PINK);
        frame.getContentPane().add(btnSearchName);

        tfname = new JTextField();
       tfname.setBounds(619, 114, 201, 30);
        frame.getContentPane().add(tfname);
        tfname.setColumns(10);

        btnSearchRating=new JButton("SEARCH BY RATING");
        btnSearchRating.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<MenuItem> menuItemList=deliveryService.searchProductRating(Float.parseFloat(tfrating.getText()));
                Object[] row=new Object[7];
                DefaultTableModel model= (DefaultTableModel) table.getModel();
                int rowCount=model.getRowCount();
                for(int i=rowCount-1;i>=0;i--){
                    model.removeRow(i);
                }
                for(MenuItem item:menuItemList){
                    if(item instanceof BaseProduct){
                        row[0]= item.getTitle();
                        row[1]= ((BaseProduct) item).getRating();
                        row[2]= ((BaseProduct) item).getCalories();
                        row[3]= ((BaseProduct) item).getProteins();
                        row[4]= ((BaseProduct) item).getFats();
                        row[5]= ((BaseProduct) item).getSodium();
                        row[6]= ((BaseProduct) item).getPrice();
                        model.addRow(row);
                    }else if(item instanceof CompositeProduct){
                        row[0]= item.getTitle();
                        row[1]= ((CompositeProduct) item).getRating();
                        row[2]= ((CompositeProduct) item).getCalories();
                        row[3]= ((CompositeProduct) item).getProteins();
                        row[4]= ((CompositeProduct) item).getFats();
                        row[5]= ((CompositeProduct) item).getSodium();
                        row[6]= ((CompositeProduct) item).getPrice();
                        model.addRow(row);
                    }
                }

            }

        });
        btnSearchRating.setBounds(620,240,200,30);
        btnSearchRating.setBackground(Color.PINK);
        frame.getContentPane().add(btnSearchRating);

        tfrating = new JTextField();
        tfrating.setColumns(10);
        tfrating.setBounds(620, 198, 201, 30);
        frame.getContentPane().add(tfrating);

        btnSearchCalories=new JButton("SEARCH BY CALORIES");
        btnSearchCalories.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<MenuItem> menuItemList=deliveryService.searchProductCalories(Integer.parseInt(tfcalories.getText()));
                Object[] row=new Object[7];
                DefaultTableModel model= (DefaultTableModel) table.getModel();
                int rowCount=model.getRowCount();
                for(int i=rowCount-1;i>=0;i--){
                    model.removeRow(i);
                }
                for(MenuItem item:menuItemList){
                    if(item instanceof BaseProduct){
                        row[0]= item.getTitle();
                        row[1]= ((BaseProduct) item).getRating();
                        row[2]= ((BaseProduct) item).getCalories();
                        row[3]= ((BaseProduct) item).getProteins();
                        row[4]= ((BaseProduct) item).getFats();
                        row[5]= ((BaseProduct) item).getSodium();
                        row[6]= ((BaseProduct) item).getPrice();
                        model.addRow(row);
                    }else if(item instanceof CompositeProduct){
                        row[0]= item.getTitle();
                        row[1]= ((CompositeProduct) item).getRating();
                        row[2]= ((CompositeProduct) item).getCalories();
                        row[3]= ((CompositeProduct) item).getProteins();
                        row[4]= ((CompositeProduct) item).getFats();
                        row[5]= ((CompositeProduct) item).getSodium();
                        row[6]= ((CompositeProduct) item).getPrice();
                        model.addRow(row);
                    }
                }

            }
        });



        btnSearchCalories.setBounds(620,320,200,30);
        btnSearchCalories.setBackground(Color.PINK);
        frame.getContentPane().add(btnSearchCalories);

        tfcalories = new JTextField();
        tfcalories.setColumns(10);
        tfcalories.setBounds(619, 282, 201, 30);
        frame.getContentPane().add(tfcalories);

        btnSearchFats=new JButton("SEARCH BY FATS");
        btnSearchFats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<MenuItem> menuItemList=deliveryService.searchProductFats(Integer.parseInt(tffats.getText()));
                Object[] row=new Object[7];
                DefaultTableModel model= (DefaultTableModel) table.getModel();
                int rowCount=model.getRowCount();
                for(int i=rowCount-1;i>=0;i--){
                    model.removeRow(i);
                }
                for(MenuItem item:menuItemList){
                    if(item instanceof BaseProduct){
                        row[0]= item.getTitle();
                        row[1]= ((BaseProduct) item).getRating();
                        row[2]= ((BaseProduct) item).getCalories();
                        row[3]= ((BaseProduct) item).getProteins();
                        row[4]= ((BaseProduct) item).getFats();
                        row[5]= ((BaseProduct) item).getSodium();
                        row[6]= ((BaseProduct) item).getPrice();
                        model.addRow(row);
                    }else if(item instanceof CompositeProduct){
                        row[0]= item.getTitle();
                        row[1]= ((CompositeProduct) item).getRating();
                        row[2]= ((CompositeProduct) item).getCalories();
                        row[3]= ((CompositeProduct) item).getProteins();
                        row[4]= ((CompositeProduct) item).getFats();
                        row[5]= ((CompositeProduct) item).getSodium();
                        row[6]= ((CompositeProduct) item).getPrice();
                        model.addRow(row);
                    }
            }}
        });

        btnSearchFats.setBounds(620,400,200,30);
        btnSearchFats.setBackground(Color.PINK);
        frame.getContentPane().add(btnSearchFats);

        tffats = new JTextField();
        tffats.setColumns(10);
        tffats.setBounds(619, 366, 201, 30);
        frame.getContentPane().add(tffats);

        btnSearchProtein=new JButton("SEARCH BY PROTEIN");
        btnSearchProtein.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<MenuItem> menuItemList=deliveryService.searchProductProteins(Integer.parseInt(tfprotein.getText()));
                Object[] row=new Object[7];
                DefaultTableModel model= (DefaultTableModel) table.getModel();
                int rowCount=model.getRowCount();
                for(int i=rowCount-1;i>=0;i--){
                    model.removeRow(i);
                }
                for(MenuItem item:menuItemList){
                    if(item instanceof BaseProduct){
                        row[0]= item.getTitle();
                        row[1]= ((BaseProduct) item).getRating();
                        row[2]= ((BaseProduct) item).getCalories();
                        row[3]= ((BaseProduct) item).getProteins();
                        row[4]= ((BaseProduct) item).getFats();
                        row[5]= ((BaseProduct) item).getSodium();
                        row[6]= ((BaseProduct) item).getPrice();
                        model.addRow(row);
                    }else if(item instanceof CompositeProduct){
                        row[0]= item.getTitle();
                        row[1]= ((CompositeProduct) item).getRating();
                        row[2]= ((CompositeProduct) item).getCalories();
                        row[3]= ((CompositeProduct) item).getProteins();
                        row[4]= ((CompositeProduct) item).getFats();
                        row[5]= ((CompositeProduct) item).getSodium();
                        row[6]= ((CompositeProduct) item).getPrice();
                        model.addRow(row);
                    }
                }}

        });

        btnSearchProtein.setBounds(620,490,200,30);
        btnSearchProtein.setBackground(Color.PINK);
        frame.getContentPane().add(btnSearchProtein);

        tfprotein= new JTextField();
        tfprotein.setColumns(10);
        tfprotein.setBounds(619, 450, 201, 30);
        frame.getContentPane().add(tfprotein);

        btnSearchPrice=new JButton("SEARCH BY PRICE");
        btnSearchPrice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<MenuItem> menuItemList=deliveryService.searchProductProteins(Integer.parseInt(tfprice.getText()));
                Object[] row=new Object[7];
                DefaultTableModel model= (DefaultTableModel) table.getModel();
                int rowCount=model.getRowCount();
                for(int i=rowCount-1;i>=0;i--){
                    model.removeRow(i);
                }
                for(MenuItem item:menuItemList){
                    if(item instanceof BaseProduct){
                        row[0]= item.getTitle();
                        row[1]= ((BaseProduct) item).getRating();
                        row[2]= ((BaseProduct) item).getCalories();
                        row[3]= ((BaseProduct) item).getProteins();
                        row[4]= ((BaseProduct) item).getFats();
                        row[5]= ((BaseProduct) item).getSodium();
                        row[6]= ((BaseProduct) item).getPrice();
                        model.addRow(row);
                    }else if(item instanceof CompositeProduct){
                        row[0]= item.getTitle();
                        row[1]= ((CompositeProduct) item).getRating();
                        row[2]= ((CompositeProduct) item).getCalories();
                        row[3]= ((CompositeProduct) item).getProteins();
                        row[4]= ((CompositeProduct) item).getFats();
                        row[5]= ((CompositeProduct) item).getSodium();
                        row[6]= ((CompositeProduct) item).getPrice();
                        model.addRow(row);
                    }
                }}
        });

        btnSearchPrice.setBounds(620,70,200,30);
        btnSearchPrice.setBackground(Color.PINK);
        frame.getContentPane().add(btnSearchPrice);

        tfprice = new JTextField();
        tfprice.setColumns(10);
        tfprice.setBounds(619, 30, 201, 30);
        frame.getContentPane().add(tfprice);

        btnCriteria=new JButton("Search by criteria");
        btnCriteria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<MenuItem> menuItemsList = deliveryService.searchCriteria( Integer.parseInt(tfprice.getText()),
                        Float.parseFloat(tfrating.getText()), Integer.parseInt(tfprotein.getText()), Integer.parseInt(tftfsodium.getText()), Integer.parseInt(tffats.getText()), Integer.parseInt(tfcalories.getText()));
                Object[] row = new Object[7];
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int rowCount = model.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    model.removeRow(i);
                }
                try {
                    for (MenuItem item : menuItemsList) {
                        if (item instanceof BaseProduct) {
                            row[0] = item.getTitle();
                            row[1] = ((BaseProduct) item).getRating();
                            row[2] = ((BaseProduct) item).getCalories();
                            row[3] = ((BaseProduct) item).getProteins();
                            row[4] = ((BaseProduct) item).getFats();
                            row[5] = ((BaseProduct) item).getSodium();
                            row[6] = ((BaseProduct) item).getPrice();
                            model.addRow(row);
                        } else if (item instanceof CompositeProduct) {
                            row[0] = item.getTitle();
                            row[1] = ((CompositeProduct) item).getRating();
                            row[2] = ((CompositeProduct) item).getCalories();
                            row[3] = ((CompositeProduct) item).getProteins();
                            row[4] = ((CompositeProduct) item).getFats();
                            row[5] = ((CompositeProduct) item).getSodium();
                            row[6] = ((CompositeProduct) item).getPrice();
                            model.addRow(row);
                        }
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
            }}
        });

        btnCriteria.setBounds(170,430,220,47);
        btnCriteria.setBackground(Color.pink);
        frame.getContentPane().add(btnCriteria);












        frame.setVisible(true);
    }
}
