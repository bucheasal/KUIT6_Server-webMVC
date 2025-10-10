package jwp.controller;

import core.db.MemoryUserRepository;
import jwp.model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;

public class ListUserController extends HttpServlet implements Controller{
    @Override
    public String proceed(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return "redirect:/user/login";
        }
        Object value = session.getAttribute("user");
        if (value == null) {
            return "redirect:/user/login";
        }
        Collection<User> users = MemoryUserRepository.getInstance().findAll();
        req.setAttribute("users", users);
        return "/user/list";
    }
}

