package com.guardjo.freeworkslackbot.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Objects;

@Data
@ToString
public class UpdateWorker {
    private String workerName;

    protected UpdateWorker(){

    }
    private UpdateWorker(String workerName) {
        this.workerName = workerName;
    }

    public static UpdateWorker of(String workerName) {
        return new UpdateWorker(workerName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateWorker that)) return false;
        return Objects.equals(workerName, that.workerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workerName);
    }
}
