package Application.dao;

import Application.interfaces.DaoInterface;
import Application.models.Client;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientDao implements DaoInterface<Client> {
    @Autowired
    private Session session;

    /*Обновляет данные клиента в БД
     *Updates the client's data in the database*/

    @Override
    public void update(Client clientClass) {
        try {
            Transaction transaction = session.beginTransaction();
            session.update(clientClass);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Удаляет из БД клиента
     *Deletes from the client's database*/

    @Override
    public void delete(Client clientClass) {
        try {
            Transaction transaction = session.beginTransaction();
            session.delete(clientClass);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Находит в БД клиента по Id
     *Finds a client in the database by Id*/

    @Override
    public Client findId(int id) {
        try {
            return session.get(Client.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Находит в БД всех клиентов
     *Finds all clients in the database*/

    @Override
    public List<Client> findAll() {
        try {
            return session.createQuery("select a from Client a", Client.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Сохраняет клиента в БД
     *Saves the client to the database*/

    public void Save(Client clientClass) {
        try {
            Transaction transaction = session.beginTransaction();
            session.save(clientClass);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Находит в БД клиента по паспортным данным
     *Finds a client in the database based on passport data*/

    public Client findClientPassportData(String PassportData) {
        try {
            Query query = session.createQuery("select a from Client a where a.PassportData = :PassportData");
            query.setParameter("PassportData", PassportData);
            return (Client) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Находит в БД клиента по номеру телефона
     *Finds a client in the database by phone number*/

    public Client findClientNumberPhone(String Number) {
        try {
            Query query = session.createQuery("select a from Client a where a.Number = :Number");
            query.setParameter("Number", Number);
            return (Client) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Находит в БД клиента по полному имени
     *Finds a client in the database by full name*/

    public List<Client> findClientFullName(String firstName, String lastName, String patronymic) {
        try {
            Query query = session.createQuery("select a from Client a where a.FirstName = :firstName and a.LastName = :lastName and a.Patronymic = :patronymic");
            query.setParameter("firstName", firstName);
            query.setParameter("lastName", lastName);
            query.setParameter("patronymic", patronymic);
            List<Client> clients = query.getResultList();
            return clients;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
