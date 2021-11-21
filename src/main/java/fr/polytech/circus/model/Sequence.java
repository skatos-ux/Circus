package fr.polytech.circus.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Sequence {

    /**
     * Numéro de version de la classe, nécessaire pour l'interface Serializable
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private Duration duration;
    private List<Media> listMedias;

    public Sequence(String name, Duration duration){
        this.name = name;
        this.duration = duration;
        this.listMedias = new ArrayList<>();
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

    public List<Media> getListMedias() {
        return listMedias;
    }

    public void setListMedias(List<Media> listMedias) {
        this.listMedias = listMedias;
    }
}
