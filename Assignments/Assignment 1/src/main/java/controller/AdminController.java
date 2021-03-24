package controller;

import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import service.admin.ServiceAdmin;
import view.AdminView;
import view.LoginView;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminController {
    private final AdminView adminView;
    private final ServiceAdmin authenticationServiceAdmin;
    private final LoginView loginController;

    public AdminController(AdminView adminView, ServiceAdmin authenticationServiceAdmin, LoginView loginController) {
        this.adminView = adminView;
        this.authenticationServiceAdmin = authenticationServiceAdmin;
        this.loginController = loginController;
        adminView.setViewAllButtonListener(new ViewAllButtonListener());
        adminView.setCreateButtonListener(new CreateButtonListener());
        adminView.setDeleteButtonListener(new DeleteButtonListener());
        adminView.setUpdateUsernameButtonListener(new UpdateUsernameButtonListener());
        adminView.setUpdatePasswordButtonListener(new UpdatePasswordButtonListener());
        adminView.setLogOutButtonListener(new LogOutButtonListener());
    }

    public void setVisible(boolean b) {
        adminView.setVisible(b);

    }

    private class LogOutButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            loginController.setVisible(true);
            adminView.setVisible(false);
        }
    }

    private class ViewAllButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            updateEmployeeTable();

        }
    }

    private class CreateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getUsername();
            String password = adminView.getPassword();

            Notification<java.lang.Boolean> registerNotification = authenticationServiceAdmin.createEmployeeAccount(username, password);

            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Successfully created an employee!");
                }
            }

            adminView.setUsernameText("");
            adminView.setPasswordText("");
        }
    }

    private class DeleteButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(checkIfRowSelectedEmployees()) {
                if (authenticationServiceAdmin.remove(getEmployeeFromTable().getId())) {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Successfully deleted an employee!");
                    updateEmployeeTable();
                } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Error! The employee can not be deleted!");
                }
            }else{
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Error! No row selected!");
            }
        }
    }

    private class UpdateUsernameButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(checkIfRowSelectedEmployees() && (!adminView.getUsername().equals(""))) {
                if (authenticationServiceAdmin.updateEmployeeUsername(adminView.getUsername(), getEmployeeFromTable().getId())) {
                    updateEmployeeTable();
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Successfully updated the employee's username!");
                } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Error! The employee can not be updated!");
                }
            }else{
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Error! Select row/ introduce username!");
            }

            adminView.setUsernameText("");
            adminView.setPasswordText("");
        }
    }

    private class UpdatePasswordButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(checkIfRowSelectedEmployees() && (!adminView.getPassword().equals(""))) {
                if (authenticationServiceAdmin.updateEmployeePassword(adminView.getPassword(), getEmployeeFromTable().getId())) {
                    updateEmployeeTable();
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Successfully updated the employee's password!");
                } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Error! The employee can not be updated!");
                }
            }
            else{
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Error! Select row/ introduce password!");
            }
            adminView.setPasswordText("");
        }
    }

    public void updateEmployeeTable(){
        DefaultTableModel table = new DefaultTableModel();
        table.addColumn("id");
        table.addColumn("username");
        table.addColumn("password");
        table.addColumn("role");
        for (User u :  authenticationServiceAdmin.view()) {
            Object[] o = { u.getId(), u.getUsername(), u.getPassword(), u.getRoles().toString()};
            table.addRow(o);
        }

        adminView.getTableUsers().setModel(table);
    }

    public boolean checkIfRowSelectedEmployees(){
        if(adminView.getTableUsers().getSelectionModel().isSelectionEmpty()){

            return false;
        }else {
            return true;
        }
    }

    public User getEmployeeFromTable() {

        Long userId = (Long) adminView.getTableUsers().getValueAt(adminView.getTableUsers().getSelectedRow(),0);
        String username = (String) adminView.getTableUsers().getValueAt(adminView.getTableUsers().getSelectedRow(),1);
        String userpass = (String) adminView.getTableUsers().getValueAt(adminView.getTableUsers().getSelectedRow(),2);

        User employee = new UserBuilder()
                .setUsername(username)
                .setPassword(userpass)
                .setId(userId)
                .build();

        return employee;
    }

}
