package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import templater.PageGenerator;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AllRequestsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = createPageVariablesMap(request);
        pageVariables.put("message", "");
        final String key = request.getParameter("key");
        pageVariables.put("key", key == null ? "" : key);

        response.getWriter().println(PageGenerator.instance().getPage("mirror.html", pageVariables));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = createPageVariablesMap(request);
        final String message = request.getParameter("message");
        response.setContentType("text/html;charset=utf-8");
        response.setStatus((message == null || message.isEmpty()) ?
                HttpServletResponse.SC_FORBIDDEN : HttpServletResponse.SC_OK);
        pageVariables.put("message", message == null ? "" : message);
        response.getWriter().println(PageGenerator.instance().getPage("page.html", pageVariables));
    }

    public static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();
        result.put("method", request.getMethod());
        result.put("URL", request.getRequestURL().toString());
        result.put("PathInfo", request.getPathInfo());
        result.put("sessionId", request.getSession().getId());
        result.put("parameters", request.getParameterMap().toString());
        return result;
    }
}
