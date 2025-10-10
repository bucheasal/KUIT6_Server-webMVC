package core.url;

public enum Method {
    REDIRECT("redirect:"),
    FORWARD("");
    public final String method;

    Method(String method) {
        this.method = method;
    }
}
