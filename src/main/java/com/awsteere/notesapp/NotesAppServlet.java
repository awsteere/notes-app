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
 * TODO:
 * 1. Document classes and pom.xml
 * 2. Use JAX-RS and annotations
 * convert to gradle
 * integration tests
 * handle 127.0.0.1:8080/ - error.html
 * -- https://wiki.eclipse.org/Jetty/Howto/Custom_Error_Pages#Define_error_pages_in_web.xml
 * logging
 * Test
 * POST
 * - invalid object: No value for a name, names other than body, embedded "
 * - valid input object, creates note with unique UUID, valid response
 *
 * GET
 * - No arguments return empty json if no notes
 * - Gets all notes if there are more than 0
 * - Get id: UUID - returns note if found, else error
 * - test with extra path api/notes/{uuid}/foo/bar
 * - test multi-line body
 * - test max length body
 * - test empty and blank body
 * Scaling, multi-user, injection for testability
 */
public class NotesAppServlet extends HttpServlet {
    private static final String contentType = "application/json";
    private final NoteService noteService;
    private final ObjectMapper objectMapper;

    public NotesAppServlet() {
        super();

        noteService = new NoteService(20);
        objectMapper = new ObjectMapper();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        response.setContentType(contentType);
        response.setStatus(HttpServletResponse.SC_OK);
        String id = request.getPathInfo();
        System.out.println(String.format("id='%s'", id));
        if (id == null) {
            List<Note> notes = noteService.getAll();
            response.getWriter().println(objectMapper.writeValueAsString(notes));
        } else {
            Note note = getNote(id);
            response.getWriter().println(objectMapper.writeValueAsString(note));
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType(contentType);
        response.setStatus(HttpServletResponse.SC_OK);

        String body = getBodyAsString(request);
        System.out.println(String.format("body='%s'", body));
        Note note = postNote(body);
        response.getWriter().println(objectMapper.writeValueAsString(note));
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

    private Note postNote(String body) throws IOException {
        Note note = objectMapper.readValue(body, Note.class);
        return noteService.add(note);
    }

    private Note getNote(String id) {
        UUID uuid = UUID.fromString(id.replace("/", ""));
        return noteService.find(uuid);
    }
}
