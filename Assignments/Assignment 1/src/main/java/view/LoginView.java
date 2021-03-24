package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class LoginView extends JFrame{
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private JComboBox jComboBox;

    public LoginView() throws HeadlessException {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(tfUsername);
        add(tfPassword);
        add(btnLogin);
        //add(btnRegister);
        add(jComboBox);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        tfUsername = new JTextField();
        tfPassword = new JTextField();
        btnLogin = new JButton("Login");
        btnRegister = new JButton("Register");
        String roles[] ={"Administrator", "Employee"};
        jComboBox = new JComboBox(roles);

    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public void setTfUsername(String s){
        tfUsername.setText("");
    }

    public void setTfPassword(String s){
        tfPassword.setText("");
    }

    public String getPassword() {
        return tfPassword.getText();
    }

    public String getComboBoxItem(){
        return jComboBox.getItemAt(jComboBox.getSelectedIndex()).toString();
    }

    public void setLoginButtonListener(ActionListener loginButtonListener) {
        btnLogin.addActionListener(loginButtonListener);
    }
    public void setRegisterButtonListener(ActionListener registerButtonListener) {
        btnRegister.addActionListener(registerButtonListener);
    }

    public void setVisible() {
        this.setVisible(true);
    }
}
