package fr.polytech.circus.controller.PopUps;

import fr.polytech.circus.CircusApplication;
import fr.polytech.circus.controller.MetaSequenceController;
import fr.polytech.circus.model.Media;
import fr.polytech.circus.model.MetaSequence;
import fr.polytech.circus.model.Sequence;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class addMediaPopUp {
    //******************************************************************************************************************
    // Composants UI
    //******************************************************************************************************************

    /**
     * Champ texte du nom du média à ajouter
     */
    @FXML
    private TextField nameNewMedia;

    /**
     * Bouton annulant l'ajout du média
     */
    @FXML private Button addMediaCancel;

    /**
     * Bouton validant l'ajout du média
     */
    @FXML private Button    addMediaSave;

    /**
     * Bouton radio selectionnant l'ajout d'un nouveau média
     */
    @FXML private RadioButton addNewMedia;

    /**
     * Bouton radio selectionnant la copie d'un média existant
     */
    @FXML private RadioButton addCopyMedia;

    /**
     * ComboBox representant l'ensemble des médias
     */
    @FXML private ComboBox nameListMedias;
    //******************************************************************************************************************

    //******************************************************************************************************************
    // Gestionnaires sequences
    //******************************************************************************************************************

    /**
     * Liste des medias
     */
    private ObservableList listMedias = null;

    /**
     * Sequence à laquelle sera ajouté le média
     */
    private Sequence sequence = null;

    /**
     * Pop up ajout de média
     */
    private Stage popUpStage = null;

    /**
     * Listener de l'ajout du média
     */
    private MetaSequenceController.ModificationListener addListener = null;
    //******************************************************************************************************************

    //******************************************************************************************************************
    //   ###    ###   #   #   ####  #####  ####   #   #   ###   #####   ###   ####    ####
    //  #   #  #   #  ##  #  #        #    #   #  #   #  #   #    #    #   #  #   #  #
    //  #      #   #  # # #   ###     #    ####   #   #  #        #    #   #  ####    ###
    //  #   #  #   #  #  ##      #    #    #   #  #   #  #   #    #    #   #  #   #      #
    //   ###    ###   #   #  ####     #    #   #   ###    ###     #     ###   #   #  ####
    //******************************************************************************************************************

    /**
     * Constructeur du controleur
     * @param owner la fenetre principale
     * @param listMedias la liste des médias
     * @param sequence la sequence a laquelle on ajoute le média
     */
    public addMediaPopUp(Window owner, ObservableList<Media> listMedias, Sequence sequence)
    {

        FXMLLoader fxmlLoader = new FXMLLoader ( CircusApplication.class.getResource ( "views/popups/add_media_popup.fxml" ) );
        fxmlLoader.setController ( this );

        try
        {
            this.sequence   = sequence;
            this.listMedias = listMedias;

            Scene dialogScene  = new Scene ( fxmlLoader.load (), 500, 100 );
            Stage dialog       = new Stage ();

            this.popUpStage = dialog;

            dialog.initModality ( Modality.APPLICATION_MODAL                 );
            dialog.initOwner    ( owner                                      );
            dialog.setScene     ( dialogScene                                );
            dialog.setResizable ( false                                      );
            dialog.setTitle     ( "Ajout Media à " + this.sequence.getName () );

            dialog.show();
        }
        catch ( IOException e )
        {
            e.printStackTrace ();
        }
    }

    //******************************************************************************************************************
    //      #  #####  #   #         #####  #   #  #   #   ###   #####  #   ###   #   #   ####
    //      #  #       # #          #      #   #  ##  #  #   #    #    #  #   #  ##  #  #
    //      #  ###      #           ###    #   #  # # #  #        #    #  #   #  # # #   ###
    //  #   #  #       # #          #      #   #  #  ##  #   #    #    #  #   #  #  ##      #
    //   ###   #      #   #         #       ###   #   #   ###     #    #   ###   #   #  ####
    //******************************************************************************************************************


    /**
     * Initialise le controleur et ses attributs, ajoute des controleurs a chaque composant
     */
    @FXML private void initialize () {
        this.nameListMedias.setItems(listMedias);
        this.nameListMedias.getSelectionModel ().select ( 0 );
        this.nameListMedias.setDisable(true);
        this.addMediaSave.setDisable (true);

        this.nameNewMedia.setOnKeyReleased  ( keyEvent   -> checkNameNewMediaFilled           () );
        this.addCopyMedia.setOnMouseClicked(mouseEvent -> selectAddCopyMedia ());
        this.addNewMedia.setOnMouseClicked(mouseEvent -> selectAddNewMedia ());
        this.addMediaCancel.setOnMouseClicked ( mouseEvent -> cancelAddMedia () );
        this.addMediaSave.setOnMouseClicked ( mouseEvent -> addMediaToSeq   () );
    }

    private void checkNameNewMediaFilled() {
    }

    private void selectAddNewMedia() {
    }

    private void selectAddCopyMedia() {
    }

    private void addMediaToSeq() {
    }

    private void cancelAddMedia() {
    }
}