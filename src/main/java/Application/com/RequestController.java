package Application.com;

import Application.models.Client;
import Application.models.Request;
import Application.services.ClientService;
import Application.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RequestController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private RequestService requestService;

    /*Формирует и возвращает ModelAndView с RequestPage.html
     * Generates and returns a ModelAndView with RequestPage.html*/

    @GetMapping(value = "/RequestPage")
    public ModelAndView RequestControllerPage() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("RequestDirectory/RequestPage.html");
        } catch (Exception e) {
            modelAndView.setViewName("Other/ExceptionPage.html");
            modelAndView.addObject("Exception", "Ошибка при совершении запроса: " + e.getMessage());
            e.printStackTrace();
        }
        return modelAndView;
    }

    /*Формирует и возвращает ModelAndView с RequestPage.html, находит и выводит на страницу данные клиента
     * Generates and returns a ModelAndView with RequestPage.html, finds and displays client data on the page*/

    @GetMapping(value = "/RequestPage/{id}")
    public ModelAndView RequestControllerPageFromSearch(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("RequestDirectory/RequestPage.html");

            Client client = clientService.findIdServices(Integer.parseInt(id));
            modelAndView.addObject("FirstNameFromSearch", client.getFirstName());
            modelAndView.addObject("LastNameFromSearch", client.getLastName());
            modelAndView.addObject("PatronymicFromSearch", client.getPatronymic());
            modelAndView.addObject("PassportDataFromSearch", client.getPassportData());
            modelAndView.addObject("FamilyStatusFromSearch", client.getFamilyStatus());
            modelAndView.addObject("AddressFromSearch", client.getAddress());
            modelAndView.addObject("NumberFromSearch", client.getNumber());
        } catch (Exception e) {
            modelAndView.setViewName("Other/ExceptionPage.html");
            modelAndView.addObject("Exception", "Ошибка при совершении запроса: " + e.getMessage());
            e.printStackTrace();
        }
        return modelAndView;
    }

    /*Обрабатывает сразу 2 кнопки. Если нажата кнопка "Отправить заявку", то вызывается метод AddRequest, а если нажата кнопка "Удалить клиента", то вызывается
     метод DeleteClientAndHisRequest. Далее формирует и возвращает ModelAndView с RequestPage.html
     *Processes 2 buttons at once. If the "Отправить заявку" button is pressed, then the AddRequest method is called, and if the "Удалить клиента" button is pressed, then the
     The DeleteClientAndHisRequest method. Next, it generates and returns a ModelAndView with RequestPage.html*/

    @PostMapping(value = "/RequestPage/{id}")
    public ModelAndView AddRequestController(@PathVariable String id,
                                             @RequestParam String PeriodOfWork,
                                             @RequestParam String Post,
                                             @RequestParam String NameOfOrganization,
                                             @RequestParam String LoanAmount,
                                             @RequestParam String NameButtonTap) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            String status = "";
            if (NameButtonTap.length() == 10) {
                status = AddRequest(id, PeriodOfWork, Post, NameOfOrganization, LoanAmount);
            } else if (NameButtonTap.length() == 12) {
                status = DeleteClientAndHisRequest(id);
            }
            modelAndView.addObject("Status", status);
            modelAndView.setViewName("RequestDirectory/RequestPage.html");
        } catch (Exception e) {
            modelAndView.addObject("Status", "Ошибка при совершении запроса: " + e.getMessage());
            modelAndView.setViewName("RequestDirectory/RequestPage.html");
            e.printStackTrace();
        }
        return modelAndView;
    }

    /*Добавляет заявку
     *Adds a request*/

    private String AddRequest(String id, String PeriodOfWork, String Post, String NameOfOrganization, String LoanAmount) {
        try {
            Client client = clientService.findIdServices(Integer.parseInt(id));

            boolean StatusRequest = requestService.RandomStatusRequestServices();
            int creditTerm = requestService.CreditTermService(Integer.parseInt(LoanAmount));
            int ApprovedLoanAmount = requestService.RandomApprovedLoanAmount(Integer.parseInt(LoanAmount));

            Request request = new Request(LoanAmount, StatusRequest, creditTerm, ApprovedLoanAmount, PeriodOfWork, Post, NameOfOrganization);
            request.setOneToManyClient(client);
            client.addRequest(request, client);
            clientService.updateServices(client);
            return "Заявка на кредит успешно добавлена";
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка: " + e.getMessage();
        }
    }

    /*Каскадно удаляет клиента и все его заявки и кредитные договора
     *Cascades deletes the client and all his applications and loan agreements*/

    private String DeleteClientAndHisRequest(String id) {
        try {
            Client client = clientService.findIdServices(Integer.parseInt(id));
            clientService.deleteServices(client);
            return "Клиент успешно удален";
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка: " + e.getMessage();
        }
    }
}
