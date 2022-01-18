package fr.polytech.circus.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SequenceTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getName1() {

        Sequence sequence = new Sequence("seq1");
        assertEquals("seq1",sequence.getName());
    }

    @Test
    void setName1() {

        Sequence sequence = new Sequence("seq1");
        sequence.setName("seqTest");
        assertEquals("seqTest",sequence.getName());
    }

    @Test
    void getDuration() {}

    @Test
    void setDuration() {}

    @Test
    void getListMedias() {
    }

    @Test
    void setListMedias() {}


    @Test
    void addMedia() {}

    @Test
    void remMedia() {}

    @Test
    void testToString() {}
}
