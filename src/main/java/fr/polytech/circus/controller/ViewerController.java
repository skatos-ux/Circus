package fr.polytech.circus.controller;

import fr.polytech.circus.CircusApplication;
import fr.polytech.circus.model.MetaSequence;
import fr.polytech.circus.model.Sequence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.media.MediaView;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.EventListener;

/**
 * Controleur permettant la gestion de modification d'une sequence
 */
public class ViewerController
	{
	//******************************************************************************************************************
	// Composants UI
	//******************************************************************************************************************
	@FXML private MediaView mediaView;
	@FXML private ImageView imageView;
	private MediaPlayer mediaPlayer;
	//******************************************************************************************************************

	/**
	 * Stage du viewer
	 */
	private Stage viewerStage = null;
	//******************************************************************************************************************
	// Commit comment
	private MetaSequence playingMetaSequence;
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
	 */
	public ViewerController( Window owner, MetaSequence metaSequence )
	{
		FXMLLoader fxmlLoader = new FXMLLoader ( CircusApplication.class.getResource ( "views/viewer.fxml" ) );
		fxmlLoader.setController ( this );

		try
		{

			Scene dialogScene  = new Scene ( fxmlLoader.load (), 1600, 900 );
			Stage dialog       = new Stage ();

			this.viewerStage = dialog;

			dialog.initModality ( Modality.NONE                              );
			dialog.initOwner    ( owner                                      );
			dialog.setScene     ( dialogScene                                );
			dialog.setResizable ( true                                       );
			dialog.setTitle     ( "Viewer");

			dialog.show();
		}
		catch ( IOException e )
		{
			e.printStackTrace ();
		}

		this.playingMetaSequence = metaSequence;
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
		mediaView = new MediaView();
		imageView = new ImageView();
	}

	/**
	 * Affiche le media donné en paramètre
	 */
	@FXML private void showMedia(Media media)
	{
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);
		mediaView.setMediaPlayer(mediaPlayer);
	}

	/**
	 * Affiche l'image donnée en paramètre
	 */
	@FXML private void showImage(Image image)
	{
		imageView.setImage(image);
		imageView.setCache(true);
	}

	/**
	 * Retire l'image affichée
	 */
	@FXML private void removeImage()
	{
		imageView.setImage(null);
	}

	/**
	 * Fonction de test de lecture de média
	 */
	@FXML private void testMedia() throws MalformedURLException
	{
		File mediaFile = new File("D:/Codezone/POLYTECH/DI5/SI/CIRCUS/medias/media-europark.png");
		Media media = new Media(mediaFile.toURI().toURL().toString());
		showMedia(media);
	}

	/**
	 * Fonction de test d'affichage d'image
	 */
	@FXML private void testImage() throws FileNotFoundException, URISyntaxException {
//		InputStream stream = new FileInputStream("C:/Users/Loris/Downloads/test.png");
		Image image = new Image(getClass().getResource("truc.jpg").toURI().toString());
		showImage(image);
	}

	/**
	 * Met en pause la lecture
	 */
	@FXML public void pauseViewer()
	{
		mediaPlayer.pause();
	}

	/**
	 * Démarre la lecture
	 */
	@FXML public void playViewer()
	{
		mediaPlayer.play();
	}

	/**
	 * Quitte le viewer
	 */
	@FXML private void quitViewer()
	{
		this.viewerStage.close();
	}

	}
