package silentdoer.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        System.out.println("got in");
        System.out.println(request);
        System.out.println(response);
        System.out.println(request.getPathInfo() == null);
        request.setAttribute("ok", "HeHE");
        request.setAttribute("testId", "okokokId");
        request.setAttribute("count", 3);
        List<String> options = new ArrayList<>();
        options.add("aaa");
        options.add("bbb");
        request.setAttribute("options", options);
        request.getRequestDispatcher("WEB-INF/view/hello.jsp").forward(request, response);
        System.out.println("got off");
    }
}
