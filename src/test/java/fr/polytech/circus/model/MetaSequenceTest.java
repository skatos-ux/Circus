package fr.polytech.circus.model;

import fr.polytech.circus.CircusApplication;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MetaSequenceTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getName1() {
        MetaSequence metaSequence = new MetaSequence();
        assertEquals("MetaSequence",metaSequence.getName());

    }

    @Test
    void setName2() {
        MetaSequence metaSequence = new MetaSequence();
        metaSequence.setName("meta seq test");
        assertEquals("meta seq test",metaSequence.getName());

    }

    @Test
    void getDuration() {
        MetaSequence metaSequence = new MetaSequence();
        assertEquals(Duration.ZERO,metaSequence.getDuration());
    }

    @Test
    void setDuration() {
        MetaSequence metaSequence = new MetaSequence();
        metaSequence.setDuration(Duration.ofSeconds(2));
        assertEquals(Duration.ofSeconds(2),metaSequence.getDuration());

    }

    @Test
    void getListSequences1() {

        MetaSequence metaSequence = new MetaSequence();
        List<Sequence> sequences = new ArrayList<>();

        Sequence sequence1 = new Sequence("seq 1");
        Sequence sequence2 = new Sequence("seq 2");

        sequences.add(sequence1);
        sequences.add(sequence2);

        metaSequence.setListSequences(sequences);
        assertEquals(sequences,metaSequence.getListSequences());

    }

    @Test
    void setListSequences1() {

        MetaSequence metaSequence = new MetaSequence();
        List<Sequence> sequences = new ArrayList<>();

        Sequence sequence1 = new Sequence("seq 1");
        Sequence sequence2 = new Sequence("seq 2");

        sequences.add(sequence1);
        sequences.add(sequence2);

        metaSequence.setListSequences(sequences);
        assertEquals(sequences,metaSequence.getListSequences());

    }

    @Test
    void testEquals() {
        MetaSequence metaSequence1 = new MetaSequence("metaSeq1");

        MetaSequence metaSequence2 = new MetaSequence("metaSeq1");

        Assert.assertEquals(true,metaSequence2.equals(metaSequence1));
        
           }

    @Test
    void testToString() {
        MetaSequence metaSequence1 = new MetaSequence("metaSeq1");
        Assert.assertEquals("metaSeq1",metaSequence1.toString());

    }
    @Test
    void testToString2() {
        MetaSequence metaSequence1 = new MetaSequence("");
        Assert.assertEquals("",metaSequence1.toString());

    }

    @Test
    void addSequence() {

        Sequence sequence1 = new Sequence("seq 1");
        sequence1.setDuration(Duration.ofSeconds(1));

        MetaSequence metaSequence = new MetaSequence("metaSeq 1");
        metaSequence.setDuration(Duration.ofSeconds(2));
        metaSequence.addSequence(sequence1);
        Assert.assertEquals(true,metaSequence.getListSequences().contains(sequence1));

    }

    @Test
    void remSequence() {
        Sequence sequence1 = new Sequence("seq 1");
        sequence1.setDuration(Duration.ofSeconds(1));

        MetaSequence metaSequence = new MetaSequence("metaSeq 1");
        metaSequence.setDuration(Duration.ofSeconds(2));
        metaSequence.addSequence(sequence1);
        metaSequence.remSequence(sequence1);
        Assert.assertEquals(false,metaSequence.getListSequences().contains(sequence1));
    }
}