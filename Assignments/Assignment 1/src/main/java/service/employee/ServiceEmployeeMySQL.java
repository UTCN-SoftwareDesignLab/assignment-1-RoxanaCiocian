package service.employee;

import model.Client;
import model.builder.ClientBuilder;
import model.validation.ClientValidator;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.employee.EmployeeRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ServiceEmployeeMySQL implements ServiceEmployee {

    private final EmployeeRepository employeeRepository;
    private final Connection connection;

    public ServiceEmployeeMySQL(EmployeeRepository employeeRepository, Connection connection) {
        this.employeeRepository = employeeRepository;
        this.connection = connection;

    }

    @Override
    public Notification<Boolean> createClient(String name, String cardNumber, String pnc, String address) {
        Client client = new ClientBuilder()
                .setName(name)
                .setCardNumber(cardNumber)
                .setPnc(pnc)
                .setAddress(address)
                .build();

        ClientValidator clientValidator = new ClientValidator(client);
        boolean clientValid = clientValidator.validate();
        Notification<Boolean> clientRegisterNotification = new Notification<>();

        if (!clientValid) {
            clientValidator.getErrors().forEach(clientRegisterNotification::addError);
            clientRegisterNotification.setResult(Boolean.FALSE);
        } else {
            clientRegisterNotification.setResult(employeeRepository.saveClient(client));
        }
        return clientRegisterNotification;
    }

    @Override
    public List<Client> viewClients() {
        System.out.println(employeeRepository.findAll().toString());
        return employeeRepository.findAll();
    }


//    @Override
//    public boolean updateClientName(String name, Long id) {
//        Client client = new ClientBuilder()
//                .setName(name)
//                .setId(id)
//                .build();
//        return employeeRepository.updateClientName(client);
//    }

    @Override
    public Notification<Boolean> updateClientName1(String name, Long id) {
        Client client = new ClientBuilder()
                .setName(name)
                .setId(id)
                .build();

        ClientValidator clientValidator = new ClientValidator(client);
        boolean clientValid = clientValidator.validateUpdate();
        Notification<Boolean> clientRegisterNotification = new Notification<>();

        if (!clientValid) {
            clientValidator.getErrors().forEach(clientRegisterNotification::addError);
            clientRegisterNotification.setResult(Boolean.FALSE);
        } else {
            clientRegisterNotification.setResult(employeeRepository.updateClientName(client));
        }
        return clientRegisterNotification;
    }

    public Client findById(Long id) throws EntityNotFoundException {
        return employeeRepository.findById(id);
    }




}
