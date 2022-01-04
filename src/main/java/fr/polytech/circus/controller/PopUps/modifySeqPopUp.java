package fr.polytech.circus.controller.PopUps;

import fr.polytech.circus.CircusApplication;
import fr.polytech.circus.controller.MetaSequenceController;
import fr.polytech.circus.model.MetaSequence;
import fr.polytech.circus.model.Sequence;
import fr.polytech.circus.model.Media;
import fr.polytech.circus.model.TypeMedia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.time.Duration;

/**
 * Controleur permettant la gestion de modification d'une sequence
 */
public class modifySeqPopUp
	{
	//******************************************************************************************************************
	// Composants UI
	//******************************************************************************************************************

	/**
	 * Bouton sauvegardant les modifications de la sequence
	 */
	@FXML private Button saveAddMediaSeq;

	/**
	 * Bouton fermant la pop up de modification de la sequence
	 */
	@FXML private Button cancelAddMediaSeq;

	/**
	 * Bouton ajoutant un media a la sequence
	 */
	@FXML private Button addMediaToSeq;

	/**
	 *
	 */
	@FXML private TableView<Media> mediaTable;
	//******************************************************************************************************************

	//******************************************************************************************************************
	// Gestionnaires méta-sequences
	//******************************************************************************************************************
	/**
	 * Sequence a modifier
	 */
	private Sequence sequence = null;

	/**
	 * List des sequences
	 */
	private ObservableList listMedias = null;

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
	 * @param sequence Sequence a modifier
	 */
	public modifySeqPopUp(Window owner, ObservableList<Media> listMedias, Sequence sequence) {

		FXMLLoader fxmlLoader = new FXMLLoader ( CircusApplication.class.getResource ( "views/popups/modify_seq_popup.fxml" ) );
		fxmlLoader.setController ( this );

		try
			{
			this.sequence   = sequence;
			this.listMedias = listMedias;

			Scene dialogScene  = new Scene ( fxmlLoader.load (), 1000, 500 );
			Stage dialog       = new Stage ();

			this.popUpStage = dialog;

			dialog.initModality ( Modality.APPLICATION_MODAL                 );
			dialog.initOwner    ( owner                                      );
			dialog.setScene     ( dialogScene                                );
			dialog.setResizable ( false                                      );
			dialog.setTitle     ( "Modifier la Séquence " + this.sequence.getName () );

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

		// TO DO : Afficher les valeurs dans les bonnes colonnes
		this.mediaTable.setItems(FXCollections.observableList( this.sequence.getListMedias()));

		this.cancelAddMediaSeq.setOnMouseClicked ( mouseEvent -> cancelAddSeq () );
		this.saveAddMediaSeq.setOnMouseClicked ( mouseEvent -> saveMediasToSeq () );
		this.addMediaToSeq.setOnMouseClicked ( mouseEvent -> addMediaToSeq () );
	}

	/**
	 * Ajoute un media a la sequence
	 */
	@FXML private void addMediaToSeq() {
		new addMediaPopUp(
				this.saveAddMediaSeq.getScene().getWindow(),
				FXCollections.observableList (this.sequence.getListMedias()),
				this.sequence
		);
	}

	/**
	 * Ferme la pop up de modification de la sequence
	 */
	@FXML private void cancelAddSeq() {
		this.popUpStage.close ();
	}

	/**
	 * Enregistre les modifications de la sequence et ferme la pop up
	 */
	@FXML private void saveMediasToSeq() {

		Alert alert = new Alert( Alert.AlertType.CONFIRMATION,
		                         "Etes-vous sûr de vouloir enregistrer les modifications de " + this.sequence.getName () + " ?",
		                         ButtonType.YES,
		                         ButtonType.NO);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES)
			{
				this.popUpStage.close ();
			}
	}
}
