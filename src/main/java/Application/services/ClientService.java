package Application.services;

import Application.dao.ClientDao;
import Application.interfaces.ServicesInterface;
import Application.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements ServicesInterface<Client> {

    @Autowired
    private ClientDao clientDao;

    /*Вызывает метод обновления клиента
     *Calls the client update method*/

    @Override
    public void updateServices(Client client) {
        try {
            clientDao.update(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Вызывает метод удаления клиента
     *Calls the client deletion method*/

    @Override
    public void deleteServices(Client client) {
        try {
            clientDao.delete(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Вызывает метод поиска клиента по id
     *Calls the client id search method*/

    @Override
    public Client findIdServices(int id) {
        try {
            return clientDao.findId(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Вызывает метод поиска всех клиентов
     *Calls the method of searching for all clients*/

    @Override
    public List<Client> findAllServices() {
        try {
            return clientDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Вызывает метод сохранения клиентов
     *Calls the method of saving clients*/

    public void saveClient(Client client) {
        try {
            clientDao.Save(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Вызывает метод поиска клиента по паспортным данным
     *Calls the method of searching for a client by passport data*/

    public Client findClientPassportData(String PassportData) {
        try {
            return clientDao.findClientPassportData(PassportData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Вызывает метод поиска клиента по номеру телефона
     *Calls the client search method by phone number*/

    public Client findClientNumberPhone(String NumberPhone) {
        try {
            return clientDao.findClientNumberPhone(NumberPhone);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Вызывает метод поиска клиента по полному имени
     *Calls the client search method by full name*/

    public List<Client> findClientFullName(String firstName, String lastName, String patronymic) {
        try {
            return clientDao.findClientFullName(firstName, lastName, patronymic);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
