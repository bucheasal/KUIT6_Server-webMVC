package jwp.dispatcher;

import jwp.controller.*;

import java.util.HashMap;
import java.util.Map;

import static core.url.UrlPath.*;

public class RequestMapper {
    private static final Map<String, Controller> controller = new HashMap<>();
    private static final RequestMapper requestMapper = new RequestMapper();
    private RequestMapper() {
        controller.put(EMPTY.route, new HomeController());
        controller.put(HOME.route, new HomeController());
        controller.put(SIGNUP.route, new CreateUserController());
        controller.put(LIST.route, new ListUserController());
        controller.put(LOGIN.route, new LoginController());
        controller.put(LOGOUT.route, new LogoutController());
        controller.put(UPDATE.route, new UpdateUserController());
        controller.put(UPDATEFORM.route, new UpdateUserFormController());
        controller.put(LOGINFAILED.route, new LoginFailedController());
    }

    public static RequestMapper getRequestMapper() {
        return requestMapper;
    }

    public Controller getController(String path) {
        return controller.get(path);
    }
}
