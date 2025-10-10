package jwp.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFailedController implements Controller {
    @Override
    public String proceed(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return "/user/loginFailed";
    }
}
