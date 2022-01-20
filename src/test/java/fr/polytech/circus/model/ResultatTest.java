package fr.polytech.circus.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResultatTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getNomMetaSequence() {

        MetaSequence metaSequence = new MetaSequence("metSeq1");
        assertEquals("metSeq1",metaSequence.getName());

    }
    @Test
    void setNomMetaSequence() {

        MetaSequence metaSequence = new MetaSequence();
        metaSequence.setName("metSeq1");
        assertEquals("metSeq1",metaSequence.getName());

    }
    @Test
    void getDuration() {
        MetaSequence metaSequence = new MetaSequence();
        assertEquals(Duration.ZERO,metaSequence.getDuration());
    }

    @Test
    void setDuration() {
        MetaSequence metaSequence = new MetaSequence();
        metaSequence.setDuration(Duration.ofSeconds(7));
        assertEquals(Duration.ofSeconds(7),metaSequence.getDuration());

    }

    @Test
    void getListSequences() {


    }

    @Test
    void setListSequences() {

    }


}