package fr.polytech.circus.model;

import com.itextpdf.text.Meta;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationText;
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
    void getLieuxList1() {
        DataCircus dataCircus1 = new DataCircus();
        List<String> lieuxList1 = new ArrayList<>();
        lieuxList1.add("lieu1");
        lieuxList1.add("lieu1");
        dataCircus1.setLieuxList(lieuxList1);
        assertEquals(lieuxList1,dataCircus1.getLieuxList());

    }
    @Test
    void setLieuxList() {
        DataCircus dataCircus1 = new DataCircus();
        List<String> lieuxList = new ArrayList<>();
        lieuxList.add("lieu");
        dataCircus1.setLieuxList(lieuxList);
        assertEquals(lieuxList,dataCircus1.getLieuxList());

    }
    @Test
    void getMetaSequenceList() {
        MetaSequence metaSequence1 = new MetaSequence("metaSeq1");
        MetaSequence metaSequence2 = new MetaSequence("metaSeq2");
        List<MetaSequence> metaSequences = new ArrayList<>();
        metaSequences.add(metaSequence1);
        metaSequences.add(metaSequence2);
        DataCircus dataCircus = new DataCircus();
        dataCircus.setMetaSequenceList(metaSequences);
        assertEquals(metaSequences,dataCircus.getMetaSequenceList());

    }

    @Test
    void setLieuxList2(){
        DataCircus dataCircus1 = new DataCircus();
        List<String> lieuxList = new ArrayList<>();
        dataCircus1.setLieuxList(lieuxList);
        dataCircus1.setLieuxList("lieu2");

        assertEquals(true,dataCircus1.getLieuxList().contains("lieu2"));


    }

    @Test
    void setMetaSequenceList() {
        MetaSequence metaSequence1 = new MetaSequence("metaSeq1");
        MetaSequence metaSequence2 = new MetaSequence("metaSeq2");
        List<MetaSequence> metaSequences = new ArrayList<>();
        metaSequences.add(metaSequence1);
        metaSequences.add(metaSequence2);
        DataCircus dataCircus = new DataCircus();
        dataCircus.setMetaSequenceList(metaSequences);
        assertEquals(metaSequences,dataCircus.getMetaSequenceList());
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
