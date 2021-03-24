package controller;

import model.User;
import model.validation.Notification;
import service.admin.ServiceAdmin;
import service.employee.ServiceEmployee;
import service.user.AuthenticationService;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alex on 18/03/2017.
 */
public class LoginController {
    private final LoginView loginView;
    private final AdminController adminController;
    private final EmployeeController employeeController;
    private final AuthenticationService authenticationService;
    private final ServiceAdmin authenticationServiceAdmin;
    private final ServiceEmployee serviceEmployee;

    public LoginController(LoginView loginView, AuthenticationService authenticationService, AdminController adminController, ServiceAdmin authenticationServiceAdmin, EmployeeController employeeController, ServiceEmployee serviceEmployee) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        loginView.setLoginButtonListener(new LoginButtonListener());
        //loginView.setRegisterButtonListener(new RegisterButtonListener());

        this.adminController = adminController;
        this.authenticationServiceAdmin = authenticationServiceAdmin;

        this.employeeController = employeeController;
        this.serviceEmployee = serviceEmployee;
    }

    public void setVisible(boolean b) {
        loginView.setVisible(b);

    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            if (loginView.getComboBoxItem().equals("Administrator")) {
                Notification<User> loginNotification = authenticationService.loginAdmin(username, password);
                if (loginNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful as admin!");
                    adminController.setVisible(true);
                    loginView.setVisible(false);
                }
            } else if (loginView.getComboBoxItem().equals("Employee")){
                Notification<User> loginNotification = authenticationService.loginEmployee(username, password);
                if (loginNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful as employee!");
                    employeeController.setVisible(true);
                    loginView.setVisible(false);
                }
            }

            loginView.setTfUsername("");
            loginView.setTfPassword("");


        }
    }

//    private class RegisterButtonListener implements ActionListener {
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            String username = loginView.getUsername();
//            String password = loginView.getPassword();
//
//            Notification<Boolean> registerNotification = authenticationService.register(username, password);
//
//            if (registerNotification.hasErrors()) {
//                JOptionPane.showMessageDialog(loginView.getContentPane(), registerNotification.getFormattedErrors());
//            } else {
//                if (!registerNotification.getResult()) {
//                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration not successful, please try again later.");
//                } else {
//                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration successful as employee!");
//                }
//            }
//        }
//    }


}
