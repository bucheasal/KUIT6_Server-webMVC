package jwp.controller;

import jwp.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static core.url.Method.FORWARD;
import static core.url.Method.REDIRECT;
import static core.url.UrlPath.*;

public class UpdateUserFormController implements Controller{
    @Override
    public String proceed(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        HttpSession session = req.getSession(false);
        if (session == null) {
            return REDIRECT.method + HOME.route;
        }
        User loginUser = (User) session.getAttribute("user");
        if (loginUser == null||!userId.equals(loginUser.getUserId()) ) {
            return REDIRECT.method + HOME.route;
        }
        req.setAttribute("user", loginUser);
        return FORWARD.method + UPDATEFORM.route;
    }
}
