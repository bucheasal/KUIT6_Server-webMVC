package jwp.controller.question;

import jwp.dao.QuestionDao;
import jwp.model.KeyHolder;
import jwp.model.Question;
import jwp.model.User;
import jwp.util.UserSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

public class CreateQuestionController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession(false);
        User loginUser = UserSessionUtils.getUserFromSession(session);
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        // Question 생성
        // writer, title, content, createDate, countOfAnswer
        Question question = new Question(req.getParameter("writer"),
                req.getParameter("title"),
                req.getParameter("content"),
                LocalDateTime.now(),
                Integer.parseInt(req.getParameter("countOfAnswer")));

        KeyHolder keyHolder = new KeyHolder();
        keyHolder.setId(QuestionDao.getInstance().insert(question).getQuestionId());// insert 후 저장된 Question 반환 (id 포함)
        System.out.println("질문 등록 완료");
        if (keyHolder.getId() == question.getQuestionId()) {
            System.out.println("질문 정상 등록 확인 완료");
        }
        return "redirect:/qna/show.jsp";
    }
}