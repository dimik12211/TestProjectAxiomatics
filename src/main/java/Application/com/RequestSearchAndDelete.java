package Application.com;

import Application.models.Request;
import Application.services.ClientService;
import Application.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class RequestSearchAndDelete {
    @Autowired
    private ClientService clientService;
    @Autowired
    private RequestService requestService;

    /*Находит все заявки и далее формирует и возвращает ModelAndView с RequestPageSearchAndDelete.html
     *Finds all applications and then generates and returns a ModelAndView with RequestPageSearchAndDelete.html*/

    @GetMapping(value = "/RequestPageSearchAndDelete")
    public ModelAndView RequestPageSearchAndDelete() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<Request> requests = requestService.findAllServices();
            modelAndView.addObject("tableRequest", requests);
            modelAndView.setViewName("RequestDirectory/RequestPageSearchAndDelete.html");
        } catch (Exception e) {
            modelAndView.setViewName("Other/ExceptionPage.html");
            modelAndView.addObject("Exception", "Ошибка при совершении запроса: " + e.getMessage());
            e.printStackTrace();
        }
        return modelAndView;
    }

    /*Удаляет заявку. Формирует и возвращает ModelAndView с RequestPage.html
     *Deletes the request. Generates and returns a ModelAndView with RequestPage.html*/

    @PostMapping(value = "/RequestPageSearchAndDelete")
    public ModelAndView RequestDelete(@RequestParam String id) {
        ModelAndView modelAndView = new ModelAndView(new RedirectView("RequestPage"));
        try {
            clientService.findIdServices(requestService.findIdServices(Integer.parseInt(id)).getOneToManyClient().getId()).removeRequest(requestService.findIdServices(Integer.parseInt(id)));
            requestService.deleteServices(requestService.findIdServices(Integer.parseInt(id)));
            modelAndView.addObject("Status", "Заявка успешна удалена");
        } catch (Exception e) {
            modelAndView.addObject("Status", "Заявка не удалена. Ошибка: " + e.getMessage());
        }
        return modelAndView;
    }
}
