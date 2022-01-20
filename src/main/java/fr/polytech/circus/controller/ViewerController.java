package fr.polytech.circus.controller;

import fr.polytech.circus.CircusApplication;
import fr.polytech.circus.model.MetaSequence;
import fr.polytech.circus.model.Sequence;
import fr.polytech.circus.model.TypeMedia;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Controleur permettant la gestion de modification d'une sequence
 */
public class ViewerController
{
	//******************************************************************************************************************
	// Composants UI
	//******************************************************************************************************************
	@FXML
	private MediaView mediaView;
	@FXML
	private ImageView imageView;
	private MediaPlayer mediaPlayer;
	//******************************************************************************************************************

	/**
	 * Stage du viewer
	 */
	private Stage viewerStage = null;

	// La timeline permettant la lecture des médias avec gestion du temps de chacun
	private Timeline timeline = null;

	ArrayList listeDebutMedia;
	// Le controller qui a créé ce controller
	private MetaSequenceController metaSequenceController;
	// La métaséquence communiquée au viewer
	private MetaSequence playingMetaSequence;
	// Booléen indiquant si la métaséquence a déjà été démarrée une fois ou pas
	private boolean metaSequenceStarted;
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
	 *
	 * @param owner Fenetre principale
	 */
	public ViewerController(Window owner, MetaSequence metaSequence, MetaSequenceController metaSequenceController)
	{
		FXMLLoader fxmlLoader = new FXMLLoader(CircusApplication.class.getResource("views/viewer.fxml"));
		fxmlLoader.setController(this);
		try
		{
			Scene dialogScene = new Scene(fxmlLoader.load(), 1600, 900);
			Stage dialog = new Stage();

			viewerStage = dialog;

			dialog.initModality(Modality.NONE);
			dialog.initOwner(owner);
			dialog.setScene(dialogScene);
			dialog.setResizable(true);
			dialog.setTitle("Viewer");
			dialog.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		playingMetaSequence = metaSequence;
		metaSequenceStarted = false;
		this.metaSequenceController = metaSequenceController;
		listeDebutMedia = new ArrayList<Integer>();
		closingManager();
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
	@FXML
	private void initialize()
	{
		// mediaView = new MediaView();
		// Ne pas décommenter la ligne suivante, cela remplace l'ImageView déjà mise placée dans la vue
		// imageView = new ImageView();
	}

	/**
	 * Affiche le media donné en paramètre
	 */
	@FXML
	private void showMedia(Media media)
	{
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);
		mediaView.setMediaPlayer(mediaPlayer);
	}

	/**
	 * Affiche le media dont le nom est donné en paramètre
	 */
	@FXML
	private void showMediaFromName(String name)
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
	@FXML
	private void removeMedia()
	{
		if (mediaView.getMediaPlayer() != null)
		{
			mediaView.getMediaPlayer().pause();
			mediaView.setMediaPlayer(null);
		}
	}

	/**
	 * Affiche l'image donnée en paramètre
	 */
	@FXML
	private void showImage(Image image)
	{
		imageView.setImage(image);
		imageView.setCache(true);
	}

	/**
	 * Affiche l'image dont le nom est donné en paramètre
	 */
	@FXML
	private void showImageFromName(String name)
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
	@FXML
	private void removeImage()
	{
		imageView.setImage(null);
	}

	/**
	 * Fonction de test de lecture de média
	 */
	@FXML
	private void testMedia() throws MalformedURLException
	{
		File mediaFile = new File("C:/chemin/test/test.mp4");
		Media media = new Media(mediaFile.toURI().toURL().toString());
		showMedia(media);
	}

	/**
	 * Fonction de test d'affichage d'image
	 */
	@FXML
	private void testImage() throws FileNotFoundException, URISyntaxException
	{
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
	@FXML
	private void startMetaSequence(MetaSequence metaSequence)
	{
		timeline = new Timeline();
		timeline.setCycleCount(1);
		timeline.setAutoReverse(false);

		// Compteur permettant de compter la durée totale des médias parcourus
		int cptDuree = 0;
		// System.out.println("CptDurée : " + cptDuree);

		for (Sequence sequence : metaSequence.getListSequences())
		{
			// Pour chaque Média de la séquence
			for (fr.polytech.circus.model.Media media : sequence.getListMedias())
			{
				// On affiche le média joué dans la console (test)
				// System.out.println(media.getName());

				// Si le média est une image
				if (media.getType() == TypeMedia.PICTURE)
				{
					// System.out.println("Image détectée");
					timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(cptDuree),
							new EventHandler<ActionEvent>()
							{
								@Override
								public void handle(ActionEvent event)
								{
									removeMedia();
									showImageFromName(media.getName());
									// System.out.println("Image donnée.");
								}
							}));
					// On ajoute dans la liste des départs de médias la seconde à laquelle l'image démarre
					listeDebutMedia.add(cptDuree);

					// On ajoute au compteur de durée la durée du média actuellement parcouru
					cptDuree += media.getDuration().getSeconds();
				}
				// Si le média est une vidéo
				else if (media.getType() == TypeMedia.VIDEO)
				{
					// System.out.println("Vidéo détectée");
					timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(cptDuree),
							new EventHandler<ActionEvent>()
							{
								@Override
								public void handle(ActionEvent event)
								{
									removeImage();
									showMediaFromName(media.getName());
									// System.out.println("Vidéo donnée.");
								}
							}));
					// On ajoute dans la liste des départs de médias la seconde à laquelle la vidéo démarre
					listeDebutMedia.add(cptDuree);
					// On ajoute au compteur de durée la durée du média actuellement parcouru
					cptDuree += media.getDuration().getSeconds();
				}
				// Si le média donné a une interstim
				if (media.getInterStim() != null)
				{
					timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(cptDuree),
							new EventHandler<ActionEvent>()
							{
								@Override
								public void handle(ActionEvent event)
								{
									removeMedia();
									showImageFromName(media.getInterStim().getName());
									// System.out.println("Image donnée.");
								}
							}));
					// On ajoute dans la liste des départs de médias l'interstimulation démarre
					listeDebutMedia.add(cptDuree);
					// On ajoute au compteur de durée de l'interstimulation
					cptDuree += media.getInterStim().getDuration().getSeconds();
				}
			}
		}

		// On ajoute un évènement qui retire l'image ou la vidéo à la fin de la lecture
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(cptDuree),
				new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent event)
					{
						removeMedia();
						removeImage();
					}
				}));

		timeline.play();
	}

	/**
	 * Met en pause la lecture
	 */
	@FXML
	public void pauseViewer()
	{
		if (timeline != null)
		{
			timeline.pause();
			if (mediaView.getMediaPlayer() != null)
			{
				mediaPlayer.pause();
			}
		}
	}

	/**
	 * Démarre la lecture de 0 si la méta-séquence n'a jamais été lancée. Sinon, relance la lecture des médias.
	 */
	@FXML
	public void playViewer()
	{
		if (metaSequenceStarted)
		{
			timeline.play();
			if (mediaView.getMediaPlayer() != null)
			{
				mediaPlayer.play();
			}
		}
		else
		{
			metaSequenceStarted = true;
			startMetaSequence(playingMetaSequence);
		}
	}

	/**
	 * Affiche le média suivant
	 */
	public void nextMedia()
	{
		if (listeDebutMedia.size() > 0)
		{
			// On définit une date de départ tampon, à la première date de départ de la liste des débuts (probablement 0)
			Integer dateDebutTrouvee = (Integer) listeDebutMedia.get(0);

			// Pour chaque date de départ de média
			for (int i = 0; i < listeDebutMedia.size(); i++)
			{
				// On vérifie si la date de départ est supérieure à la date actuelle de la timeline
				if ((Integer) listeDebutMedia.get(i) > timeline.getCurrentTime().toSeconds())
				{
					// On a trouvé le média
					dateDebutTrouvee = (Integer) listeDebutMedia.get(i);
					break;
				}
			}
			// Duration de javafx.util.Duration prend en paramètre de constructeur des ms
			// On multiplie par 1000 les secondes
			timeline.jumpTo(new Duration(dateDebutTrouvee * 1000));
		}
	}

	/**
	 * Affiche le média précédent
	 */
	public void prevMedia()
	{
		if (listeDebutMedia.size() > 0)
		{
			// On définit une date de départ tampon, à la première date de départ de la liste des débuts (probablement 0)
			Integer dateDebutTrouvee = (Integer) listeDebutMedia.get(0);

			// Pour chaque date de départ de média
			int i = 0;
			// Cette boucle permet de trouver l'index du premier média qui sera lancé après le média en cours
			while (i < listeDebutMedia.size() && (Integer) listeDebutMedia.get(i) < timeline.getCurrentTime().toSeconds())
			{
				i++;
			}

			// Si i est supérieur à 0, le média précédent est i-1, c'est l'index correspondant au médial actuel.
			// Le média à l'index i correspond au prochain média
			// Donc on veut retirer deux à i pour trouver le média précédent
			if (i > 1)
			{
				i -= 2;
				dateDebutTrouvee = (Integer) listeDebutMedia.get(i);
			}

			// Duration de javafx.util.Duration prend en paramètre de constructeur des ms
			// On multiplie par 1000 les secondes
			timeline.jumpTo(new Duration(dateDebutTrouvee * 1000));
		}
	}

	/**
	 * Quitte le viewer
	 */
	@FXML
	private void quitViewer()
	{
		viewerStage.close();
	}

	/**
	 * Appelé quand l'utilisateur ferme la fenêtre du viewer, cela appelle Called when the user closes the program, it calls saveDepartment to save data in a file.
	 */
	private void closingManager()
	{
		viewerStage.setOnCloseRequest(new EventHandler<WindowEvent>()
		{
			@Override
			public void handle(WindowEvent event)
			{
				metaSequenceController.viewerClosed();
			}
		});
	}
}