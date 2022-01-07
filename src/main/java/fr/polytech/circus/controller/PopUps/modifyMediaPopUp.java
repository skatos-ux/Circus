package fr.polytech.circus.controller.PopUps;

import fr.polytech.circus.CircusApplication;
import fr.polytech.circus.model.Media;
import fr.polytech.circus.model.Sequence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.time.Duration;

public class modifyMediaPopUp {
    //******************************************************************************************************************
    // Composants UI
    //******************************************************************************************************************

    /**
     * Champ texte du nouveau nom du media
     */
    @FXML private TextField newMediaNameField;

    /**
     * Champ texte de la nouvelle durée
     */
    @FXML private TextField newMediaDurationField;

    /**
     * Bouton supprimant le media
     */
    @FXML private Button modifyMediaDelete;

    /**
     * Bouton annulant la modification de la meta sequence
     */
    @FXML private Button modifyMediaCancel;

    /**
     * Bouton sauvegardant la modification de la meta sequence
     */
    @FXML private Button modifyMediaSave;

    //******************************************************************************************************************

    //******************************************************************************************************************
    // Gestionnaires medias
    //******************************************************************************************************************

    /**
     * Sequence à laquelle appartient le media
     */
    private Sequence sequence = null;

    /**
     * Media à modifier
     */
    private Media media = null;

    /**
     * Pop up de modification
     */
    private Stage popUpStage = null;

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
     * @param owner Fenetre principale
     * @param media Media à modifier
     */
    public modifyMediaPopUp(Window owner, Sequence sequence, Media media) {

        FXMLLoader fxmlLoader = new FXMLLoader ( CircusApplication.class.getResource ( "views/popups/modify_media_popup.fxml" ) );
        fxmlLoader.setController ( this );

        try
        {
            this.media = media;
            this.sequence = sequence;

            Scene dialogScene  = new Scene ( fxmlLoader.load ());
            Stage dialog       = new Stage ();

            this.popUpStage = dialog;

            dialog.initModality ( Modality.APPLICATION_MODAL                 );
            dialog.initOwner    ( owner                                      );
            dialog.setScene     ( dialogScene                                );
            dialog.setResizable ( false                                      );
            dialog.setTitle     ( "Modifier le média " + this.media.getName () );

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
        this.newMediaNameField.setText (this.media.getName());
        this.newMediaDurationField.setText (( Long.toString(this.media.getDuration().getSeconds()) ));

        this.modifyMediaSave.setDisable (true);

        this.newMediaNameField.setOnKeyReleased (keyEvent->checkMediaNameAndDuration());
        this.newMediaDurationField.setOnKeyReleased (keyEvent->checkMediaNameAndDuration());
        this.modifyMediaDelete.setOnMouseClicked (mouseEvent->deleteMedia());
        this.modifyMediaCancel.setOnMouseClicked (mouseEvent->cancelModificationsMedia());
        this.modifyMediaSave.setOnMouseClicked (mouseEvent->saveModificationsMedia());
    }

    private void checkMediaNameAndDuration() {
        if (this.newMediaNameField.getText() != this.media.getName()
                || this.newMediaDurationField.getText() != this.media.getDuration().toString()) {
            this.modifyMediaSave.setDisable(false);
        }
        else {
            this.modifyMediaSave.setDisable(true);
        }
    }

    private void saveModificationsMedia() {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Etes-vous sûr de vouloir enregistrer les modifications de " + this.media.getName () + " ?",
                ButtonType.YES,
                ButtonType.NO
        );

        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES)
        {
            try {
                this.media.setName(this.newMediaNameField.getText());
                this.media.setDuration(Duration.ofSeconds(Integer.parseInt(this.newMediaDurationField.getText())));
                this.popUpStage.close ();
            }
            catch (Exception e) {
                this.newMediaDurationField.setText("Incorrect duration value");
            }
        }
    }

    private void deleteMedia() {
        Alert alert = new Alert(
                Alert.AlertType.WARNING,
                "Etes-vous sûr de vouloir supprimer " + this.media.getName () + " ?",
                ButtonType.YES,
                ButtonType.NO
        );

        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES)
        {
            this.sequence.remMedia(this.media);
            this.popUpStage.close ();
        }
    }

    private void cancelModificationsMedia() {
        this.popUpStage.close ();
    }
}
