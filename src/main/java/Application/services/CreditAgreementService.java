package Application.services;

import Application.dao.CreditAgreementDao;
import Application.interfaces.ServicesInterface;
import Application.models.CreditAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditAgreementService implements ServicesInterface<CreditAgreement> {
    @Autowired
    private CreditAgreementDao creditAgreementDao;

    /*Вызывает метод обновления кредитного договора
     *Calls the method of updating the loan agreement*/

    @Override
    public void updateServices(CreditAgreement creditAgreement) {
        try {
            creditAgreementDao.update(creditAgreement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Вызывает метод удаления кредитного договора
     *Calls the method of deleting the loan agreement*/

    @Override
    public void deleteServices(CreditAgreement creditAgreement) {
        try {
            creditAgreementDao.delete(creditAgreement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Вызывает метод поиска кредитного договора по id
     *Calls the method of searching for a loan agreement by id*/

    @Override
    public CreditAgreement findIdServices(int id) {
        try {
            return creditAgreementDao.findId(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Вызывает метод поиска всех кредитных договоров
     *Calls the search method for all loan agreements*/

    @Override
    public List<CreditAgreement> findAllServices() {
        try {
            return creditAgreementDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Вызывает метод поиска всех подписанных кредитных договоров
     *Calls the search method for all signed loan agreements*/

    public List<CreditAgreement> findAllCreditAgreementIfSignetIsTrue() {
        try {
            return creditAgreementDao.findAllCreditAgreementIfSignetIsTrue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Вызывает метод проверяющий существование кредитного договора для заявки с указанным id
     *Calls a method that verifies the existence of a loan agreement for an application with the specified id*/

    public boolean findCreditAgreementRequestIdExist(int Request_id) {
        boolean TrueOrFalse = true;
        try {
            TrueOrFalse = creditAgreementDao.findCreditAgreementRequestIdExist(Request_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TrueOrFalse;
    }

    /*Вызывает метод поиска кредитного договора по id заявки
     *Calls the method of searching for a loan agreement by the application id*/

    public CreditAgreement findCreditAgreementRequest(int Request_id) {
        try {
            return creditAgreementDao.findCreditAgreementRequest(Request_id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
