package fr.polytech.circus.model;

import fr.polytech.circus.CircusApplication;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;

class MediaTest {

    @BeforeEach
    void setUp() {
    }


    @Test
    void getName1() {
        Media media1 = new Media("nom media 1", "media/test.txt", Duration.ofSeconds(1), TypeMedia.PICTURE, null);
        assertEquals("nom media 1",media1.getName());
    }

    @Test
    void getName2() {
        Media media1 = new Media("", "media/test.txt", Duration.ofSeconds(1), TypeMedia.PICTURE, null);
        assertEquals("",media1.getName());
    }

    @Test
    void setName1() {
        Media media1 = new Media("media 1", "media/test.txt", Duration.ofSeconds(1), TypeMedia.PICTURE, null);
        media1.setName("nouveau nom");
        assertEquals("nouveau nom",media1.getName());
    }

    @Test
    void getDuration1() {
        Media media1 = new Media("media 1", "media/test.txt", Duration.ofSeconds(2), TypeMedia.PICTURE, null);
        assertEquals(Duration.ofSeconds(2),media1.getDuration());

    }

    @Test
    void getDuration2() {
        Media media1 = new Media("media 1", "media/test.txt", Duration.ZERO, TypeMedia.PICTURE, null);
        assertEquals(Duration.ZERO,media1.getDuration());

    }
    @Test
    void setDuration() {
        Media media1 = new Media("media 1", "media/test.txt", Duration.ofSeconds(2), TypeMedia.PICTURE, null);
        media1.setDuration(Duration.ZERO);
        assertEquals(Duration.ZERO,media1.getDuration());

    }
    @Test
    void getType() {
        Media media1 = new Media("media 1", "media/test.txt", Duration.ofSeconds(2), TypeMedia.PICTURE, null);
        assertEquals(TypeMedia.PICTURE,media1.getType());

    }

    @Test
    void setType() {
        Media media1 = new Media("media 1", "media/test.txt", Duration.ofSeconds(2), TypeMedia.PICTURE, null);
        media1.setType(TypeMedia.VIDEO);
        assertEquals(TypeMedia.VIDEO,media1.getType());

    }
    @Test
    void getVerr1() {
        Media media1 = new Media("media 1", "media/test.txt", Duration.ofSeconds(2), TypeMedia.PICTURE, null);
        assertEquals(true,media1.getVerr());

    }
    @Test
    void getVerr2() {
        Media media1 = new Media("media 1", "media/test.txt", Duration.ofSeconds(2), TypeMedia.PICTURE, null);
        media1.setVerr(false);
        assertEquals(false,media1.getVerr());

    }
/*
    @Test
    public void setVerr(Boolean verr) {
        Media media1 = new Media("media 1", "media/test.txt", Duration.ofSeconds(2), TypeMedia.PICTURE, null);
        media1.setVerr(true);
        assertEquals(true,media1.getVerr());
    }
*/


    @Test
    void getInterStim() {
        Media InterStim1 = new Media("media 1", "media/test.txt", Duration.ofSeconds(2), TypeMedia.PICTURE, null);

        Media media1 = new Media("media 1", "media/test.txt", Duration.ofSeconds(2), TypeMedia.PICTURE, InterStim1);

        assertEquals(InterStim1,media1.getInterStim());

    }

    @Test
    void setInterStim() {
        Media InterStim1 = new Media("media 1", "media/test.txt", Duration.ofSeconds(2), TypeMedia.PICTURE, null);
        Media media1 = new Media("media 1", "media/test.txt", Duration.ofSeconds(2), TypeMedia.PICTURE, null);
        media1.setInterStim(InterStim1);
        assertEquals(InterStim1,media1.getInterStim());

    }
}