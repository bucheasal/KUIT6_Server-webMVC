package core.url;

public enum UrlPath {
    EMPTY(""),
    HOME("/"),
    SIGNUP("/user/signup"),
    LIST("/user/list"),
    LOGIN("/user/login"),
    LOGOUT("/user/logout"),
    UPDATE("/user/update"),
    UPDATEFORM("/user/updateForm"),
    LOGINFAILED("/user/loginFailed");

    public final String route;

    UrlPath(String route) {
        this.route = route;
    }
}
