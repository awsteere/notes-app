package com.awsteere.notesapp;

import com.awsteere.notesapp.pojo.Note;
import com.fasterxml.jackson.databind.ObjectMapper;;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet to handle GET and POST of notes. 
 *
 * For expediency, not using JAX-RS.
 */
public class NotesAppServlet extends HttpServlet {
    private static final String contentType = "application/json";
    private final NoteService notesService;
    private final ObjectMapper objectMapper;

    public NotesAppServlet() {
        super();

        notesService = new NotesService();
        objectMapper = new ObjectMapper();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        response.setContentType(contentType);
        response.setStatus(HttpServletResponse.SC_OK);
        String id = request.getPathInfo();
        if (id == null) {
            String query = request.getParameter("query");
            List<Note> notes = notesService.getAll(query);
            response.getWriter().println(objectMapper
		.writerWithDefaultPrettyPrinter()
		.writeValueAsString(notes));
        } else {
            Note note = getNote(id);
            if (note == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                response.getWriter().println(prettyPrint(note));
            }
        }
    }

    protected void doPost(HttpServletRequest request, 
	                  HttpServletResponse response) throws IOException {
        response.setContentType(contentType);
        response.setStatus(HttpServletResponse.SC_OK);

        String body = getBodyAsString(request);
        Note note = addNote(body);
        response.getWriter().println(prettyPrint(note));
    }

    private String getBodyAsString(HttpServletRequest request) throws IOException {
        StringBuffer jb = new StringBuffer();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        }
        return jb.toString();
    }

    private Note addNote(String body) throws IOException {
        Note note = objectMapper.readValue(body, Note.class);
        return notesService.add(note);
    }

    private Note getNote(String id) {
        UUID uuid = UUID.fromString(id.replace("/", ""));
        return notesService.find(uuid);
    }

    private String prettyPrint(Note note) {
        return objectMapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(note));
    }
}
