package fr.polytech.circus.model;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Objet permettant de représenter une meta sequence
 */
public class MetaSequence implements Serializable {

    /**
     * Numéro de version de la classe, nécessaire pour l'interface Serializable
     */
    private static final long serialVersionUID = 1L;

    /**
     * Nom de la meta sequence
     */
    private String         name;

    /**
     * Duree de la meta sequence
     */
    private Duration       duration;

    /**
     * Liste des sequences contenues dans la meta sequence
     */
    private List<Sequence> listSequences;

    /**
     * Constructeur par défaut de l'objet meta sequence
     */
    public MetaSequence()
        {
        this.name = "MetaSequence";
        this.duration = Duration.ZERO;
        }

    /**
     * Constructeur avec parametre de l'objet meta sequence
     * @param name nom de la meta sequence
     */
    public MetaSequence(String name) {
        this.name          = name;
        this.duration      = Duration.ZERO;
        this.listSequences = new ArrayList<>();
    }

    /**
     * Retourne le nom de la meta sequence
     * @return name le nom
     */
    public String getName() {
        return name;
    }

    /**
     * Modifie le nom de la meta sequence
     * @param name le nouveau nom
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne la duree de la meta sequence
     * @return duration la duree
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Modifie la duree de la meta sequence
     * @param duration la nouvelle duree
     */
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    /**
     * Retourne la liste des sequences associees a la meta sequence
     * @return listSequences la liste des sequences
     */
    public List<Sequence> getListSequences() {
        return listSequences;
    }

    /**
     * Modifie la liste des sequences
     * @param listSequences  la nouvelle liste des sequences
     */
    public void setListSequences(List<Sequence> listSequences) {
        this.listSequences = listSequences;
    }

    /**
     * Verifie si deux meta sequences sont egales
     * @param object la meta sequence a comparer
     * @return boolean vrai si egal faux sinon
     */
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

    /**
     * Surcharge de la methode toString
     * @return name le nom
     */
    public String toString() { return name; }

    /**
     * Ajoute une sequence a la meta sequence et modifie en consequence la duree
     * @param sequence la sequence a ajouter
     */
    public void addSequence(Sequence sequence)
        {
        this.listSequences.add ( sequence );
        this.duration = this.duration.plus ( sequence.getDuration ());
        }

    /**
     * Supprime une sequence de la meta sequence et modifie en consequence la duree
     * @param sequence la sequence a supprimer
     */
    public void remSequence(Sequence sequence)
        {
        if ( this.listSequences.remove ( sequence ) )
            {
            this.duration = this.duration.minus ( sequence.getDuration () );
            }
        }
}
