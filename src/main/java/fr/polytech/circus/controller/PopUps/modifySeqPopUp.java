package fr.polytech.circus.controller.PopUps;

import fr.polytech.circus.CircusApplication;
import fr.polytech.circus.controller.MetaSequenceController;
import fr.polytech.circus.model.MetaSequence;
import fr.polytech.circus.model.Sequence;
import fr.polytech.circus.model.Media;
import fr.polytech.circus.model.TypeMedia;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

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
	private FileChooser fileChooserInterstim;

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

	private List<Media> listMediaPlusInterstim = null;

	private void consructMediaInterstimList() {

		this.listMediaPlusInterstim.clear();

		for (int i = 0; i < this.sequence.getListMedias().size(); i++) {

			//this.mediaTable.getEditingCell().getRow(); //.get(i).setStyle("-fx-background-color: green");

			this.listMediaPlusInterstim.add(this.sequence.getListMedias().get(i));

			if (this.sequence.getListMedias().get(i).getInterStim() != null) {
				this.listMediaPlusInterstim.add(this.sequence.getListMedias().get(i).getInterStim());
			}
		}
	}

	private Media tempMedia = null;

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
			this.listMediaPlusInterstim = new ArrayList<>();
			consructMediaInterstimList();

			this.fileChooserInterstim = new FileChooser();
			this.fileChooserInterstim.setTitle("Open file (interstim)");
			this.fileChooserInterstim.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
			);

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

		this.mediaTableColumnDuration.setCellValueFactory(cellData -> {
			String formattedDuration = cellData.getValue().getDuration().toString()
					.replace("PT", "")
					.replace("M", "m")
					.replace("S", "s");
			return new SimpleStringProperty(formattedDuration);
		});

		Callback<TableColumn<Media, String>, TableCell<Media, String>> cellFactoryOption = new Callback<>()
		{
			@Override
			public TableCell<Media, String> call(final TableColumn<Media, String> param)
			{
				return new TableCell<>()
				{
					final Button tableViewOptionButton = new Button("");
					final Button tableViewDeleteButton = new Button("");
					final Button tableViewAddButton = new Button("");
					final HBox hBox = new HBox (tableViewAddButton, tableViewOptionButton, tableViewDeleteButton);

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
							Boolean interstim = Boolean.FALSE;
							if (tempMedia != null) {
								if (getTableRow().getItem() == tempMedia.getInterStim()) {
									interstim = Boolean.TRUE;
								}
							}
							tempMedia = getTableRow().getItem();

							final FontIcon addIcon = new FontIcon ("fa-plus");
							final FontIcon cogIcon = new FontIcon ("fa-cog");
							final FontIcon delIcon = new FontIcon ("fa-trash");

							hBox.setAlignment ( Pos.CENTER );
							hBox.setSpacing ( 20 );

							tableViewAddButton.setGraphic ( addIcon );
							tableViewOptionButton.setGraphic ( cogIcon );
							tableViewDeleteButton.setGraphic ( delIcon );

							tableViewOptionButton.setOnMouseClicked(event ->
							{
								Media media = getTableView().getItems().get(getIndex());
								modifyMediaInSeq(media);
							});


							if (interstim) {
								tableViewAddButton.setDisable(true);

								tableViewDeleteButton.setOnAction(event ->
								{
									for (int i = 0; i < listMediaPlusInterstim.size(); i++) {
										if (listMediaPlusInterstim.get(i).getInterStim() == getTableView().getItems().get(getIndex())) {
											listMediaPlusInterstim.get(i).setInterStim(null);

										}
									}

									consructMediaInterstimList();
									mediaTable.setItems ( FXCollections.observableList (listMediaPlusInterstim)  );
									mediaTable.refresh();
								});

								getTableRow().setStyle("-fx-background-color : #e6f2ff");
							}
							else {
								tableViewDeleteButton.setOnAction(event ->
								{
									sequence.remMedia(getTableView().getItems().get(getIndex()));
									consructMediaInterstimList();
									mediaTable.setItems ( FXCollections.observableList (listMediaPlusInterstim)  );
									mediaTable.refresh();
								});

								if (getTableView().getItems().get(getIndex()).getInterStim() == null) {
									tableViewAddButton.setOnMouseClicked(event ->
									{
										try {
											File newInterstim = fileChooserInterstim.showOpenDialog(popUpStage);
											Path path = Paths.get(newInterstim.getPath());
											OutputStream os = new FileOutputStream("medias/" + newInterstim.getName());
											Files.copy(path,os);

											Media newMedia = new Media(
													newInterstim.getName(),
													newInterstim.getName(),
													Duration.ofSeconds(1),
													TypeMedia.PICTURE,
													null
											);

											getTableView().getItems().get(getIndex()).setInterStim(newMedia);

											consructMediaInterstimList();
											mediaTable.setItems ( FXCollections.observableList (listMediaPlusInterstim)  );
											mediaTable.refresh();
										}
										catch (Exception e) {
											System.out.printf("Aucun fichier selectionné.");
										}
									});
								}
								else {
									tableViewAddButton.setDisable(true);
								}

								getTableRow().setStyle("-fx-background-color : #b3d9ff");
							}

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
							Media media = getTableView ()
									.getItems ().get ( getIndex () );

							if(media.getVerr()){
								tableViewVerrCheckBox.setSelected(true);
							} else {
								tableViewVerrCheckBox.setSelected(false);
							}

							tableViewVerrCheckBox.setOnAction ( event ->
							{
								if(tableViewVerrCheckBox.isSelected()){
									media.setVerr(true);
								}
								else{
									media.setVerr(false);
								}
							} );

							setGraphic(hBox);
						}
						setText ( null );
					}
				};
			}
		};

		this.mediaTableColumnOption.setCellFactory(cellFactoryOption);
		this.mediaTableColumnVerrouillage.setCellFactory(cellFactoryVerr);

		this.mediaTable.setItems(FXCollections.observableList(this.listMediaPlusInterstim));

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
			consructMediaInterstimList();
			this.mediaTable.setItems(FXCollections.observableList(this.listMediaPlusInterstim));
			this.mediaTable.refresh();
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
			consructMediaInterstimList();
			this.mediaTable.setItems(FXCollections.observableList(this.listMediaPlusInterstim));
			this.mediaTable.refresh();
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
			this.sequence.setDuration(sequence.getDuration());
			this.listener.onModified(this.sequence);
			this.popUpStage.close ();
		}
	}
}
