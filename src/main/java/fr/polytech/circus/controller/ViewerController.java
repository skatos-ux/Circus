package fr.polytech.circus.controller;

import fr.polytech.circus.CircusApplication;
import fr.polytech.circus.model.Sequence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.media.MediaView;

import java.io.IOException;

/**
 * Controleur permettant la gestion de modification d'une sequence
 */
public class ViewerController
	{
	//******************************************************************************************************************
	// Composants UI
	//******************************************************************************************************************
	@FXML private MediaView mediaView;
	//******************************************************************************************************************

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
	public ViewerController(Window owner, Sequence sequence)
	{
		FXMLLoader fxmlLoader = new FXMLLoader ( CircusApplication.class.getResource ( "views/viewer.fxml" ) );
		fxmlLoader.setController ( this );

		try
		{

			Scene dialogScene  = new Scene ( fxmlLoader.load (), 1920, 1080 );
			Stage dialog       = new Stage ();

			this.popUpStage = dialog;

			dialog.initModality ( Modality.APPLICATION_MODAL                 );
			dialog.initOwner    ( owner                                      );
			dialog.setScene     ( dialogScene                                );
			dialog.setResizable ( false                                      );
			dialog.setTitle     ( "Viewer ");

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
	 * Initialise le controller et ses attributs
	 */
	@FXML private void initialize ()
	{

	}

	/**
	 * Affiche le media donné en paramètre
	 */
	@FXML private void showMedia(Media media)
	{
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);
		mediaView.setMediaPlayer(mediaPlayer);
		// TO DO

	}

	/**
	 * Met en pause la lecture
	 */
	@FXML private void pauseViewer()
	{
		//TO DO
	}

	/**
	 *
	 */
	@FXML private void cancelAddSeq()
	{
		this.popUpStage.close();
	}

	/**
	 * Enregistre les modifications de la sequence et ferme la pop-up
	 */
	@FXML private void saveMediasToSeq()
	{

		Alert alert = new Alert( Alert.AlertType.CONFIRMATION,
		                         "Etes-vous sûr de vouloir enregistrer les modifications ?",
		                         ButtonType.YES,
		                         ButtonType.NO);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES)
			{
				this.popUpStage.close ();
			}
	}
}