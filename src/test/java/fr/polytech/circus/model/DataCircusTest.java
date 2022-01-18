package fr.polytech.circus.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DataCircusTest implements Serializable {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getLieuxList() {
    }

    @Test
    void getlocationsList (){
    }

    @Test
    void setLieuxList() {
    }

    @Test
    void getMetaSequenceList() {
    }


    @Test
    void setMetaSequenceList() {
    }

    @Test
    void saveMetaSeq1(){
        DataCircus dataCircus1 = new DataCircus();
        Sequence seq1 = new Sequence("seq1");
        MetaSequence metaSequence1 = new MetaSequence("MetaSequence1");
        metaSequence1.addSequence(seq1);
        dataCircus1.saveMetaSeq(metaSequence1);
        assertEquals(true,dataCircus1.getMetaSequenceList().contains(metaSequence1));

    }
}
