package Application.com;

import Application.models.CreditAgreement;
import Application.services.CreditAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdditionalFunctionallyController {

    @Autowired
    private CreditAgreementService creditAgreementService;

    /*Формирует ModelAndView с AdditionalFunctionallyPage.html и выводит на Http страницу все подписанные кредитные договора.
     * Generates and returns a ModelAndView with AdditionalFunctionallyPage.html and displays all signed loan agreements on the Http page.*/

    @GetMapping(value = "/AdditionalFunctionallyPage")
    public ModelAndView AdditionalFunctionallyPage() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<CreditAgreement> creditAgreements = creditAgreementService.findAllCreditAgreementIfSignetIsTrue();
            modelAndView.addObject("tableCreditAgreement", creditAgreements);
            modelAndView.setViewName("Other/AdditionalFunctionallyPage.html");
        } catch (Exception e) {
            modelAndView.setViewName("Other/ExceptionPage.html");
            modelAndView.addObject("Exception", "Ошибка при совершении запроса: " + e.getMessage());
            e.printStackTrace();
        }
        return modelAndView;
    }
}
