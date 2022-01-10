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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.time.Duration;
import java.util.EventListener;

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
	@FXML private TextField titleSequenceLabel;

	/**
	 *
	 */
	@FXML private TableView<Media> mediaTable;
	@FXML private TableColumn<Media, String> mediaTableColumnVerrouillage;
	@FXML private TableColumn<Media, String> mediaTableColumnName;
	@FXML private TableColumn<Media, String> mediaTableColumnDuration;
	@FXML private TableColumn<Media, String> mediaTableColumnOption;
	//******************************************************************************************************************

	//******************************************************************************************************************
	// Gestionnaires sequences
	//******************************************************************************************************************
	/**
	 * Sequence a modifier
	 */
	private Sequence sequence = null;

	/**
	 * List des medias
	 */
	private ObservableList listMedias = null;

	/**
	 * Pop up de modification
	 */
	private Stage popUpStage = null;

	/**
	 *
	 */
	private MetaSequenceController.ModificationSequenceListener listener;
	//******************************************************************************************************************

	public interface SequenceModificationListener extends EventListener
	{
		void onModified(Sequence sequence);
	}

	public interface MediaModificationListener extends EventListener
	{
		void onModified(Media media);
	}

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
	public modifySeqPopUp(Window owner, ObservableList<Media> listMedias, Sequence sequence,
						  MetaSequenceController.ModificationSequenceListener listener) {

		FXMLLoader fxmlLoader = new FXMLLoader ( CircusApplication.class.getResource ( "views/popups/modify_seq_popup.fxml" ) );
		fxmlLoader.setController ( this );

		try
		{
			this.sequence   = sequence;
			this.listMedias = listMedias;
			this.listener = listener;

			Scene dialogScene  = new Scene ( fxmlLoader.load (), 1000, 500 );
			Stage dialog       = new Stage ();

			this.popUpStage = dialog;

			dialog.initModality ( Modality.APPLICATION_MODAL                 );
			dialog.initOwner    ( owner                                      );
			dialog.setScene     ( dialogScene                                );
			dialog.setResizable ( true                                      );
			dialog.setMinHeight(250); //220 (+30 hauteur de l'entête de la fenêtre sur windows)
			dialog.setMinWidth(585); //575 (+10 largeur de la fenêtre sur windows)
			dialog.setTitle     ( "Modifier la séquence : " + this.sequence.getName () );

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

		this.titleSequenceLabel.setText(this.sequence.getName());
		this.mediaTableColumnName.setCellValueFactory(new PropertyValueFactory<> ("name"));
		this.mediaTableColumnDuration.setCellValueFactory(new PropertyValueFactory<> ("duration"));

		Callback<TableColumn<Media, String>, TableCell<Media, String>> cellFactory = new Callback<>()
		{
			@Override
			public TableCell<Media, String> call(final TableColumn<Media, String> param)
			{
				return new TableCell<>()
				{
					final Button tableViewOptionButton = new Button("");
					final Button tableViewDeleteButton = new Button("");
					final HBox hBox = new HBox (tableViewOptionButton, tableViewDeleteButton);

					@Override
					public void updateItem(String item, boolean empty)
					{
						super.updateItem(item, empty);
						if (empty)
						{
							setGraphic(null);
						}
						else
						{
							final FontIcon cogIcon = new FontIcon ("fa-cog");
							final FontIcon delIcon = new FontIcon ("fa-trash");

							hBox.setAlignment ( Pos.CENTER );
							hBox.setSpacing ( 20 );

							tableViewOptionButton.setGraphic ( cogIcon );
							tableViewDeleteButton.setGraphic ( delIcon );

							tableViewOptionButton.setOnMouseClicked(event ->
							{
								Media media = getTableView().getItems().get(getIndex());
								modifyMediaInSeq(media);
							});

							tableViewDeleteButton.setOnAction(event ->
							{
								sequence.getListMedias ().remove(getTableView().getItems().get(getIndex()));
								mediaTable.setItems ( FXCollections.observableList (sequence.getListMedias ())  );
							});

							setGraphic(hBox);
						}
						setText ( null );
					}
				};
			}
		};

		Callback<TableColumn<Media, String>, TableCell<Media, String>> cellFactoryVerr = new Callback<>()
		{
			@Override
			public TableCell<Media, String> call(final TableColumn<Media, String> param)
			{
				return new TableCell<>()
				{
					final CheckBox tableViewVerrCheckBox = new CheckBox("");
					final HBox hBox = new HBox (tableViewVerrCheckBox);

					@Override
					public void updateItem(String item, boolean empty)
					{
						super.updateItem(item, empty);
						if (empty)
						{
							setGraphic(null);
						}
						else
						{
							hBox.setAlignment ( Pos.CENTER );
							hBox.setSpacing ( 20 );
							tableViewVerrCheckBox.setSelected(true);
							tableViewVerrCheckBox.setOnMouseClicked(event ->
							{
								//Media media = getTableView().getItems().get(getIndex());
								//modifyMediaInSeq(media);
							});

							setGraphic(hBox);
						}
						setText ( null );
					}
				};
			}
		};
		this.mediaTableColumnOption.setCellFactory(cellFactory);
		this.mediaTableColumnVerrouillage.setCellFactory(cellFactoryVerr);
		this.mediaTable.setItems(FXCollections.observableList( this.sequence.getListMedias()));

		this.cancelAddMediaSeq.setOnMouseClicked ( mouseEvent -> cancelAddSeq () );
		this.saveAddMediaSeq.setOnMouseClicked ( mouseEvent -> saveMediasToSeq () );
		this.addMediaToSeq.setOnMouseClicked ( mouseEvent -> addMediaToSeq () );
		this.titleSequenceLabel.setOnKeyPressed( mouseEvent -> {
			KeyCode keyCode = mouseEvent.getCode();
			if (keyCode.equals(KeyCode.ENTER)) {
				modifySequenceName();
			}
		});
	}

	/**
	 *
	 */
	private void modifySequenceName() {
		if (this.sequence.getName() != this.titleSequenceLabel.getText()) {
			Alert alert = new Alert( Alert.AlertType.CONFIRMATION,
					"Etes-vous sûr de vouloir renommer la séquence en " + this.titleSequenceLabel.getText()+ " ?",
					ButtonType.YES,
					ButtonType.NO);

			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				this.sequence.setName(this.titleSequenceLabel.getText());
				this.popUpStage.setTitle("Modifier la séquence : " + this.sequence.getName ());
				this.listener.onModified(this.sequence);
			}
		}
	}

	/**
	 * Ajoute un media a la sequence
	 */
	@FXML private void addMediaToSeq() {
		SequenceModificationListener listener = sequence -> {
			this.mediaTable.setItems(FXCollections.observableList(sequence.getListMedias()));
		};

		new addMediaPopUp(
				this.saveAddMediaSeq.getScene().getWindow(),
				FXCollections.observableList (this.sequence.getListMedias()),
				this.sequence,
				listener
		);
	}

	/**
	 *
	 * @param media
	 */
	@FXML private void modifyMediaInSeq(Media media) {
		SequenceModificationListener listener1 = sequence -> {
			this.mediaTable.setItems(FXCollections.observableList(sequence.getListMedias()));
		};

		MediaModificationListener listener2 = temp -> {
			this.mediaTable.refresh();
		};

		new modifyMediaPopUp(
				this.saveAddMediaSeq.getScene().getWindow(),
				this.sequence,
				media,
				listener1,
				listener2
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
				this.sequence.setName(this.titleSequenceLabel.getText());
				this.listener.onModified(this.sequence);
				this.popUpStage.close ();
			}
	}
}
