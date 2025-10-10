package jwp.dispatcher;

import jwp.controller.*;

import java.util.HashMap;
import java.util.Map;

public class RequestMapper {
    private static Map<String, Controller> controller = new HashMap<>();
    private static RequestMapper requestMapper = new RequestMapper();
    //싱글톤으로 해야하기 때문
    private RequestMapper() {
        controller.put("", new HomeController());
        controller.put("/", new HomeController());
        controller.put("/user/signup", new CreateUserController());
        controller.put("/user/list", new ListUserController());
        controller.put("/user/login", new LoginController());
        controller.put("/user/logout", new LogoutController());
        controller.put("/user/update", new UpdateUserController());
        controller.put("/user/updateForm", new UpdateUserFormController());
        controller.put("/user/loginFailed", new LoginFailedController());
    }

    //싱글톤 방식으로 강제하는 방법
    public static RequestMapper getRequestMapper() {
        return requestMapper;
    }

    public Controller getController(String path) {
        return controller.get(path);
    }
}
