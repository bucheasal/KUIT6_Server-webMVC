
package jwp.dispatcher;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import jwp.controller.Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    private RequestMapper requestMapper;

    @Override
    public void init() throws ServletException {
        this.requestMapper = RequestMapper.getRequestMapper();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ctx = req.getContextPath();
        String uri = req.getRequestURI();
        String path = uri.substring(ctx.length());
        System.out.println("ctx=" + ctx + ", uri=" + uri + ", path=" + path);
        Controller controller = requestMapper.getController(path);
        if (controller == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            String viewName = controller.proceed(req, resp);
            if (viewName == null)
                return;
            if (viewName.startsWith("redirect:")) {
                String target = viewName.substring("redirect:".length());
                resp.sendRedirect(ctx + target);
                return;
            } else {
                String jsp = "/" + viewName + ".jsp";
                RequestDispatcher rd = req.getRequestDispatcher(jsp);
                rd.forward(req, resp);
                return;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}
