package jwp.controller;

import jwp.dao.QuestionDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final QuestionDao questionDao;
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("questions", questionDao.findAll());
        return "home";
    }
}