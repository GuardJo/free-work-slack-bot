package com.guardjo.freeworkslackbot.domain;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.Objects;

@Document
@Getter
@Setter
@ToString
public class Worker {
    @MongoId
    private ObjectId id;

    @Indexed
    private String name;
    private Date workStartTime;
    private Date workFinishedTime;
    private float todayWorkTime;
    private float weeklyWorkTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Worker worker)) return false;
        return Objects.equals(id, worker.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    protected Worker() {

    }

    private Worker(String name) {
        this.name = name;
    }

    public static Worker of(String name) {
        return new Worker(name);
    }
}
