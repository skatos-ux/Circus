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
     * Constructeur de l'objet Media
     * @param name Nom du media
     * @param duration Duree du media
     * @param type Type du media
     */
    public Media(String name, Duration duration, TypeMedia type) {
        this.name = name;
        this.duration = duration;
        this.type = type;
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
}
