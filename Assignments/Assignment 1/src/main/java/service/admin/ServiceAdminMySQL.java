package service.admin;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.admin.AdminRepository;
import repository.security.RightsRolesRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class ServiceAdminMySQL implements ServiceAdmin {

    private final AdminRepository adminRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public ServiceAdminMySQL(AdminRepository adminRepository, RightsRolesRepository rightsRolesRepository) {
        this.adminRepository = adminRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<java.lang.Boolean> createEmployeeAccount(String username, String password) {
        Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(customerRole))
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<java.lang.Boolean> userRegisterNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(java.lang.Boolean.FALSE);
        } else {
            user.setPassword(password);
            userRegisterNotification.setResult(adminRepository.saveEmployee(user));
        }
        return userRegisterNotification;
    }

    @Override
    public boolean logout(User user) {
        return false;
    }

    @Override
    public List<User> view(){
        System.out.println(adminRepository.viewEmployees().toString());
        return adminRepository.viewEmployees();
    }

    @Override
    public boolean remove(Long id) {
        return adminRepository.remove(id);
    }

    @Override
    public boolean updateEmployeeUsername(String username, Long id){
        User employee = new UserBuilder()
                .setUsername(username)
                .setId(id)
                .build();
        return adminRepository.updateEmployeeUsername(employee);
    }

    @Override
    public boolean updateEmployeePassword(String password, Long id){
        User employee = new UserBuilder()
                .setPassword(password)
                .setId(id)
                .build();
        return adminRepository.updateEmployeePassword(employee);
    }

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
