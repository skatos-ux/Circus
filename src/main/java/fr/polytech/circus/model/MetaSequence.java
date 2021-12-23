package fr.polytech.circus.model;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MetaSequence implements Serializable {

    /**
     * Numéro de version de la classe, nécessaire pour l'interface Serializable
     */
    private static final long serialVersionUID = 1L;
    private String         name;
    private Duration       duration;
    private List<Sequence> listSequences;

    public MetaSequence()
        {
        this.name = "MetaSequence";
        this.duration = Duration.ZERO;
        }

    public MetaSequence(String name) {
        this.name          = name;
        this.duration      = Duration.ZERO;
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

    public boolean equals ( Object object )
        {
        if ( object instanceof MetaSequence )
            {
            return this.name.toUpperCase().equals ( (( MetaSequence ) object).name.toUpperCase () );
            }
        if ( object instanceof String )
            {
            return this.name.toUpperCase().equals ( ( ( String ) object ).toUpperCase () );
            }
        return false;
        }

    public String toString() { return name; }

    public void addSequence(Sequence sequence)
        {
        this.listSequences.add ( sequence );
        this.duration = this.duration.plus ( sequence.getDuration ());
        }
    public void remSequence(Sequence sequence)
        {
        if ( this.listSequences.remove ( sequence ) )
            {
            this.duration = this.duration.minus ( sequence.getDuration () );
            }
        }
}
