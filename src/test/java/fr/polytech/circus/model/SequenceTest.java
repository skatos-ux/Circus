package fr.polytech.circus.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
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
    void getDuration() {
        Sequence sequence = new Sequence("seq test");
        assertEquals(Duration.ZERO,sequence.getDuration());
    }

    /*
    @Test
    void setDuration() {
        Sequence sequence = new Sequence("seq test");
        sequence.setDuration(Duration.                                                                                                                                                                                                                              (1));
        assertEquals(Duration.ofSeconds(1),sequence.getDuration());

    }

     */

    @Test
    void getListMedias1() {

        Instant debut = Instant.parse ( "2021-11-11T12:00:00.00Z" );
        Instant fin   = Instant.parse ( "2021-11-11T15:00:00.00Z" );

        Sequence sequence = new Sequence("seq 2");
        Media media1 = new Media ( "Media1", "media/test.txt", Duration.between ( debut, fin ), TypeMedia.PICTURE, null);
        Media media2 = new Media ( "Media2", "media/test.txt", Duration.between ( debut, fin ), TypeMedia.PICTURE, null);


        List<Media> mediaList =  new ArrayList<Media>();
        mediaList.add(media1);
        mediaList.add(media2);
        sequence.setListMedias(mediaList);
        for(int i = 0 ; i < mediaList.size(); i++){

            assertEquals(mediaList.get(i),sequence.getListMedias().get(i));

        }


    }

    @Test
    void setListMedias() {

        Instant debut = Instant.parse ( "2021-11-11T12:00:00.00Z" );
        Instant fin   = Instant.parse ( "2021-11-11T15:00:00.00Z" );

        Sequence sequence = new Sequence("seq 2");
        Media media1 = new Media ( "Media1", "media/test.txt", Duration.between ( debut, fin ), TypeMedia.PICTURE, null);
        Media media2 = new Media ( "Media2", "media/test.txt", Duration.between ( debut, fin ), TypeMedia.PICTURE, null);


        List<Media> mediaList =  new ArrayList<Media>();
        sequence.setListMedias(mediaList);
        assertEquals(mediaList,sequence.getListMedias());


    }


    @Test
    void addMedia() {
        Media media1 = new Media("media test", "media/test.txt", Duration.ofSeconds(2), TypeMedia.PICTURE, null);
        Media media2 = new Media("media test", "media/test.txt", Duration.ofSeconds(2), TypeMedia.PICTURE, null);

        Sequence sequence = new Sequence("seq test");
        sequence.addMedia(media1);
        sequence.addMedia(media2);
        for(Media media : sequence.getListMedias()){

            assertEquals(true,sequence.getListMedias().contains(media));

        }

    }

    @Test
    void remMedia() {
        Media media1 = new Media("media test", "media/test.txt", Duration.ofSeconds(2), TypeMedia.PICTURE, null);

        Sequence sequence = new Sequence("seq test");
        sequence.addMedia(media1);

        sequence.remMedia(media1);
        assertEquals(false,sequence.getListMedias().contains(media1));

    }

    @Test
    void testToString() {
        Sequence sequence = new Sequence("nomSeq");

        assertEquals("nomSeq",sequence.toString());

    }
}
