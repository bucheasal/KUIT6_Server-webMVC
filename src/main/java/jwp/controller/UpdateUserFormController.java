package jwp.controller;

import jwp.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UpdateUserFormController implements Controller{
    @Override
    public String proceed(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        HttpSession session = req.getSession(false);
        if (session == null) {
            return "redirect:/";
        }
        User loginUser = (User) session.getAttribute("user");
        if (loginUser == null||!userId.equals(loginUser.getUserId()) ) {
            return "redirect:/";
        }
        req.setAttribute("user", loginUser);
        return "/user/updateForm";
    }
}
