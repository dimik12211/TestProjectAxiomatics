package Application.com;

import Application.models.Client;
import Application.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class RequestSearchController {

    @Autowired
    private ClientService clientService;

    /*Выводит всех клиентов в таблицу. Формирует и возвращает ModelAndView с RequestPageSearch.html
     *Displays all clients in a table. Generates and returns a ModelAndView with RequestPageSearch.html*/

    @GetMapping(value = "/RequestPageSearch")
    public ModelAndView RequestSearchControllerPage() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<Client> tableClients = clientService.findAllServices();
            modelAndView.addObject("tableClients", tableClients);
            modelAndView.setViewName("RequestDirectory/RequestPageSearch.html");
        } catch (Exception e) {
            modelAndView.setViewName("Other/ExceptionPage.html");
            modelAndView.addObject("Exception", "Ошибка при совершении запроса: " + e.getMessage());
            e.printStackTrace();
        }
        return modelAndView;
    }

    /*Добавляет в адресную строку: "RequestPage/'id выбранного клиента'"
     *Adds to the address bar: "RequestPage/'id of the selected client'"*/

    @PostMapping(value = "/RequestPageSearch")
    public ModelAndView FromRequestPageSearchToRequestPage(@RequestParam String id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView = new ModelAndView(new RedirectView("RequestPage/" + id));
        } catch (Exception e) {
            modelAndView.setViewName("Other/ExceptionPage.html");
            modelAndView.addObject("Exception", "Ошибка при совершении запроса: " + e.getMessage());
            e.printStackTrace();
        }
        return modelAndView;
    }
}
