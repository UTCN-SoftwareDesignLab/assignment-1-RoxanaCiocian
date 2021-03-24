package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class EmployeeView extends JFrame {
    private JLabel explicatii;
    private JLabel explicatii2;
    private JLabel explicatii3;
    private JLabel explicatii4;
    private JLabel explicatii5;
    private JButton logOut;

    //client related info
    private JTextField tfName;
    private JTextField tfCardNR;
    private JTextField tfPcn;
    private JTextField tfAddress;

    private JLabel nameLabel;
    private JLabel cardLabel;
    private JLabel pcnLabel;
    private JLabel addrLabel;

    private JButton btnViewAll;
    private JButton btnCreate;
    private JButton btnUpdate;

    //account related info
    private JTextField tfIdentNr;
    private JTextField tfType;
    private JTextField tfBalance;

    private JLabel identLabel;
    private JLabel typeLabel;
    private JLabel balanceLabel;

    private JButton btnCreateAccount;
    private JButton btnViewAccounts;
    private JButton btnUpdateBalance;
    private JButton btnDeleteAccount;

    private JTable clientsTable;
    private JScrollPane scrollPane;
    private DefaultTableModel table;

    public EmployeeView() throws HeadlessException {
        setSize(750, 750);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(null);
        add(tfName);
        add(tfCardNR);
        add(tfPcn);
        add(tfAddress);
        add(nameLabel);
        add(cardLabel);
        add(pcnLabel);
        add(addrLabel);
        add(btnViewAll);
        add(btnCreate);
        add(tfIdentNr);
        add(tfType);
        add(tfBalance);
        add(identLabel);
        add(typeLabel);
        add(balanceLabel);
        add(btnCreateAccount);
        add(btnViewAccounts);
        add(btnUpdateBalance);
        add(btnDeleteAccount);
        add(btnUpdate);
        add(scrollPane);
        add(explicatii);
        add(explicatii2);
        add(explicatii3);
        add(explicatii4);
        add(explicatii5);
        add(logOut);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        //client related info
        tfName = new JTextField();
        tfName.setBounds(110, 300, 200, 40);
        tfCardNR = new JTextField();
        tfCardNR.setBounds(110, 350, 200, 40);
        tfPcn = new JTextField();
        tfPcn.setBounds(110, 400, 200, 40);
        tfAddress = new JTextField();
        tfAddress.setBounds(110, 450, 200, 40);

        nameLabel = new JLabel("Client's name:");
        nameLabel.setBounds(10,280, 100,70);
        cardLabel = new JLabel("Card number:");
        cardLabel.setBounds(10,330,100,70);
        pcnLabel = new JLabel("PNC:");
        pcnLabel.setBounds(60,380,100,70);
        addrLabel = new JLabel("Address:");
        addrLabel.setBounds(40,430,100,70);

        btnViewAll = new JButton("View clients");
        btnViewAll.setBounds(20, 90, 150, 50);
        btnCreate = new JButton("Create client");
        btnCreate.setBounds(20, 20, 150, 50);
        btnUpdate = new JButton("Update client");
        btnUpdate.setBounds(20, 160, 150, 50);


        //account related info
        tfIdentNr = new JTextField();
        tfIdentNr.setBounds(500, 300, 200, 40);
        tfType = new JTextField();
        tfType.setBounds(500,350,200,40);
        tfBalance = new JTextField();
        tfBalance.setBounds(500,400,200,40);

        identLabel = new JLabel("Identification nr:");
        identLabel.setBounds(400,280,100,70);
        typeLabel = new JLabel("Account type:");
        typeLabel.setBounds(410, 330,100,70);
        balanceLabel = new JLabel("Amount of money:");
        balanceLabel.setBounds(390, 380,100,70);

        btnCreateAccount = new JButton("Create account");
        btnCreateAccount.setBounds(20, 520, 150, 50);
        btnViewAccounts = new JButton("View accounts");
        btnViewAccounts.setBounds(190, 520, 150, 50);
        btnUpdateBalance = new JButton("Update money");
        btnUpdateBalance.setBounds(360,520,150,50);
        btnDeleteAccount = new JButton("Delete account");
        btnDeleteAccount.setBounds(530,520,150,50);

        explicatii = new JLabel("For creating a client, fill the text fields from the left and click Create client.");
        explicatii.setBounds(10, 570, 700, 50);
        explicatii2 = new JLabel("For creating an account, click View clients, select a client and fill the text fields from the right.");
        explicatii2.setBounds(10, 590, 700, 50);
        explicatii3 = new JLabel("For updating a client, select one and write a new name.");
        explicatii3.setBounds(10, 610, 700, 50);
        explicatii4 = new JLabel("For updating an account, select one and write the amount of money for updating. For delete an account, just choose one from the table and");
        explicatii4.setBounds(10, 630, 700, 50);
        explicatii5 = new JLabel(" For deleting an account, just choose one from the table and press Delete account.");
        explicatii5.setBounds(10, 650, 700, 50);
        logOut = new JButton("Log Out");
        logOut.setBounds(620, 665, 100, 50);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(180, 20, 500, 200);
        table  = new DefaultTableModel();
        clientsTable = new JTable();
        clientsTable.setModel(table);
        scrollPane.setViewportView(clientsTable);
        //setVisible(true);

    }

    public String getTfIdentNr() {
        return tfIdentNr.getText();
    }

    public String getTfType() {
        return tfType.getText();
    }

    public String getTfBalance() {
        return tfBalance.getText();
    }

    public String getNameField() {
        return tfName.getText();
    }

    public String getCardNr() {
        return tfCardNR.getText();
    }

    public String getPcn() {
        return tfPcn.getText();
    }

    public String getAddress() {
        return tfAddress.getText();
    }

    public void setTfIdentNr(String s) { tfIdentNr.setText(s);}

    public void setTfType(String s) {tfType.setText(s);}

    public  void  setTfBalance(String s){
        tfBalance.setText("");
    }

    public void setNameText(String s) {
        tfName.setText(s);}

    public void setCardNRText(String s) {
        tfCardNR.setText(s);}

    public void setPncText(String s) {
        tfPcn.setText(s);}

    public void setAddressText(String s) {
        tfAddress.setText(s);}

    public void setViewAllButtonListener(ActionListener viewAllButtonListener) {
        btnViewAll.addActionListener(viewAllButtonListener);
    }
    public void setCreateButtonListener(ActionListener createButtonListener) {
        btnCreate.addActionListener(createButtonListener);
    }

    public void setUpdateNameButtonListener(ActionListener updateNameButtonListener) {
        btnUpdate.addActionListener(updateNameButtonListener);
    }

    public void setCreateAccountButtonListener(ActionListener createAccountButtonListener) {
        btnCreateAccount.addActionListener(createAccountButtonListener);
    }

    public void setBtnViewAccountsButtonListener(ActionListener viewAccountsButtonListener) {
        btnViewAccounts.addActionListener(viewAccountsButtonListener);
    }

    public void setUpdateAccountButtonListener(ActionListener updateAccountButtonListener) {
        btnUpdateBalance.addActionListener(updateAccountButtonListener);
    }

    public void setDeleteAccountButtonListener(ActionListener deleteAccountButtonListener) {
        btnDeleteAccount.addActionListener(deleteAccountButtonListener);
    }

    public void setLogOutButtonListener(ActionListener logOutButtonListener) {
        logOut.addActionListener(logOutButtonListener);
    }

    public DefaultTableModel getModel() {
        DefaultTableModel m = this.table;
        return m;
    }

    public JTable getTableClients() {
        JTable tableUsers = this.clientsTable;
        return tableUsers;
    }



    public void setVisible() {
        this.setVisible(true);
    }
}
