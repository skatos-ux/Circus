package fr.polytech.circus.controller.PopUps;

import fr.polytech.circus.CircusApplication;
import fr.polytech.circus.controller.MetaSequenceController;
import fr.polytech.circus.model.Media;
import fr.polytech.circus.model.MetaSequence;
import fr.polytech.circus.model.Sequence;
import fr.polytech.circus.model.TypeMedia;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class addMediaPopUp {
    //******************************************************************************************************************
    // Composants UI
    //******************************************************************************************************************

    /**
     * Champ texte du nom du média à ajouter
     */
    @FXML private TextField nameNewMedia;

    /**
     *
     */
    @FXML private TextField durationField;

    /**
     *
     */
    @FXML private FileChooser fileChooserMedia;

    /**
     *
     */
    @FXML private File newFileMedia;

    /**
     * Bouton annulant l'ajout du média
     */
    @FXML private Button addMediaCancel;

    /**
     * Bouton validant l'ajout du média
     */
    @FXML private Button    addMediaSave;

    /**
     *
     */
    @FXML private Button    addMediaFile;

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

            this.fileChooserMedia = new FileChooser();
            this.fileChooserMedia.setTitle("Open file");
            this.fileChooserMedia.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );

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
        this.addMediaFile.setDisable(true);

        this.addNewMedia.setSelected(true);
        selectAddNewMedia();

        this.nameNewMedia.setOnKeyReleased  ( keyEvent   -> checkNameNewMediaFilled           () );
        this.addCopyMedia.setOnMouseClicked(mouseEvent -> selectAddCopyMedia ());
        this.addNewMedia.setOnMouseClicked(mouseEvent -> selectAddNewMedia ());
        this.addMediaCancel.setOnMouseClicked ( mouseEvent -> cancelAddMedia () );
        this.addMediaSave.setOnMouseClicked ( mouseEvent -> {
            try {
                addMediaToSeq   ();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.addMediaFile.setOnMouseClicked( mouseEvent -> selectMediaFile());


    }

    private void selectMediaFile() {
        this.newFileMedia = this.fileChooserMedia.showOpenDialog(this.popUpStage);

        if (this.newFileMedia.isFile()) {
            this.nameNewMedia.setText(this.newFileMedia.getName());
            this.addMediaSave.setDisable(false);
        }
    }

    private void checkNameNewMediaFilled() {
        if(this.addNewMedia.isSelected()) {
            if (this.nameNewMedia.getText().length() > 0) {
                this.addMediaSave.setDisable(false);
            } else {
                this.addMediaSave.setDisable(true);
            }
        }
    }

    private void selectAddNewMedia() {
        if(addCopyMedia.isSelected()){
            this.addCopyMedia.fire();
        }
        this.nameListMedias.setDisable(true);
        this.addMediaFile.setDisable(false);

        if(this.nameNewMedia.getText().length() > 0){
            this.addMediaSave.setDisable (false);
        } else {
            this.addMediaSave.setDisable(true);
        }
    }

    private void selectAddCopyMedia() {
        if(this.addNewMedia.isSelected()){
            this.addNewMedia.fire();
        }
        this.nameListMedias.setDisable(false);
        this.addMediaSave.setDisable(false);
        this.addMediaFile.setDisable(true);
    }

    private void addMediaToSeq() throws IOException {
        Alert alert = new Alert( Alert.AlertType.CONFIRMATION,
                "Etes-vous sûr de vouloir enregistrer les modifications de " + this.sequence.getName () + " ?",
                ButtonType.YES,
                ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES)
        {
            if (this.addNewMedia.isSelected()) {
                if (this.newFileMedia.exists()) {

                    Path path = Paths.get(this.newFileMedia.getPath());
                    OutputStream os = new FileOutputStream("medias\\" + this.newFileMedia.getName());
                    Files.copy(path,os);

                    Media newMedia = new Media(
                            this.nameNewMedia.getText(),
                            Duration.ofSeconds(Integer.parseInt(this.durationField.getText())),
                            TypeMedia.valueOf("PICTURE")
                    );

                    this.sequence.addMedia(newMedia);
                }
            }
            else {
                this.sequence.addMedia((Media) this.nameListMedias.getSelectionModel().getSelectedItem());
            }

            this.popUpStage.close ();
        }
    }

    private void cancelAddMedia() {
        this.popUpStage.close ();
    }
}
