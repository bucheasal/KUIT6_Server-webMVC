package jwp.controller;

import core.db.MemoryUserRepository;
import jwp.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static core.url.Method.REDIRECT;
import static core.url.UrlPath.HOME;
import static core.url.UrlPath.LOGINFAILED;

public class LoginController implements Controller{
    @Override
    public String proceed(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        if (userId == null || password == null || userId.isEmpty() || password.isEmpty()) {
            return REDIRECT.method + LOGINFAILED.route;
        }

        User user = MemoryUserRepository.getInstance().findUserById(userId);
        if (user == null || !user.getPassword().equals(password)) {
            return REDIRECT.method + LOGINFAILED.route;
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        return REDIRECT.method + HOME.route;
    }
}
