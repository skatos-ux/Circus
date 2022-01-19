package fr.polytech.circus.controller;

import fr.polytech.circus.CircusApplication;
import fr.polytech.circus.model.MetaSequence;
import fr.polytech.circus.model.Sequence;
import fr.polytech.circus.model.TypeMedia;
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
import java.util.concurrent.TimeUnit;

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
		startMetaSequence(playingMetaSequence);
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
	 * Affiche le media dont le nom est donné en paramètre
	 */
	@FXML private void showMediaFromName(String name)
	{
		File mediaFile = new File("medias/" + name);
		try
		{
			Media media = new Media(mediaFile.toURI().toURL().toString());
			showMedia(media);
		}
		// Si l'URL est malformée, on le signale
		catch (MalformedURLException error)
		{
			System.out.println("URL malformée, le chemin vers la vidéo est incorrect.");
		}

	}
	/**
	 * Retire la vidéo affichée
	 */
	@FXML private void removeMedia()
	{
		mediaView.setMediaPlayer(null);
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
	 * Affiche l'image dont le nom est donné en paramètre
	 */
	@FXML private void showImageFromName(String name)
	{
		// On essaye de créer un InputStream avec le chemin du fichier
		try
		{
			InputStream is = new FileInputStream("medias/" + name);
			Image image = new Image(is);
			showImage(image);
		}
		// Si le chemin n'est pas trouvé on le signale
		catch (FileNotFoundException error)
		{
			System.out.println("Le fichier n'existe pas.");
		}
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
		File mediaFile = new File("C:/Users/Loris/Download/four.mp4");
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
	 * Affiche la méta-séquence donnée en paramètre
	 */
	@FXML private void startMetaSequence(MetaSequence metaSequence)
	{
		// Pour chaque séquence de la méta séquence
		for (Sequence sequence : metaSequence.getListSequences())
		{
			// Pour chaque Média de la séquence
			for (fr.polytech.circus.model.Media media : sequence.getListMedias())
			{
				// Si le média est une image
				if (media.getType() == TypeMedia.PICTURE)
				{
					// On cache la vidéo s'il y en avait une
					removeMedia();

					// On affiche l'image
					showImageFromName(media.getName());
					// On attend pendant la duration du Media

					// wait x ?
					try
					{
						TimeUnit.SECONDS.sleep(media.getDuration().getSeconds());
					}
					// Si l'attente est interrompue on le signale
					catch (InterruptedException error)
					{
						System.out.println("Interruption de l'attente");
					}

					// On affiche l'inter stimulation
					showImageFromName(media.getInterStim().getName());

					// On attend pendant la duration de l'inter stimulation

					// wait x ?
					try
					{
						TimeUnit.SECONDS.sleep(media.getInterStim().getDuration().getSeconds());
					}
					// Si l'attente est interrompue on le signale
					catch (InterruptedException error)
					{
						System.out.println("Interruption de l'attente");
					}

				}
				// Si le média est une vidéo
				else if (media.getType() == TypeMedia.VIDEO)
				{
					// On cache l'image s'il y en avait une
					removeImage();
					// On affiche la vidéo
					showMediaFromName(media.getName());
					// On attend pendant la duration du Media

					// wait x ?
					try
					{
						TimeUnit.SECONDS.sleep(media.getDuration().getSeconds());
					}
					// Si l'attente est interrompue on le signale
					catch (InterruptedException error)
					{
						System.out.println("Interruption de l'attente");
					}
				}

			}
		}
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
