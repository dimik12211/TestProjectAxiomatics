package Application.com;

import Application.models.CreditAgreement;
import Application.models.Request;
import Application.services.CreditAgreementService;
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
public class CreditAgreementSearchAndDelete {
    @Autowired
    private RequestService requestService;
    @Autowired
    private CreditAgreementService creditAgreementService;

    /*Формирует и возвращает ModelAndView с CreditAgreementDeletePage.html и выводит таблицу всех кредитных договоров
     * Generates and returns a ModelAndView with CreditAgreementDeletePage.html and displays a table of all loan agreements*/

    @GetMapping(value = "/CreditAgreementDeletePage")
    public ModelAndView CreditAgreementDeletePage() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<CreditAgreement> creditAgreements = creditAgreementService.findAllServices();
            modelAndView.addObject("tableCreditAgreement", creditAgreements);
            modelAndView.setViewName("CreditAgreementDirectory/CreditAgreementDeletePage.html");
        } catch (Exception e) {
            modelAndView.setViewName("Other/ExceptionPage.html");
            modelAndView.addObject("Exception", "Ошибка при совершении запроса: " + e.getMessage());
            e.printStackTrace();
        }
        return modelAndView;
    }

    /*Удаляет кредитный договор, далее формирует и возвращает ModelAndView с CreditAgreementPage.html
     * Deletes the loan agreement, then forms and returns a ModelAndView with CreditAgreementPage.html*/

    @PostMapping(value = "/CreditAgreementDeletePage")
    public ModelAndView CreditAgreementDelete(@RequestParam String id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Request request = requestService.findIdServices(creditAgreementService.findIdServices(Integer.parseInt(id)).getOneToOneRequest().getId());
            request.setRequestToCreditAgreement(null);
            requestService.updateServices(request);
            modelAndView = new ModelAndView(new RedirectView("/CreditAgreementPage"));
            modelAndView.addObject("Status", "Кредитный договор успешно удален");
        } catch (Exception e) {
            modelAndView.setViewName("Other/ExceptionPage.html");
            modelAndView.addObject("Exception", "Ошибка при совершении запроса: " + e.getMessage());
            e.printStackTrace();
        }
        return modelAndView;
    }
}
