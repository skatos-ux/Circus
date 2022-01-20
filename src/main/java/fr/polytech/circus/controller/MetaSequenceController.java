package fr.polytech.circus.controller;

import fr.polytech.circus.CircusApplication;
import fr.polytech.circus.controller.PopUps.addSeqPopUp;
import fr.polytech.circus.controller.PopUps.modifyMetaSeqPopUp;
import fr.polytech.circus.controller.PopUps.modifySeqPopUp;
import fr.polytech.circus.model.Internals.ObservableMetaSequenceSet;
import fr.polytech.circus.model.Media;
import fr.polytech.circus.model.MetaSequence;
import fr.polytech.circus.model.Sequence;
import fr.polytech.circus.utils.MetaSequenceContainer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.Duration;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class MetaSequenceController
	{

	//******************************************************************************************************************
	// Composants UI
	//******************************************************************************************************************
	@FXML private ComboBox< MetaSequence >          metaSeqComboBox;
	@FXML private ProgressBar                       progressBar;
	@FXML private TextField                         metaSeqAddName;
	@FXML private Button                            metaSeqAdd;
	@FXML private Button                            metaSeqAddQuit;
	@FXML private Button                            metaSeqOption;
	@FXML private TableView< Sequence >             metaSeqTable;
	@FXML private TableColumn< Sequence, String >   metaSeqTableColumnName;
	@FXML private TableColumn< Sequence, String > metaSeqTableColumnDuration;
	@FXML private TableColumn< Sequence, String >   metaSeqTableColumnOption;
	@FXML private TableColumn< Sequence, String >   metaSeqTableColumnVerrouillage;
	@FXML private Button                            addSeqToMetaSeq;
	@FXML private Button                            metaSeqBackward;
	@FXML private Button                            metaSeqPlay;
	@FXML private Button                            metaSeqForward;

	private final FontIcon addIcon   = new FontIcon ( "fa-plus" );
	private final FontIcon checkIcon = new FontIcon ( "fa-check" );
	private final FontIcon pauseIcon = new FontIcon ( "fa-pause" );
	private final FontIcon playIcon  = new FontIcon ( "fa-play" );


	//******************************************************************************************************************

	//******************************************************************************************************************
	// Gestionnaires méta-sequences
	//******************************************************************************************************************
	private final MetaSequenceController    self                  = this;
	private final MetaSequenceContainer     metaSequenceContainer = new MetaSequenceContainer ();
	private final ObservableMetaSequenceSet metaSequences         = new ObservableMetaSequenceSet ();
	//******************************************************************************************************************

	//******************************************************************************************************************
	// Gestionnaires d'état interne
	//******************************************************************************************************************
	private int addState = 0;
	//******************************************************************************************************************

	//******************************************************************************************************************
	// Gestionnaire Viewer
	//******************************************************************************************************************
	private ViewerController viewer             = null;
	private Boolean          viewerPlayingState = true;
	//******************************************************************************************************************


	//******************************************************************************************************************
	//  #  #   #  #####  #####  ####   #####   ###    ###   #####   ####
	//  #  ##  #    #    #      #   #  #      #   #  #   #  #      #
	//  #  # # #    #    ###    ####   ###    #####  #      ###     ###
	//  #  #  ##    #    #      #   #  #      #   #  #   #  #          #
	//  #  #   #    #    #####  #   #  #      #   #   ###   #####  ####
	//******************************************************************************************************************

	public interface ModificationMetaSeqListener extends EventListener
		{
		void onModified ( MetaSequence newMetaSequence );
		}

	public interface ModificationSequenceListener extends EventListener
		{
		void onModified ( Sequence newSequence );
		}

	//******************************************************************************************************************
	//   ###    ###   #   #   ####  #####  ####   #   #   ###   #####   ###   ####    ####
	//  #   #  #   #  ##  #  #        #    #   #  #   #  #   #    #    #   #  #   #  #
	//  #      #   #  # # #   ###     #    ####   #   #  #        #    #   #  ####    ###
	//  #   #  #   #  #  ##      #    #    #   #  #   #  #   #    #    #   #  #   #      #
	//   ###    ###   #   #  ####     #    #   #   ###    ###     #     ###   #   #  ####
	//******************************************************************************************************************

	public MetaSequenceController ()
		{
		}

	//******************************************************************************************************************
	//      #  #####  #   #         #####  #   #  #   #   ###   #####  #   ###   #   #   ####
	//      #  #       # #          #      #   #  ##  #  #   #    #    #  #   #  ##  #  #
	//      #  ###      #           ###    #   #  # # #  #        #    #  #   #  # # #   ###
	//  #   #  #       # #          #      #   #  #  ##  #   #    #    #  #   #  #  ##      #
	//   ###   #      #   #         #       ###   #   #   ###     #    #   ###   #   #  ####
	//******************************************************************************************************************

	@FXML
	private void initialize ()
		{
		//**************************************************************************************************************
		// Initialisation de l'ui.
		//**************************************************************************************************************

		//--------------------------------------------------------------------------------------------------------------
		// Sélecteur de méta-séquences
		//--------------------------------------------------------------------------------------------------------------
		metaSeqComboBox.setItems ( metaSequences );

		//Lecture des donnees serialisees
		metaSequences.addAll ( CircusApplication.dataCircus.getMetaSequenceList () );

		metaSeqComboBox.getSelectionModel ().select ( 0 );
		//--------------------------------------------------------------------------------------------------------------
		// Table
		//--------------------------------------------------------------------------------------------------------------
		metaSeqTableColumnName.setCellValueFactory ( new PropertyValueFactory<> ( "name" ) );
		metaSeqTableColumnDuration.setCellValueFactory(cellData -> {
			String formattedDuration = cellData.getValue().getDuration().toString()
					.replace("PT", "")
					.replace("M", "m")
					.replace("S", "s");
			return new SimpleStringProperty(formattedDuration);
		});
		metaSeqTableColumnOption.setCellValueFactory ( new PropertyValueFactory<> ( "name" ) );
		metaSeqTableColumnVerrouillage.setCellValueFactory(new PropertyValueFactory<> ( "name" ));

		Callback< TableColumn< Sequence, String >, TableCell< Sequence, String > > cellFactory = new Callback<> ()
			{
			@Override
			public TableCell< Sequence, String > call ( final TableColumn< Sequence, String > param )
				{
				return new TableCell<> ()
					{
					final Button tableViewOptionButton = new Button ( "" );
					final Button tableViewDeleteButton = new Button ( "" );
					final HBox hBox = new HBox ( tableViewOptionButton, tableViewDeleteButton );

					@Override
					public void updateItem ( String item, boolean empty )
						{
						super.updateItem ( item, empty );
						if ( empty )
							{
							setGraphic ( null );
							}
						else
							{
							final FontIcon cogIcon = new FontIcon ( "fa-cog" );
							final FontIcon delIcon = new FontIcon ( "fa-trash" );

							hBox.setAlignment ( Pos.CENTER );
							hBox.setSpacing ( 20 );

							tableViewOptionButton.setGraphic ( cogIcon );
							tableViewDeleteButton.setGraphic ( delIcon );

							tableViewOptionButton.setOnMouseClicked ( event ->
								                                          {
								                                          Sequence sequence = getTableView ()
										                                          .getItems ().get ( getIndex () );
								                                          modifySeqInMetaSeq ( sequence );
								                                          } );

							tableViewDeleteButton.setOnAction ( event ->
								                                    {
								                                    MetaSequence metaSequence =
										                                    metaSeqComboBox.getValue ();
								                                    metaSequence.getListSequences ().remove (
										                                    getTableView ().getItems ()
										                                                   .get ( getIndex () ) );
								                                    metaSeqTable.setItems (
										                                    FXCollections.observableList (
												                                    metaSequence.getListSequences () ) );
								                                    } );

							setGraphic ( hBox );
							}
						setText ( null );
						}
					};
				}
			};

		Callback<TableColumn<Sequence, String>, TableCell<Sequence, String>> cellFactoryVerr = new Callback<>()
		{
			@Override
			public TableCell<Sequence, String> call(final TableColumn<Sequence, String> param)
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

							Sequence sequence = getTableView ()
									.getItems ().get ( getIndex () );

							if(sequence.getVerr()){
								tableViewVerrCheckBox.setSelected(true);
							} else {
								tableViewVerrCheckBox.setSelected(false);
							}

							tableViewVerrCheckBox.setOnAction ( event ->
							{
								if(tableViewVerrCheckBox.isSelected()){
									sequence.setVerr(true);
								}
								else{
									sequence.setVerr(false);
								}

							} );

							setGraphic(hBox);
						}
						setText ( null );
					}
				};
			}
		};
		metaSeqTableColumnOption.setCellFactory ( cellFactory );
		metaSeqTableColumnVerrouillage.setCellFactory (cellFactoryVerr);
		metaSeqTable.getColumns ().clear ();
		metaSeqTable.getColumns ().addAll (metaSeqTableColumnVerrouillage,
											metaSeqTableColumnName,
		                                    metaSeqTableColumnDuration,
		                                    metaSeqTableColumnOption
											);
		metaSeqTable.setItems ( FXCollections.observableList ( metaSequences.get ( 0 ).getListSequences () ) );
		//--------------------------------------------------------------------------------------------------------------
		// Ajout méta-séquences
		//--------------------------------------------------------------------------------------------------------------
		metaSeqAdd.setGraphic ( addIcon );
		metaSeqAddName.setVisible ( false );
		metaSeqAddName.setManaged ( false );

		metaSeqAddQuit.setVisible ( false );
		metaSeqAddQuit.setManaged ( false );
		//--------------------------------------------------------------------------------------------------------------
		// Barre de progression
		//--------------------------------------------------------------------------------------------------------------
		progressBar.setProgress ( 0.5 );
		//--------------------------------------------------------------------------------------------------------------
		}

	@FXML
	private void switchMetaSeq ()
		{
		if ( metaSeqComboBox.getValue () != null )
			{
			metaSeqTable.setItems ( FXCollections.observableList ( metaSeqComboBox.getValue ().getListSequences () ) );
			}
		}

	@FXML
	private void addMetaSeq ()
		{
		if ( this.addState != 0 )
			{
			MetaSequence newMetaSequence = new MetaSequence ( metaSeqAddName.getText () );
			metaSequences.add ( newMetaSequence );
			metaSeqComboBox.getSelectionModel ().select ( metaSequences.size () - 1 );
			CircusApplication.dataCircus.saveMetaSeq ( newMetaSequence );
			}
		toggleMetaSeqOptions ();
		}

	@FXML
	private void addQuitMetaSeq ()
		{
		toggleMetaSeqOptions ();
		}

	@FXML
	private void modifyMetaSeq ()
		{
		ModificationMetaSeqListener modificationMetaSeqListener = newMetaSequence ->
			{
			self.metaSeqComboBox.getSelectionModel ().clearSelection ();
			self.metaSeqComboBox.getSelectionModel ().select ( newMetaSequence );
			};

		new modifyMetaSeqPopUp ( this.metaSeqComboBox.getScene ().getWindow (),
		                         this.metaSequences,
		                         this.metaSeqComboBox.getSelectionModel ().getSelectedItem (),
		                         modificationMetaSeqListener
		);
		}

	@FXML
	private void addSeqToMetaSeq ()
		{
		ModificationMetaSeqListener addListener = newMetaSequence ->
				this.metaSeqTable.setItems ( FXCollections.observableList ( newMetaSequence.getListSequences () ) );

		new addSeqPopUp ( this.metaSeqComboBox.getScene ().getWindow (),
		                  FXCollections.observableList (
		                  this.metaSeqComboBox.getSelectionModel ().getSelectedItem ().getListSequences () ),
		                  this.metaSeqComboBox.getSelectionModel ().getSelectedItem (),
		                  addListener
			);
		}

	@FXML
	private void modifySeqInMetaSeq ( Sequence sequence )
		{
		ModificationSequenceListener listener = seq ->
			{
			this.metaSeqTable.refresh ();
			};

		new modifySeqPopUp (
				this.metaSeqComboBox.getScene ().getWindow (),
				FXCollections.observableList ( sequence.getListMedias () ),
				sequence,
				listener
			);
		}

	@FXML
	private void checkMetaSeqName ()
		{
		metaSeqAdd.setDisable ( metaSequences.findName ( metaSeqAddName.getText () ) );
		}
	@FXML
	private ArrayList<Media> randomiseMetaSequence(String nomMetaSeq){
		MetaSequence Sequence = metaSequences.get(0);
		for (int x=0 ; x < metaSequences.size() ; x=x+1 ){
			if ( metaSequences.get(x).getName().equals(nomMetaSeq) ){
				Sequence = metaSequences.get(x);
			}
		}

		int cursor = 0;
		ArrayList liste_fin = new ArrayList<Media>();

		ArrayList liste_seq = new ArrayList<Sequence>(Sequence.getListSequences());
			
		for (int z=0; z < liste_seq.size() ; z= z+1 ){
			if ( ((Sequence)liste_seq.get(z)).getVerr() != true )
			{
				int nombreAleatoire = 0 + (int)(Math.random() * (((liste_seq.size()-1) - 0) + 1));

				while( ((Sequence)liste_seq.get(nombreAleatoire)).getVerr() == true){
					nombreAleatoire = nombreAleatoire + 1;
					if (nombreAleatoire > (liste_seq.size()-1) ){
						nombreAleatoire = 0;
					}
				}

				Sequence cmp1 = (Sequence) liste_seq.get(nombreAleatoire);
				Sequence cmp2 = (Sequence) liste_seq.get(z);

				liste_seq.set(nombreAleatoire, cmp2);

				liste_seq.set(z, cmp1);

			}
		}
			

		for (int y=0; y < liste_seq.size() ; y= y+1 ){

		ArrayList liste_clone = new ArrayList<Media>(((Sequence)liste_seq.get(y)).getListMedias());

		for ( int i=0 ; i < liste_clone.size() ; i=i+1 ) {
			if ( ((Sequence)liste_seq.get(y)).getListMedias().get(i).getVerr() != true )
			{
				int nombreAleatoire = 0 + (int)(Math.random() * (((liste_clone.size()-1) - 0) + 1));

				while( ((Sequence)liste_seq.get(y)).getListMedias().get(nombreAleatoire).getVerr() == true){
					nombreAleatoire = nombreAleatoire+1;
					if (nombreAleatoire > liste_clone.size()-1 ){
						nombreAleatoire = 0;
					}
				}

				Media cmp1 = (Media) liste_clone.get(nombreAleatoire);
				Media cmp2 = (Media) liste_clone.get(i);

				liste_clone.set(nombreAleatoire, cmp2);

				liste_clone.set(i, cmp1);

			}
		}
		liste_fin.addAll(liste_clone);

		}
		return liste_fin;
	}
	
	
	@FXML
	private void play() {
		if ( viewer != null )
		{
			// System.out.println(viewerPlayingState);

			// Si le bouton affiché est le bouton play, cliquer dessus appelle la fonction appropriée du viewerController
			// Change aussi l'icone affichée et la variable d'état liée au bouton
			if ( viewerPlayingState )
			{
				viewer.playViewer();
				metaSeqPlay.setGraphic(pauseIcon);
				viewerPlayingState = false;
			}
			else
			{
				// Si le bouton affiché est le bouton pause, cliquer dessus appelle la fonction appropriée du viewerController
				// Change aussi l'icone affichée et la variable d'état liée au bouton
				viewer.pauseViewer();
				metaSeqPlay.setGraphic(playIcon);
				viewerPlayingState = true;
			}
		}
		// Si le viewer est fermé lorsque l'on appuie sur Play, on l'ouvre
		else
		{
			viewer = new ViewerController ( this.metaSeqComboBox.getScene ().getWindow (), metaSeqComboBox.getValue (), this );
		}
	}

	public void viewerClosed()
	{
		viewer = null;
		viewerPlayingState = true;
		metaSeqPlay.setGraphic(playIcon);
	}
	//******************************************************************************************************************
	//  #  #   #  #####  #####  ####   #   #   ###   #         #####  #   #  #   #   ###   #####  #   ###   #   #   ####
	//  #  ##  #    #    #      #   #  ##  #  #   #  #         #      #   #  ##  #  #   #    #    #  #   #  ##  #  #
	//  #  # # #    #    ###    ####   # # #  #####  #         ###    #   #  # # #  #        #    #  #   #  # # #   ###
	//  #  #  ##    #    #      #   #  #  ##  #   #  #         #      #   #  #  ##  #   #    #    #  #   #  #  ##      #
	//  #  #   #    #    #####  #   #  #   #  #   #  #####     #       ###   #   #   ###     #    #   ###   #   #  ####
	//******************************************************************************************************************

	private void toggleMetaSeqOptions ()
		{
		if ( this.addState == 0 )
			{
			metaSeqAdd.setGraphic ( checkIcon );
			metaSeqAdd.setDisable ( true );

			metaSeqAddName.setVisible ( true );
			metaSeqAddName.setManaged ( true );

			metaSeqAddQuit.setVisible ( true );
			metaSeqAddQuit.setManaged ( true );

			metaSeqOption.setVisible ( false );
			metaSeqOption.setManaged ( false );

			this.addState++;
			}
		else
			{
			metaSeqAdd.setDisable ( false );
			metaSeqAdd.setGraphic ( addIcon );

			metaSeqAddName.setText ( "" );
			metaSeqAddName.setVisible ( false );
			metaSeqAddName.setManaged ( false );

			metaSeqAddQuit.setVisible ( false );
			metaSeqAddQuit.setManaged ( false );

			metaSeqOption.setVisible ( true );
			metaSeqOption.setManaged ( true );

			this.addState = 0;
			}
		}

	}


