package Application.dao;

import Application.interfaces.DaoInterface;
import Application.models.CreditAgreement;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CreditAgreementDao implements DaoInterface<CreditAgreement> {
    @Autowired
    private Session session;

    /*Обновляет данные кредитного договора в БД
     *Updates the data of the loan agreement*/

    @Override
    public void update(CreditAgreement creditAgreement) {
        try {
            Transaction transaction = session.beginTransaction();
            session.update(creditAgreement);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Удаляет кредитный договор в БД
     *Deletes the loan agreement*/

    @Override
    public void delete(CreditAgreement creditAgreement) {
        try {
            Transaction transaction = session.beginTransaction();
            session.delete(creditAgreement);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Находит в БД кредитный договор по id
     *Finds a loan agreement by id*/

    @Override
    public CreditAgreement findId(int id) {
        try {
            return session.get(CreditAgreement.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Находит в БД все кредитные договора (данные заявки и клиента подгружаются)
     *Finds all loan agreements (application and client data are loaded)*/

    @Override
    public List<CreditAgreement> findAll() {
        try {
            List<CreditAgreement> creditAgreements = session.createQuery("select distinct ca from CreditAgreement ca inner join fetch ca.OneToOneRequest as r inner join fetch r.OneToManyClient").getResultList();
            return creditAgreements;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Находит в БД все подписанные кредитные договора
     *Finds all signed loan agreements*/

    public List<CreditAgreement> findAllCreditAgreementIfSignetIsTrue() {
        try {
            List<CreditAgreement> creditAgreements = session.createQuery("select distinct ca from CreditAgreement ca inner join fetch ca.OneToOneRequest as r inner join fetch r.OneToManyClient as c where ca.Signed = true").getResultList();
            return creditAgreements;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Проверяет существует ли в БД кредитный договор для заявки по указанному id
     *Checks whether there is a loan agreement for the application at the specified id*/

    public boolean findCreditAgreementRequestIdExist(int Request_id) {
        boolean Exist = true;
        try {
            Query query = session.createQuery("from CreditAgreement as ca where ca.OneToOneRequest.id = :Request_id");
            query.setParameter("Request_id", Request_id);
            Exist = query.getResultList().size() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Exist;
    }

    /*Находит в БД кредитный договор по id заявки
     *Finds the loan agreement by the application id*/

    public CreditAgreement findCreditAgreementRequest(int Request_id) {
        try {
            Query query = session.createQuery("from CreditAgreement as ca where ca.OneToOneRequest.id = :Request_id");
            query.setParameter("Request_id", Request_id);
            return (CreditAgreement) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
