package jwp.controller;

import core.db.MemoryUserRepository;
import jwp.model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;

import static core.url.Method.FORWARD;
import static core.url.Method.REDIRECT;
import static core.url.UrlPath.LIST;
import static core.url.UrlPath.LOGIN;

public class ListUserController extends HttpServlet implements Controller{
    @Override
    public String proceed(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return REDIRECT.method + LOGIN.route;
        }
        Object value = session.getAttribute("user");
        if (value == null) {
            return REDIRECT.method + LOGIN.route;
        }
        Collection<User> users = MemoryUserRepository.getInstance().findAll();
        req.setAttribute("users", users);
        return FORWARD.method + LIST.route;
    }
}

