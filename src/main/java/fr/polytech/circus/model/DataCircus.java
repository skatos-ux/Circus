package fr.polytech.circus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataCircus implements Serializable {

    /**
     * Numéro de version de la classe, nécessaire pour l'interface Serializable
     */
    private static final long serialVersionUID = 1L;
    private List<MetaSequence> metaSequenceList;

    public DataCircus() {
        this.metaSequenceList = new ArrayList<>();
    }

    public List<MetaSequence> getMetaSequenceList() {
        return metaSequenceList;
    }

    public void setMetaSequenceList(List<MetaSequence> metaSequenceList) {
        this.metaSequenceList = metaSequenceList;
    }

    public void saveMetaSeq(MetaSequence metaSequence){
        this.metaSequenceList.add(metaSequence);
    }
}
