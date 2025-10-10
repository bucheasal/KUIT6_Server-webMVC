package jwp.controller;

import core.db.MemoryUserRepository;
import jwp.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateUserController extends HttpServlet implements Controller{
    @Override
    public String proceed(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User updateUser = new User(req.getParameter("userId"),
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email"));
        User repoUser = MemoryUserRepository.getInstance().findUserById(updateUser.getUserId());
        repoUser.update(updateUser);
        MemoryUserRepository.getInstance().changeUserInfo(repoUser);
        return "redirect:/user/list";
    }
}
