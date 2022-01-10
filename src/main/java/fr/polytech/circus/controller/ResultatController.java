package fr.polytech.circus.controller;

import fr.polytech.circus.CircusApplication;
import fr.polytech.circus.model.MetaSequence;
import fr.polytech.circus.model.Resultat;
import fr.polytech.circus.model.Sequence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;
import java.time.Duration;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ResultatController  implements  Initializable {

    @FXML private TableView<Resultat> tableResultat;
    @FXML private TableColumn<Resultat,String> nomMetaSequence;
    @FXML private TableColumn<Resultat,Duration> duration;
    @FXML private TableColumn<Resultat, List<Sequence>> listSequences;

    public List<Resultat> initializeResultats(List<MetaSequence> metaSequences){
        List<Resultat> resultats = new ArrayList<> ();
        for (MetaSequence metaSequence : metaSequences){
            Resultat resultat = new Resultat();
            resultat.setNomMetaSequence(metaSequence.getName());
            resultat.setDuration(metaSequence.getDuration());
            resultat.setListSequences(metaSequence.getListSequences());
            resultats.add ( resultat );
        }
        return resultats ;
    }


    private List<MetaSequence> initMetasequenses(){
        List<MetaSequence>  metaSequences = new ArrayList<MetaSequence>() ;
        metaSequences.addAll(CircusApplication.dataCircus.getMetaSequenceList());
        return metaSequences;

    }
    public ObservableList<Resultat> list = FXCollections.observableArrayList(
            new Resultat(initMetasequenses().get(0).getName(),initMetasequenses().get(0).getDuration(),initMetasequenses().get(0).getListSequences())
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        nomMetaSequence.setCellValueFactory(new PropertyValueFactory<Resultat,String>("nomMetaSequence"));
        duration.setCellValueFactory(new PropertyValueFactory<Resultat,Duration>("duration"));
        listSequences.setCellValueFactory(new PropertyValueFactory<Resultat,List<Sequence>>("listSequences"));
        tableResultat.setItems ( list  );

    }



}
