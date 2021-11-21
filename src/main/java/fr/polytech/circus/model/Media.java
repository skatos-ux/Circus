package fr.polytech.circus.model;

import java.time.Duration;

public class Media {

    private String name;
    private Duration duration;
    private TypeMedia type;

    public Media(String name, Duration duration, TypeMedia type) {
        this.name = name;
        this.duration = duration;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public TypeMedia getType() {
        return type;
    }

    public void setType(TypeMedia type) {
        this.type = type;
    }
}
