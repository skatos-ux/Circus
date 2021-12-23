package fr.polytech.circus.model;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Sequence implements Serializable {

    /**
     * Numéro de version de la classe, nécessaire pour l'interface Serializable
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private Duration duration;
    private List<Media> listMedias;

    public Sequence(String name){
        this.name = name;
        this.duration = Duration.ZERO;
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

    public void addMedia(Media media)
        {
        this.listMedias.add ( media );
        this.duration = this.duration.plus ( media.getDuration () );
        }

    public void remMedial(Media media)
        {
        if ( this.listMedias.remove ( media ) )
            {
            this.duration = this.duration.minus ( media.getDuration () );
            }
        }

    public String toString() { return name; }
}
