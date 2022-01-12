package fr.polytech.circus.model;

import java.io.Serializable;
import java.time.Duration;

/**
 * Objet permettant de représenter un media (image ou video)
 */
public class Media implements Serializable {

    /**
     * Numéro de version de la classe, nécessaire pour l'interface Serializable
     */
    private static final long serialVersionUID = 1L;

    /**
     * Nom du media
     */
    private String name;

    /**
     * Duree d'affichage du media
     */

    private Duration duration;

    /**
     * Type du media (image ou video)
     */
    private TypeMedia type;

    /**
     * Interstim apres chaque media
     */
    private Media interStim;

    /**
     * Booleen verrouille
     */
    private Boolean verr;

    /**
     * Constructeur de l'objet Media
     * @param name Nom du media
     * @param duration Duree du media
     * @param type Type du media
     */
    public Media(String name, Duration duration, TypeMedia type, Media interStim) {
        this.name = name;
        this.duration = duration;
        this.type = type;
        this.interStim = interStim;
        this.verr = true;
    }

    /**
     * Constructeur de l'objet Media par copie
     * @param media media a copier
     */
    public Media(Media media) {
        this.name = media.getName();
        this.duration = media.getDuration();
        this.type = media.getType();
        this.interStim = media.getInterStim();
    }

    /**
     * Retourne le nom du media
     * @return name le nom du media
     */
    public String getName() {
        return name;
    }

    /**
     * Modifie le nom du media
     * @param name le nouveau nom du media
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne la duree d'affichage du media
     * @return duration la duree d'affichage du media
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Modifie la duree d'affichage du media
     * @param duration la duree d'afichage du media
     */
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    /**
     * Retourne le type du media
     * @return type le type du media
     */
    public TypeMedia getType() {
        return type;
    }

    /**
     * Modifie le type du media
     * @param type le nouveau type du media
     */
    public void setType(TypeMedia type) {
        this.type = type;
    }

    /**
     * Retourne l'interStim apres le media
     * @return Media l'interStim
     */
    public Media getInterStim() {
        return interStim;
    }

    /**
     * Modifie l'interStim
     * @param interStim le nouvel interStim
     */
    public void setInterStim(Media interStim) {
        this.interStim = interStim;
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
