package com.awsteere.notesapp.pojo;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.UUID;

public class Note {
    @JsonProperty(required = false)
    private UUID id;

    private String body;

    public Note() { }

    public Note(String body) {
        id = UUID.randomUUID();
        this.body = body;
    }

    @JsonGetter("id")
    public UUID getId() {
        return id;
    }

    @JsonSetter("id")
    public void setId(UUID id) {
        this.id = id;
    }

    @JsonGetter("body")
    public String getBody() {
        return body;
    }

    @JsonSetter("body")
    public void setBody(String body) {
        this.body = body;
    }
}
