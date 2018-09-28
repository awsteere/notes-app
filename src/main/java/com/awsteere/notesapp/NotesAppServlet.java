package com.awsteere.notesapp;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO:
 * 1. Document classes and pom.xml
 * 2. Use JAX-RS and annotations
 * convert to gradle
 * integration tests
 * handle 127.0.0.1:8080/ - error.html
 * -- https://wiki.eclipse.org/Jetty/Howto/Custom_Error_Pages#Define_error_pages_in_web.xml
 *
 */
public class NotesAppServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>Get Hello Servlet</h1>");
        response.getWriter().println("session=" + request.getSession(true).getId());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>Post Hello Servlet</h1>");
        response.getWriter().println("session=" + request.getSession(true).getId());
    }
}
