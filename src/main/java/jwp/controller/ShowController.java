package jwp.controller;

import core.mvc.Controller;
import jwp.dao.QuestionDao;
import jwp.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String idParam = req.getParameter("questionId");
        if (idParam == null || idParam.isBlank()) {
            return "redirect:/";
        }

        long questionId = Long.parseLong(idParam);
        QuestionDao questionDao = QuestionDao.getInstance();

        Question question = questionDao.findByQuestionId(questionId);

        req.setAttribute("question", question);

        return "/qna/show.jsp";
    }
}
