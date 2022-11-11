package Application.com;

import Application.models.Client;
import Application.models.CreditAgreement;
import Application.models.Request;
import Application.services.ClientService;
import Application.services.CreditAgreementService;
import Application.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CreditAgreementController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private CreditAgreementService creditAgreementService;

    private Map<Request, Client> tableClientRequests = new HashMap<>();

    /*Формирует и возвращает ModelAndView с CreditAgreementPage.html
     * Generates and returns a ModelAndView with CreditAgreementPage.html*/

    @GetMapping(value = "/CreditAgreementPage")
    public ModelAndView CreditAgreementPage() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("CreditAgreementDirectory/CreditAgreementPage.html");
        } catch (Exception e) {
            modelAndView.setViewName("Other/ExceptionPage.html");
            modelAndView.addObject("Exception", "Ошибка при получении доступа к сайту: " + e.getMessage());
            e.printStackTrace();
        }
        return modelAndView;
    }

    /*Формирует и возвращает ModelAndView с CreditAgreementPage.html и выводит на html сраницу заявку по id
     * Generates and returns a ModelAndView with CreditAgreementPage.html and outputs an application by id to the html page*/

    @GetMapping(value = "/CreditAgreementPage/{id}")
    public ModelAndView CreditAgreementPageId(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Request request = requestService.findIdServices(Integer.parseInt(id));
            modelAndView = AddParametersToPage(new ModelAndView(), request);
            modelAndView.setViewName("CreditAgreementDirectory/CreditAgreementPage.html");
        } catch (Exception e) {
            modelAndView.addObject("Status", "Ошибка: " + e.getMessage());
            modelAndView.setViewName("CreditAgreementDirectory/CreditAgreementPage.html");
            e.printStackTrace();
        }
        return modelAndView;
    }

    /*Ищет клиента по паспортным данным/ФИО/номеру телефона (в случае если введены несколько параметров, приоритет отдается: паспортные дынные -> ФИО -> номер телефона)
     * и формирует ModelAndView с CreditAgreementPage.html выводя на нее параметры
     * Searches for a client by passport data /full name /phone number (if several parameters are entered, priority is given to: passport data -> FULL NAME -> phone number)
     * and generates a ModelAndView with CreditAgreementPage.html displaying parameters on it*/

    @PostMapping(value = "/CreditAgreementPage", params = {"SearchClientAndRequest"})
    public ModelAndView SearchRequestInCreditAgreementPage(@RequestParam String PassportData,
                                                           @RequestParam String FirstName,
                                                           @RequestParam String LastName,
                                                           @RequestParam String Patronymic,
                                                           @RequestParam String Number) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            if (PassportData.length() > 0) {
                tableClientRequests.clear();

                Client client = clientService.findClientPassportData(PassportData);
                List<Request> requests = requestService.findRequestsWithIdClient(client.getId());

                for (Request request : requests) {
                    tableClientRequests.put(request, client);
                }
                modelAndView.addObject("tableClientRequests", tableClientRequests);

            } else if ((FirstName.length() > 0) && (LastName.length() > 0) && (Patronymic.length() > 0)) {
                tableClientRequests.clear();

                List<Client> clients = clientService.findClientFullName(FirstName, LastName, Patronymic);

                for (Client client : clients) {
                    List<Request> requests = requestService.findRequestsWithIdClient(client.getId());

                    for (Request request : requests) {
                        tableClientRequests.put(request, client);
                    }
                }
                modelAndView.addObject("tableClientRequests", tableClientRequests);

            } else if (Number.length() > 0) {
                tableClientRequests.clear();

                Client client = clientService.findClientNumberPhone(Number);
                List<Request> requests = requestService.findRequestsWithIdClient(client.getId());

                for (Request request : requests) {
                    tableClientRequests.put(request, client);
                }

                modelAndView.addObject("tableClientRequests", tableClientRequests);

            } else {
                modelAndView.addObject("Status", "Клиент с такими данными не найден");
            }
            modelAndView.addObject("TrueOrFalse", true);
            modelAndView.setViewName("CreditAgreementDirectory/CreditAgreementPage.html");
        } catch (Exception e) {
            modelAndView.addObject("Status", "Данный клиент не найден, ошибка: " + e.getMessage());
            e.printStackTrace();
        }
        return modelAndView;
    }

    /*Получаем параметр id (заявки на кредит) и добавляем его в адресную строку
     * We get and add the id parameter (loan application) to the address bar*/

    @PostMapping(value = "/CreditAgreementPage", params = {"ButtonRow"})
    public ModelAndView SearchRequestInCreditAgreementPage(@RequestParam String id) {
        try {
            return new ModelAndView(new RedirectView("CreditAgreementPage/" + id));
        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("Other/ExceptionPage.html");
            modelAndView.addObject("Exception", "Ошибка при совершении запроса: " + e.getMessage());
            return modelAndView;
        }
    }

    /*Получаем дату подписания кредитного договора, статус подписания и id заявки для которой формируется договор, далее проверяем существует ли уже договор
     * для этой заявки, если да то обновляем данные, если нет то формируем договор
     * We get the date of signing the loan agreement, the status of signing and the id of the application for which the contract is being formed, then we check
     * whether there is already a contract for this application, if yes, we update the data, if not, we form the contract*/

    @PostMapping(value = "/CreditAgreementPage/{id}")
    public ModelAndView AddCreditAgreement(@PathVariable String id,
                                           @RequestParam String Date,
                                           @RequestParam String StatusSignage) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Request request = requestService.findIdServices(Integer.parseInt(id));
            modelAndView = AddParametersToPage(new ModelAndView(), request);
            if (creditAgreementService.findCreditAgreementRequestIdExist(Integer.parseInt(id))) {
                CreditAgreement creditAgreement = creditAgreementService.findCreditAgreementRequest(Integer.parseInt(id));
                creditAgreement.setDateOfSignature(Date);
                creditAgreement.setSigned(Boolean.parseBoolean(StatusSignage));
                creditAgreementService.updateServices(creditAgreement);
                modelAndView.addObject("Status", "Статус кредитного договора обновлен");
            } else {
                CreditAgreement creditAgreement = new CreditAgreement(Date, Boolean.parseBoolean(StatusSignage));

                creditAgreement.setOneToOneRequest(request);
                request.setRequestToCreditAgreement(creditAgreement);
                requestService.updateServices(request);
                modelAndView.addObject("Status", "Кредитный договор создан");
            }
            modelAndView.setViewName("CreditAgreementDirectory/CreditAgreementPage.html");
        } catch (Exception e) {
            modelAndView.addObject("Status", "Ошибка, кредитный договор не создан: " + e.getMessage());
            e.printStackTrace();
        }
        return modelAndView;
    }

    /*Метод в котором в ModelAndView добавляются параметры выбранной заявки на кредит (таблица показывающая выбранную заявку) и выводится таблица со всеми заявками
     * A method in which the parameters of the selected loan application are added to ModelAndView (a table showing the selected application) and a table with all
     * applications is displayed*/

    private ModelAndView AddParametersToPage(ModelAndView modelAndView, Request request) {
        try {
            modelAndView.addObject("FirstName", request.getOneToManyClient().getFirstName());
            modelAndView.addObject("LastName", request.getOneToManyClient().getLastName());
            modelAndView.addObject("Patronymic", request.getOneToManyClient().getPatronymic());
            modelAndView.addObject("PassportData", request.getOneToManyClient().getPassportData());
            modelAndView.addObject("FamilyStatus", request.getOneToManyClient().getFamilyStatus());
            modelAndView.addObject("Address", request.getOneToManyClient().getAddress());
            modelAndView.addObject("Number", request.getOneToManyClient().getNumber());
            modelAndView.addObject("PeriodOfWork", request.getPeriodOfWork());
            modelAndView.addObject("Position", request.getPosition());
            modelAndView.addObject("NameOfTheOrganization", request.getNameOfTheOrganization());
            modelAndView.addObject("ApprovedLoanAmount", request.getApprovedLoanAmount());

            modelAndView.addObject("tableClientRequests", tableClientRequests);
            modelAndView.addObject("TrueOrFalse", true);
            modelAndView.addObject("TrueOrFalse2", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }
}
