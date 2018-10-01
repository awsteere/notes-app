package com.awsteere.notesapp;

import com.awsteere.notesapp.pojo.Note;
import com.fasterxml.jackson.core.JsonProcessingException;
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
 * <p>
 * For expediency, not using JAX-RS, Swagger, Spring or a dependency
 * injection framework and persistence were not implemented.
 * <p>
 * Note: Used IntelliJ's default formatting.
 *
 * @author Andy Steere
 */
public class NotesAppServlet extends HttpServlet {
    private static final String contentType = "application/json";
    private final NotesService notesService = new NotesService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(contentType);
        response.setStatus(HttpServletResponse.SC_OK);
        getNotes(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(contentType);
        response.setStatus(HttpServletResponse.SC_OK);

        String body = getBodyAsString(request);
        Note note = addNote(body);
        if (note == null) {
            // Would like to return 422 (Unprocessable Entity) but not available w/o additional work.
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        response.getWriter().println(prettyPrint(note));
    }

    /**
     * Get one or more notes based on the URL.
     * 1. If an id was specified /notes/:id, return the note.
     * 2. If no id was specified and the parameter query was passed, find notes which contain the
     *    parameter string value.
     * 3. If no id and query parameter, return all notes.
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void getNotes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getPathInfo();
        if (id == null) {
            String query = request.getParameter("query");
            List<Note> notes = notesService.getAll(query);
            response.getWriter().println(objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(notes));
        } else {
            Note note = notesService.find(id);
            if (note == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                response.getWriter().println(prettyPrint(note));
            }
        }
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

    private String prettyPrint(Note note) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(note);
    }
}
