package service.employee;

import model.Client;

import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.List;


public interface ServiceEmployee {

    Notification<Boolean> createClient(String name, String cardNumber, String pnc, String address);

    List<Client> viewClients();

    //boolean updateClientName(String name, Long id);

    Notification<Boolean> updateClientName1(String name, Long id);

    Client findById(Long id) throws EntityNotFoundException;


}
