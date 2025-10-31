package core.mvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private RequestMapping requestMapping;
    private static final String REDIRECT_PREFIX = "redirect:";

    @Override
    public void init() throws ServletException {
        requestMapping = new RequestMapping();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = url.substring(contextPath.length());
        Controller controller = requestMapping.getController(path);
        if (controller == null) {
            System.out.println("No controller found for path = " + path);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            String viewName = controller.execute(req, resp);
            move(viewName, req, resp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ServletException(e.getMessage());
        }
    }

    private void move(String viewName, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (viewName.startsWith(REDIRECT_PREFIX)) {
            String target = req.getContextPath() + viewName.substring(REDIRECT_PREFIX.length());
            resp.sendRedirect(target);
            return;
        }
        RequestDispatcher rd = req.getRequestDispatcher(viewName);
        rd.forward(req, resp);
    }
}
