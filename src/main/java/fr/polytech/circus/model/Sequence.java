package fr.polytech.circus.model;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Objet permettant de représenter une sequence
 */
public class Sequence implements Serializable {

    /**
     * Numéro de version de la classe, nécessaire pour l'interface Serializable
     */
    private static final long serialVersionUID = 1L;

    /**
     * Nom de la sequence
     */
    private String name;

    /**
     * Duree de la sequence
     */
    private Duration duration;

    /**
     * Liste des medias de la sequence
     */
    private List<Media> listMedias;

    /**
     * Booleen verrouille
     */
    private Boolean verr;

    /**
     * Constructeur de l'objet sequence
     * @param name nom de la sequence
     */
    public Sequence(String name){
        this.name = name;
        this.duration = Duration.ZERO;
        this.listMedias = new ArrayList<>();
        this.verr = true;
    }

    /**
     * Constructeur de l'objet sequence par copie
     * @param sequence Sequence a copier
     */
    public Sequence(Sequence sequence){
        this.name = sequence.getName();
        this.duration = sequence.getDuration();
        this.listMedias = sequence.getListMedias();
        this.verr = sequence.getVerr();
    }

    /**
     * Retourne le nom de la sequence
     * @return name le nom
     */
    public String getName() {
        return name;
    }

    /**
     * Modifie le nom de la sequence
     * @param name le nouveau nom
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne la duree de la sequence
     * @return duration la duree
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Modifie la duree de la sequence
     * @param duration la nouvelle duree
     */
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    /**
     * Retourne la liste des medias de la sequence
     * @return listMedias la liste des medias
     */
    public List<Media> getListMedias() {
        return listMedias;
    }

    /**
     * Modifie la liste des medias de la sequence
     * @param listMedias la nouvelle liste des medias
     */
    public void setListMedias(List<Media> listMedias) {
        this.listMedias = listMedias;
    }

    /**
     * Ajoute un media a la liste des medias de la sequence
     * @param media le media a ajouter
     */
    public void addMedia(Media media)
        {
        this.listMedias.add ( media );
        this.duration = this.duration.plus ( media.getDuration () );
        }

    /**
     * Supprime un media a la liste des medias de la sequence
     * @param media le media a supprimer
     */
    public void remMedia(Media media)
        {
        if ( this.listMedias.remove ( media ) )
            {
            this.duration = this.duration.minus ( media.getDuration () );
            }
        }

    /**
     * Retourne si le media est verrouille
     * @return Boolean verr
     */
    public Boolean getVerr() {
        return verr;
    }

    /**
     * Verrouille ou deverouille le media
     * @param verr le nouvel etat de verrouillage
     */
    public void setVerr(Boolean verr) {
        this.verr = verr;
    }

    /**
     * Surcharge de la methode toString
     * @return name le nom
     */
    public String toString() { return name; }
}
