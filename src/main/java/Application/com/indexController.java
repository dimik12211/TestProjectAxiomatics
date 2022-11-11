package Application.com;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class indexController {

    /*Обрабатывает стартовую страницу
     * Processes the start page*/

    @GetMapping(value = "/")
    public ModelAndView indexPageController() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("Other/index.html");
        } catch (Exception e) {
            modelAndView.setViewName("Other/ExceptionPage.html");
            modelAndView.addObject("Exception", "Ошибка при совершении запроса: " + e.getMessage());
            e.printStackTrace();
        }
        return modelAndView;
    }
}
