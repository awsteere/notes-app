package com.awsteere.notesapp;

import com.awsteere.notesapp.pojo.Note;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class NoteService {
    private final int maxLength;

    /* UUID is thread safe see: https://stackoverflow.com/questions/7212635/is-java-util-uuid-thread-safe */
    private ConcurrentHashMap<UUID, Note> notes;

    public NoteService(int maxLength) {
        this.maxLength = maxLength;
        notes = new ConcurrentHashMap<UUID, Note>();
    }

    public synchronized Note find(UUID id) {
        return notes.get(id);
    }

    public Note add(Note body) {
        Note note = validateBody(body);
        if (note == null) {
            return null;
        }

        notes.put(note.getId(), note);
        return note;
    }

    Note validateBody(Note newNote) {
        if (newNote.getBody().isBlank() || newNote.getBody().length() > maxLength) {
            return null;
        }

        newNote.setId(UUID.randomUUID());
        return newNote;
    }

    public List<Note> getAll(String query) {
        if (query == null) {
            return new ArrayList<Note>(notes.values());
        }

        return notes.values().stream()
                .filter(note -> note.getBody().contains(query))
                .collect(Collectors.toList());
    }
}
