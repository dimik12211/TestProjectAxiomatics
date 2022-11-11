package Application.dao;

import Application.interfaces.DaoInterface;
import Application.models.Request;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Random;

@Repository
public class RequestDao implements DaoInterface<Request> {
    @Autowired
    private Session session;

    /*Обновляет данные заяки в БД
     *Updates the application data in the database*/

    @Override
    public void update(Request request) {
        try {
            Transaction transaction = session.beginTransaction();
            session.update(request);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Удаляет заявку в БД
     *Deletes the request in the database*/

    @Override
    public void delete(Request request) {
        try {
            Transaction transaction = session.beginTransaction();
            session.delete(request);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Находит в БД заявку по id
     *Finds an application in the database by id*/

    @Override
    public Request findId(int id) {
        try {
            return session.get(Request.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Request();
        }
    }

    /*Находит в БД все заявки
     *Finds all applications in the database*/

    @Override
    public List<Request> findAll() {
        try {
            List<Request> requests = session.createQuery("select distinct r from Request r inner join fetch r.OneToManyClient as c").getResultList();
            return requests;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Находит в БД все заявки принадлежащие клиенту по указанному id
     *Finds in the database all applications belonging to the client by the specified id*/

    public List<Request> findRequestsWithIdClient(int client_id) {
        try {
            Query query = session.createQuery("select a from Request a where a.OneToManyClient.id = :client_id and a.Status = true ");
            query.setParameter("client_id", client_id);
            List<Request> requests = query.getResultList();
            return requests;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Рандомно генерирует рандомное значение bool
     *Randomly generates a random bool value*/

    public boolean RandomStatusRequest() {
        try {
            return new Random().nextBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*Генерирует значение creditTerm (срок кредита)
     *Generates the value of credit Term (loan term)*/

    public int CreditTerm(int creditTerm) {
        try {
            if (creditTerm < 100000) {
                return 12;
            } else if ((creditTerm > 100000) && (creditTerm < 500000)) {
                return 24;
            } else if (creditTerm > 500000) {
                return 36;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /*Рандомно генерирует ApprovedLoanAmount (одобренную сумму кредита) от 0 до запрашиваемой суммы кредита
     *Randomly generates an ApprovedLoanAmount (approved loan amount) from 0 to the requested loan amount*/

    public int RandomApprovedLoanAmount(int MyCredit) {
        try {
            return new Random().nextInt(MyCredit);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}