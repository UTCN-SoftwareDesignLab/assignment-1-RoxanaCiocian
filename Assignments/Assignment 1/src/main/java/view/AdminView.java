package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminView extends JFrame{
    private JLabel explicatii;
    private JLabel explicatii2;
    private JLabel explicatii3;

    private JButton logOut;

    private JTextField tfUsername;
    private JTextField tfPassword;
    private JLabel usernameLabel;
    private JLabel passwordLabel;

    private JButton btnViewAll;
    private JButton btnCreate;
    private JButton btnUpdateUsername;
    private JButton btnUpdatePassword;
    private JButton btnDelete;

    private JTable employeesTable;
    private JScrollPane scrollPane;
    private DefaultTableModel table;


    public AdminView() throws HeadlessException {
        setSize(700, 520);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(null);
        add(tfUsername);
        add(tfPassword);
        add(usernameLabel);
        add(passwordLabel);
        add(btnViewAll);
        add(btnCreate);
        add(btnUpdateUsername);
        add(btnUpdatePassword);
        add(btnDelete);
        add(scrollPane);
        add(explicatii);
        add(explicatii2);
        add(explicatii3);
        add(logOut);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //setVisible(true);
    }

    private void initializeFields() {
        tfUsername = new JTextField();
        tfUsername.setBounds(400, 250, 200, 40);
        tfPassword = new JTextField();
        tfPassword.setBounds(400, 300, 200, 40);
        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(330, 230, 100,70);
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(330, 280, 100,70);
        btnViewAll = new JButton("View all");
        btnViewAll.setBounds(20, 90, 100, 50);
        btnCreate = new JButton("Create");
        btnCreate.setBounds(20, 20, 100, 50);
        btnDelete = new JButton("Delete");
        btnDelete.setBounds(20, 160, 100, 50);
        btnUpdateUsername = new JButton("Update username");
        btnUpdateUsername.setBounds(20, 230, 150, 50);
        btnUpdatePassword = new JButton("Update password");
        btnUpdatePassword.setBounds(20, 300, 150, 50);
        scrollPane = new JScrollPane();
        scrollPane.setBounds(140, 20, 500, 200);
        table  = new DefaultTableModel();
        table.addColumn("id");
        table.addColumn("username");
        table.addColumn("password");
        table.addColumn("role");

        employeesTable = new JTable();
        employeesTable.setModel(table);
        scrollPane.setViewportView(employeesTable);

        explicatii = new JLabel("For creating an employee, fill the text fields and click Create client.");
        explicatii.setBounds(10, 350, 700, 50);
        explicatii2 = new JLabel("For deleting an employee, click on View all, select the one you want to delete and press Delete.");
        explicatii2.setBounds(10, 370, 700, 50);
        explicatii3 = new JLabel("For updating an employee, select one from table and then fill the text you want to update.Then press update.");
        explicatii3.setBounds(10, 390, 700, 50);
        logOut = new JButton("Log Out");
        logOut.setBounds(550, 425, 100, 50);
    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public void setUsernameText(String s) {
        tfUsername.setText(s);
    }

    public String getPassword() {
        return tfPassword.getText();
    }

    public void setPasswordText(String s) {
        tfPassword.setText(s);
    }

    public void setViewAllButtonListener(ActionListener viewAllButtonListener) {
        btnViewAll.addActionListener(viewAllButtonListener);
    }

    public void setCreateButtonListener(ActionListener createButtonListener) {
        btnCreate.addActionListener(createButtonListener);

    }

    public void setUpdateUsernameButtonListener(ActionListener updateUsernameButtonListener){
        btnUpdateUsername.addActionListener(updateUsernameButtonListener);
    }

    public void setUpdatePasswordButtonListener(ActionListener updatePasswordButtonListener){
        btnUpdatePassword.addActionListener(updatePasswordButtonListener);
    }

    public void setDeleteButtonListener(ActionListener deleteButtonListener){
        btnDelete.addActionListener(deleteButtonListener);
    }

    public void setLogOutButtonListener(ActionListener logOutButtonListener) {
        logOut.addActionListener(logOutButtonListener);
    }

    public DefaultTableModel getModel() {
        DefaultTableModel m = this.table;
        return m;
    }

    public JTable getTableUsers() {
        JTable tableUsers = this.employeesTable;
        return tableUsers;
    }
    public void setVisible() {
        this.setVisible(true);
    }
}

