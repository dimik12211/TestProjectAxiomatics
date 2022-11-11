package Application.services;

import Application.dao.RequestDao;
import Application.interfaces.ServicesInterface;
import Application.models.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService implements ServicesInterface<Request> {
    @Autowired
    private RequestDao requestDao;

    /*Вызывает метод обновления данных заявки
     *Calls the request data update method*/

    @Override
    public void updateServices(Request request) {
        try {
            requestDao.update(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Вызывает метод удаления заявки
     *Calls the request deletion method*/

    @Override
    public void deleteServices(Request request) {
        try {
            requestDao.delete(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Вызывает метод поиска заявки по id
     *Calls the request search method by id*/

    @Override
    public Request findIdServices(int id) {
        try {
            return requestDao.findId(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Вызывает метод поиска всех заявок
     *Calls the search method for all applications*/

    @Override
    public List<Request> findAllServices() {
        try {
            return requestDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Вызывает метод поиска заявок определенного клиента (по id клиента)
     *Calls the method of searching for applications of a certain client (by client id)*/

    public List<Request> findRequestsWithIdClient(int client_id) {
        try {
            return requestDao.findRequestsWithIdClient(client_id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Вызывает метод рандомной генерации статуса заявки
     *Calls the method of random generation of the application status*/

    public boolean RandomStatusRequestServices() {
        try {
            return requestDao.RandomStatusRequest();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*Вызывает метод генерации срока кредита
     *Calls the method of generating the loan term*/

    public int CreditTermService(int CreditTerm) {
        try {
            return requestDao.CreditTerm(CreditTerm);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /*Вызывает метод рандомной генерации одобренной суммы кредита
     *Calls the method of random generation of the approved loan amount*/

    public int RandomApprovedLoanAmount(int MyCredit) {
        try {
            return requestDao.RandomApprovedLoanAmount(MyCredit);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
