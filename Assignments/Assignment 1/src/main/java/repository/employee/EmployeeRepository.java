package repository.employee;

import model.Client;
import repository.EntityNotFoundException;

import java.util.List;


public interface EmployeeRepository {

    List<Client> findAll();

    boolean saveClient(Client client);

    boolean removeClient(Long id);

    boolean updateClientName(Client client);

    Client findById(Long id) throws EntityNotFoundException;

    void removeAll();

}
