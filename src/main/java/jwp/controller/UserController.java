package jwp.controller;

import jwp.dao.UserDao;
import jwp.model.User;
import jwp.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor //final 생성자 코드 자동 생성
public class UserController {
    private final UserDao userDao;

    @GetMapping("/form")
    public String createUserForm() {
        return "user/form";
    }

    @PostMapping("/signup")
    public String createUser(@ModelAttribute User user) {
        System.out.println("signup userId=" + user.getUserId());
        userDao.insert(user);
        System.out.println("user 회원가입 완료");
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String listUser(HttpSession session, Model model) {
        if (UserSessionUtils.isLogined(session)) {
            model.addAttribute("users", userDao.findAll());
            return "user/list";
        }
        return "redirect:/user/login";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        userDao.update(user);
        return "redirect:/user/list";
    }

    @GetMapping("/updateForm")
    public String updateUserForm(@RequestParam String userId, HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("user");
        if (loginUser == null) {
            return "redirect:/user/login";
        }

        User targetUser = userDao.findByUserId(userId);

        if (targetUser == null) {
            return "redirect:/";
        }

        if (!loginUser.getUserId().equals(targetUser.getUserId())) {
            return "redirect:/"; //다른 사람 수정 불가
        }

        model.addAttribute("user", targetUser);
        return "user/updateForm";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(HttpSession session, @RequestParam("userId") String userId,
                        @RequestParam("password") String password) {
        User loginUser = new User(userId, password);
        User user = userDao.findByUserId(userId);
        if (user != null && user.isSameUser(loginUser)) {
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    @GetMapping("/loginFailed")
    public String loginFailed() {
        return "user/loginFailed";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/";
    }
}