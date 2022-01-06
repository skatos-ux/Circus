package fr.polytech.circus.model;

import java.time.Duration;
import java.util.Date;
import java.util.List;

public class Resultat {

    private String nomMetaSequence;
    private Duration duration;
    private List<Sequence> listSequences;

    public String getNomMetaSequence() {
        return nomMetaSequence;
    }

    public void setNomMetaSequence(String nomMetaSequence) {
        this.nomMetaSequence = nomMetaSequence;
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

    public Resultat(String nomMetaSequence, Duration duration, List<Sequence> listSequences) {
        this.nomMetaSequence = nomMetaSequence;
        this.duration = duration;
        this.listSequences = listSequences;
    }
    public Resultat() {

    }
}