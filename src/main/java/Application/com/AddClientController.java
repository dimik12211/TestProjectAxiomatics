package Application.com;

import Application.models.Client;
import Application.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AddClientController {

    @Autowired
    private ClientService clientService;

    /*Формирует и возвращает ModelAndView с AddClientPage.html.
     * Generates and returns a ModelAndView with an AddClientPage.html.*/

    @GetMapping(value = "/AddClientPage")
    public ModelAndView HelloPageController() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("AddClientDirectory/AddClientPage.html");
        } catch (Exception e) {
            modelAndView.setViewName("Other/ExceptionPage.html");
            modelAndView.addObject("Exception", "Ошибка при совершении запроса: " + e.getMessage());
            e.printStackTrace();
        }
        return modelAndView;
    }

    /*Обрабатывает запрос на добавление клиента и выдает результат: добавлен/не добавлен.
     * Processes a request to add a client and outputs the result: added/not added.*/

    @PostMapping(value = "/AddClientPage")
    public ModelAndView AddClientPageController(@RequestParam String FirstName,
                                                @RequestParam String LastName,
                                                @RequestParam String Patronymic,
                                                @RequestParam String PassportData,
                                                @RequestParam String FamilyStatus,
                                                @RequestParam String Address,
                                                @RequestParam String Number) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Client client = new Client(FirstName, LastName, Patronymic, PassportData, FamilyStatus, Address, Number);
            clientService.saveClient(client);
            modelAndView.setViewName("AddClientDirectory/AddClientPage.html");
            modelAndView.addObject("Status", "Клиент успешно добавлен");
        } catch (Exception e) {
            modelAndView.setViewName("AddClientDirectory/AddClientPage.html");
            modelAndView.addObject("Status", "Клиент не добавлен, ошибка: " + e.getMessage());
            e.printStackTrace();
        }
        return modelAndView;
    }
}
