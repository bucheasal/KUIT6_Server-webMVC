package jwp.controller;

import jwp.dao.QuestionDao;
import jwp.model.KeyHolder;
import jwp.model.Question;
import jwp.model.User;
import jwp.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionDao questionDao;

    @GetMapping("/show")
    public String qnaShow(@RequestParam("questionId") Long questionId, Model model) throws Exception {
        if (questionId == null) {
            return "redirect:/";
        }

        Question question = questionDao.findByQuestionId(questionId);

        if (question == null) {
            return "redirect:/";
        }
        model.addAttribute("question", question);

        return "qna/show";
    }

    @GetMapping("/form")
    public String qnaCreateForm(HttpSession session,
                                @RequestParam(value = "questionId", required = false) Long questionId,
                                Model model) throws Exception {
        if (!UserSessionUtils.isLogined(session)) {
            return "redirect:/user/loginForm";
        }
        if (questionId != null) { // id 존재 -> 수정
            Question targetQuestion = questionDao.findByQuestionId(questionId);
            if (targetQuestion == null) {
                return "redirect:/";
            }
            User user = UserSessionUtils.getUserFromSession(session);
            if (!targetQuestion.isSameUser(user)) {
                throw new IllegalArgumentException();
            }
            model.addAttribute("question", targetQuestion);
        }
        return "qna/form";
    }

    @PostMapping("/create")
    public String qnaCreateUpdate(@RequestParam(value = "questionId", required = false) Long questionId,
                            @RequestParam("title") String title,
                            @RequestParam("contents") String contents,
                            HttpSession session) throws Exception {
        User loginUser = UserSessionUtils.getUserFromSession(session);
        if (loginUser == null) {
            return "redirect:/user/loginForm";
        }
        if (questionId == null) { // id 없음 -> create
            // Question 생성
            // writer, title, content, createDate, countOfAnswer
            String writer = loginUser.getUserId();
            Question question = new Question(writer, title, contents, 0);
            KeyHolder keyHolder = new KeyHolder();
            keyHolder.setId(questionDao.insert(question).getQuestionId());// insert 후 저장된 Question 반환 (id 포함)
            System.out.println("질문 등록 완료");
            if (keyHolder.getId() == question.getQuestionId()) {
                System.out.println("질문 정상 등록 확인 완료");
            }
            return "redirect:/qna/show?questionId=" + keyHolder.getId();
        } else { // id 있음 -> update
            Question targetQuestion = questionDao.findByQuestionId(questionId);
            if (targetQuestion == null) {
                return "redirect:/";
            }
            User user = UserSessionUtils.getUserFromSession(session);
            if (!targetQuestion.isSameUser(user)) {
                throw new IllegalArgumentException("로그인된 유저와 질문 작성자가 다르면 질문을 수정할 수 없습니다.");
            }
            targetQuestion.updateTitleAndContent(title, contents);
            questionDao.update(targetQuestion);
            return "redirect:/qna/show?questionId=" + questionId;
        }
    }

//    @PostMapping("/update")
//    public String qnaUpdateQuestion(@RequestParam("questionId") String questionId, @RequestParam("title") String title,
//                                    @RequestParam("contents") String contents,
//                                    HttpSession session) throws Exception {
//        if (!UserSessionUtils.isLogined(session)) {
//            return "redirect:/users/loginForm";
//        }
//
//        User user = UserSessionUtils.getUserFromSession(session);
//        Question question = questionDao.findByQuestionId(Integer.parseInt(questionId));
//        if (!question.isSameUser(user)) {
//            throw new IllegalArgumentException("로그인된 유저와 질문 작성자가 다르면 질문을 수정할 수 없습니다.");
//        }
//        question.updateTitleAndContent(title, contents);
//        questionDao.update(question);
//        return "redirect:/";
//    }
//
//    @GetMapping("/updateForm")
//    public String qnaUpdateForm(HttpSession session, @RequestParam("questionId") String questionId, Model model) throws Exception {
//        if (!UserSessionUtils.isLogined(session)) {          // 회원만 질문 등록 가능
//            return "redirect:/user/loginForm";
//        }
//        Question question = questionDao.findByQuestionId(Integer.parseInt(questionId));
//        User user = UserSessionUtils.getUserFromSession(session);
//        if (!question.isSameUser(user)) {
//            throw new IllegalArgumentException();
//        }
//        model.addAttribute("question", question);
//        return "qna/updateForm";
//    }

    @GetMapping("/delete")
    public String qnaDelete(@RequestParam("questionId") Long questionId, HttpSession session) {
        if (questionId == null) {
            return "redirect:/";
        }
        Question targetQuestion = questionDao.findByQuestionId(questionId);
        if (targetQuestion == null) {
            return "redirect:/";
        }
        User curUser = UserSessionUtils.getUserFromSession(session);
        if (!targetQuestion.isSameUser(curUser)) {
            return "redirect:/";
        }
        questionDao.deleteById(targetQuestion);
        return "redirect:/";
    }
}
