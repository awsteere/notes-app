package com.awsteere.notesapp;

import com.awsteere.notesapp.pojo.Note;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * The service that adds and gets {@Notes}.
 *
 * @author Andy Steere
 */
public class NotesService {
    /*
     * UUID is thread safe.
     * See: https://stackoverflow.com/questions/7212635/is-java-util-uuid-thread-safe
     */
    private ConcurrentHashMap<UUID, Note> notes;

    public NotesService() {
        notes = new ConcurrentHashMap<UUID, Note>();
    }

    /**
     * Get all the {@Note}s or {@Note} with a body that contains the query string.
     *
     * @param query
     * @return
     */
    public List<Note> getAll(String query) {
        if (query == null) {
            return new ArrayList<Note>(notes.values());
        }
        String cleanQuery = StringEscapeUtils.unescapeHtml4(query);
        return notes.values().stream()
                .filter(note -> note.getBody().contains(cleanQuery))
                .collect(Collectors.toList());
    }

    /**
     * Return the {@Note} with the {@UUID} id. Returns null if the id is
     * not found or an invalid {@UUID}.
     *
     * @param id
     * @return
     */
    public Note find(String id) {
        try {
            UUID uuid = UUID.fromString(id.replace("/", ""));
            return notes.get(id);
        } catch (IllegalArgumentException e) {
            // Ask the PM/Lead/Architect if we need to be more specific
            // with the error message and HTTP status code.
            return null;
        }
    }

    /**
     * Add the new {@Note}.
     *
     * @param newNote
     * @return {@Note} or null if the body is invalid.
     */
    public Note add(Note newNote) {
        Note note = validateBody(newNote);
        if (note == null) {
            return null;
        }

        notes.put(note.getId(), note);
        return note;
    }

    private Note validateBody(Note newNote) {
        if (newNote.getBody().isBlank()) {
            return null;
        }

        newNote.setId(UUID.randomUUID());
        return newNote;
    }
}
