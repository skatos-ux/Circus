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
	public ViewerController(Window owner)
	{
		FXMLLoader fxmlLoader = new FXMLLoader ( CircusApplication.class.getResource ( "views/viewer.fxml" ) );
		fxmlLoader.setController ( this );

		try
		{

			Scene dialogScene  = new Scene ( fxmlLoader.load (), 1600, 900 );
			Stage dialog       = new Stage ();

			this.viewerStage = dialog;

			dialog.initModality ( Modality.APPLICATION_MODAL                 );
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
		// mediaView = new MediaView();
		// Ne pas décommenter la ligne suivante, cela remplace l'ImageView déjà mise placée dans la vue
		// imageView = new ImageView();
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
		File mediaFile = new File("C:/Users/Loris/Downloads/four.mp4");
		Media media = new Media(mediaFile.toURI().toURL().toString());
		showMedia(media);
	}

	/**
	 * Fonction de test d'affichage d'image
	 */
	@FXML private void testImage() throws FileNotFoundException, URISyntaxException {
//		InputStream stream = new FileInputStream("C:/Users/Loris/Downloads/test.png");
//		Image image = new Image(getClass().getResource("truc.jpg").toURI().toString());
		// System.out.println(getClass().getResource("./").toURI().toString());
		// OutputStream os = new FileOutputStream("medias/" + this.newFileMedia.getName());
		InputStream is = new FileInputStream("medias/test.png");
		Image image = new Image(is);
		showImage(image);
	}

	/**
	 * Met en pause la lecture
	 */
	@FXML private void pauseViewer()
	{
		mediaPlayer.pause();
	}

	/**
	 * Démarre la lecture
	 */
	@FXML private void playViewer()
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
