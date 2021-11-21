package fr.polytech.circus.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MetaSequence {

    /**
     * Numéro de version de la classe, nécessaire pour l'interface Serializable
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private Duration duration;
    private List<Sequence> listSequences;

    public MetaSequence(String name, Duration duration) {
        this.name = name;
        this.duration = duration;
        this.listSequences = new ArrayList<>();
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

    public List<Sequence> getListSequences() {
        return listSequences;
    }

    public void setListSequences(List<Sequence> listSequences) {
        this.listSequences = listSequences;
    }
}
