package controller;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import service.account.ServiceAccount;
import service.employee.ServiceEmployee;
import view.EmployeeView;
import view.LoginView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;

public class EmployeeController {
    private final EmployeeView employeeView;
    private final ServiceEmployee serviceEmployee;
    private final ServiceAccount accountService;
    private final LoginView loginController;

    public EmployeeController(EmployeeView employeeView, ServiceEmployee serviceEmployee, ServiceAccount accountService, LoginView loginController){
        this.employeeView = employeeView;
        this.serviceEmployee = serviceEmployee;
        this.accountService = accountService;
        this.loginController = loginController;
        employeeView.setCreateButtonListener(new CreateButtonListener());
        employeeView.setViewAllButtonListener(new ViewAllButtonListener());
        employeeView.setUpdateNameButtonListener(new UpdateNameButtonListener());
        employeeView.setCreateAccountButtonListener(new CreateAccountButtonListener());
        employeeView.setBtnViewAccountsButtonListener(new ViewAccountsButtonListener());
        employeeView.setUpdateAccountButtonListener(new UpdateBalanceButtonListener());
        employeeView.setDeleteAccountButtonListener(new DeleteAccountButtonListener());
        employeeView.setLogOutButtonListener(new LogOutButtonListener());
    }

    public void setVisible(boolean b) {
        employeeView.setVisible(b);

    }

    private class LogOutButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            loginController.setVisible(true);
            employeeView.setVisible(false);
        }
    }

    private class CreateButtonListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e)  {
            String name = employeeView.getNameField();
            String cardNr = employeeView.getCardNr();
            String pcn = employeeView.getPcn();
            String address = employeeView.getAddress();

            Notification<Boolean> registerNotification = serviceEmployee.createClient(name, cardNr, pcn, address);

            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client successfully created!");
                }
            }

            employeeView.setNameText("");
            employeeView.setCardNRText("");
            employeeView.setPncText("");
            employeeView.setAddressText("");
        }
    }

    private class CreateAccountButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String identNr = employeeView.getTfIdentNr();
            String type = employeeView.getTfType();
            float balance = Float.parseFloat(employeeView.getTfBalance());
            Long id = getClientFromTable().getId();
            if(id != null) {

                Notification<Boolean> registerNotification = accountService.createAccount(identNr, type, balance, Date.valueOf(LocalDate.now()), id);

                if (registerNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), registerNotification.getFormattedErrors());
                } else {
                    if (!registerNotification.getResult()) {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Not successful, please try again later.");
                    } else {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account successfully created!");
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Select client from table");
            }

            employeeView.setTfIdentNr("");
            employeeView.setTfType("");
            employeeView.setTfBalance("");
        }
    }

    private class ViewAllButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
           updateClientsTable();
        }
    }

    private class ViewAccountsButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            updateAccountsTable();
        }
    }

    private class UpdateNameButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(checkIfRowSelectedClients() && (!employeeView.getNameField().equals(""))){
            String name = employeeView.getNameField();
            Long idClient = getClientFromTable().getId();
            try {
                Long id = serviceEmployee.findById(idClient).getId();
                Notification<Boolean> registerNotification = serviceEmployee.updateClientName1(name, id);

                if (registerNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), registerNotification.getFormattedErrors());
                } else {
                    if (!registerNotification.getResult()) {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Error! The client can not be updated!");
                    } else {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Successfully updated the client's name!");
                        updateClientsTable();
                    }
                }

            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }}
            else{
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Error! No row selected/ no new name!");
            }


            employeeView.setNameText("");
        }
    }

    private class UpdateBalanceButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(checkIfRowSelectedAccount() && (!employeeView.getTfBalance().equals(""))){
                float balance = Float.parseFloat(employeeView.getTfBalance());
                Long idCl = getAccountFromTable().getId();

                if (accountService.updateClientBalance(balance,idCl)) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Successfully updated the account's balance!");
                    updateAccountsTable();
                } else {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Error! The account can not be updated!");

                }
            } else{
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Error! No row selected/ no introduced amount!");
            }

            employeeView.setTfBalance("");
        }
    }

    private class DeleteAccountButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(checkIfRowSelectedAccount()) {
                if (accountService.removeAccount(getAccountFromTable().getId())) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Successfully deleted an account!");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Error! The account can not be deleted!");
                }
            }else{
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Error! No row selected!");
            }
        }
    }

    public void updateClientsTable(){
        DefaultTableModel table = new DefaultTableModel();
        table.addColumn("id");
        table.addColumn("name");
        table.addColumn("card_number");
        table.addColumn("pnc");
        table.addColumn("address");
        for (Client c :  serviceEmployee.viewClients()) {
            Object[] o = { c.getId(), c.getName(), c.getCard_number(), c.getPnc(), c.getAddress()};
            table.addRow(o);
        }

        employeeView.getTableClients().setModel(table);
    }

    public void updateAccountsTable(){
        DefaultTableModel table = new DefaultTableModel();
        table.addColumn("id");
        table.addColumn("ident_number");
        table.addColumn("type");
        table.addColumn("balance");
        table.addColumn("creation_date");
        table.addColumn("id_client");
        for (Account a :  accountService.viewAccounts()) {
            Object[] o = { a.getId(), a.getIdent_number(), a.getType(), a.getBalance(), a.getCreation_date(), a.getId_client()};
            table.addRow(o);
        }

        employeeView.getTableClients().setModel(table);
    }

    public boolean checkIfRowSelectedClients(){
        if(employeeView.getTableClients().getSelectionModel().isSelectionEmpty()){

            return false;
        }else {
            return true;
        }
    }

    public boolean checkIfRowSelectedAccount(){
        if(employeeView.getTableClients().getSelectionModel().isSelectionEmpty()){

            return false;
        }else {
            return true;
        }
    }

    public Client getClientFromTable() {

        Long clientId = (Long) employeeView.getTableClients().getValueAt(employeeView.getTableClients().getSelectedRow(),0);
        String name = (String) employeeView.getTableClients().getValueAt(employeeView.getTableClients().getSelectedRow(),1);
        String cardNr = (String) employeeView.getTableClients().getValueAt(employeeView.getTableClients().getSelectedRow(),2);
        String pnc = (String) employeeView.getTableClients().getValueAt(employeeView.getTableClients().getSelectedRow(),3);
        String address = (String) employeeView.getTableClients().getValueAt(employeeView.getTableClients().getSelectedRow(),4);


        Client client = new ClientBuilder()
                .setId(clientId)
                .setName(name)
                .setCardNumber(cardNr)
                .setPnc(pnc)
                .setAddress(address)
                .build();

        return client;
    }

    public Account getAccountFromTable(){
        Long id = (Long) employeeView.getTableClients().getValueAt(employeeView.getTableClients().getSelectedRow(),0);
        String identNr = (String) employeeView.getTableClients().getValueAt(employeeView.getTableClients().getSelectedRow(),1);
        String type = (String) employeeView.getTableClients().getValueAt(employeeView.getTableClients().getSelectedRow(),2);
        float balance = (float) employeeView.getTableClients().getValueAt(employeeView.getTableClients().getSelectedRow(),3);
        Date date = (Date) employeeView.getTableClients().getValueAt(employeeView.getTableClients().getSelectedRow(),4);
        Long client_id = (Long) employeeView.getTableClients().getValueAt(employeeView.getTableClients().getSelectedRow(),5);

        Account account = new AccountBuilder()
                .setId(id)
                .setIdentNumber(identNr)
                .setType(type)
                .setBalance(balance)
                .setCreationDate(date)
                .setIdClient(client_id)
                .build();
        return account;
    }

}
