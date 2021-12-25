package fr.polytech.circus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Objet permettant de contenir l'ensemble des meta sequences pour simplifier la serialisation
 */
public class DataCircus implements Serializable {

    /**
     * Numéro de version de la classe, nécessaire pour l'interface Serializable
     */
    private static final long serialVersionUID = 1L;

    /**
     * Liste des meta sequences de l'application
     */
    private List<MetaSequence> metaSequencesList;

    /**
     * Liste des lieux d'examen
     */
    private List<String> locationsList;

    /**
     * Constructeur de l'objet
     */
    public DataCircus() {
        this.metaSequencesList = new ArrayList<>();
    }

    /**
     * Retourne la liste des lieux
     * @return locationsList
     */
    public List<String> getLieuxList() {
        return locationsList;
    }

    /**
     * Modifie la liste des lieux
     * @param locationsList
     */
    public void setLieuxList(List<String> locationsList) {
        this.locationsList = locationsList;
    }

    /**
     * Ajoute un lieu a la liste des lieux
     * @param location nouveau lieu a ajouter
     */
    public void setLieuxList(String location) {
        if(!this.locationsList.contains(location)){
            this.locationsList.add(location);
        }
    }

    /**
     * Retourne la liste des meta sequences
     * @return metaSequencesList
     */
    public List<MetaSequence> getMetaSequenceList() {
        return metaSequencesList;
    }

    /**
     * Modifie la liste des meta sequences
     * @param metaSequencesList la nouvelle liste de meta sequences
     */
    public void setMetaSequenceList(List<MetaSequence> metaSequencesList) {
        this.metaSequencesList = metaSequencesList;
    }

    /**
     * Ajoute une meta sequence a la liste des meta sequences
     * @param metaSequence la nouvelle meta sequence
     */
    public void saveMetaSeq(MetaSequence metaSequence){
        this.metaSequencesList.add(metaSequence);
    }
}
