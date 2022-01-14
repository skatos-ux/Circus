package fr.polytech.circus.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

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
    void getDuration() {
    }

    @Test
    void setDuration() {
    }

    @Test
    void getType() {
    }

    @Test
    void setType() {
    }

    @Test
    void getInterStim() {
    }

    @Test
    void setInterStim() {
    }
}