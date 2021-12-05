package fr.polytech.circus.controller;

import fr.polytech.circus.controller.PopUps.modifyMetaSeqPopUp;
import fr.polytech.circus.model.Internals.ObservableMetaSequenceSet;
import fr.polytech.circus.model.MetaSequence;
import fr.polytech.circus.model.Sequence;
import fr.polytech.circus.utils.MetaSequenceContainer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.Duration;
import java.util.EventListener;

public class MetaSequenceController
	{

	//******************************************************************************************************************
	// Composants UI
	//******************************************************************************************************************
	@FXML private ComboBox< MetaSequence > metaSeqComboBox;
	@FXML private ProgressBar              progressBar;
	@FXML private TextField                metaSeqAddName;
	@FXML private Button                   metaSeqAdd;
	@FXML private Button                   metaSeqAddQuit;
	@FXML private Button                   metaSeqOption;
	@FXML private TableView<Sequence>             metaSeqTable;
	@FXML private TableColumn<Sequence, String>  metaSeqTableColumnName;
	@FXML private TableColumn<Sequence, Duration> metaSeqTableColumnDuration;
	@FXML private TableColumn<Sequence, String>   metaSeqTableColumnOption;
	@FXML private Button                   metaSeqBackward;
	@FXML private Button                   metaSeqPlay;
	@FXML private Button                   metaSeqForward;

	private final FontIcon addIcon   = new FontIcon ("fa-plus" );
	private final FontIcon checkIcon = new FontIcon ("fa-check");
	//******************************************************************************************************************

	//******************************************************************************************************************
	// Gestionnaires méta-sequences
	//******************************************************************************************************************
	private final MetaSequenceController    self                  = this;
	private final MetaSequenceContainer     metaSequenceContainer = new MetaSequenceContainer     ();
	private final ObservableMetaSequenceSet metaSequences         = new ObservableMetaSequenceSet ();
	//******************************************************************************************************************

	//******************************************************************************************************************
	// Gestionnaires d'état interne
	//******************************************************************************************************************
	private       int                       addState              = 0;
	//******************************************************************************************************************

	//******************************************************************************************************************
	//  #  #   #  #####  #####  ####   #####   ###    ###   #####   ####
	//  #  ##  #    #    #      #   #  #      #   #  #   #  #      #
	//  #  # # #    #    ###    ####   ###    #####  #      ###     ###
	//  #  #  ##    #    #      #   #  #      #   #  #   #  #          #
	//  #  #   #    #    #####  #   #  #      #   #   ###   #####  ####
	//******************************************************************************************************************

	public interface ModificationListener extends EventListener
		{
		void onModified(MetaSequence newMetaSequence);
		}

	//******************************************************************************************************************
	//   ###    ###   #   #   ####  #####  ####   #   #   ###   #####   ###   ####    ####
	//  #   #  #   #  ##  #  #        #    #   #  #   #  #   #    #    #   #  #   #  #
	//  #      #   #  # # #   ###     #    ####   #   #  #        #    #   #  ####    ###
	//  #   #  #   #  #  ##      #    #    #   #  #   #  #   #    #    #   #  #   #      #
	//   ###    ###   #   #  ####     #    #   #   ###    ###     #     ###   #   #  ####
	//******************************************************************************************************************

	public MetaSequenceController () {}

	//******************************************************************************************************************
	//      #  #####  #   #         #####  #   #  #   #   ###   #####  #   ###   #   #   ####
	//      #  #       # #          #      #   #  ##  #  #   #    #    #  #   #  ##  #  #
	//      #  ###      #           ###    #   #  # # #  #        #    #  #   #  # # #   ###
	//  #   #  #       # #          #      #   #  #  ##  #   #    #    #  #   #  #  ##      #
	//   ###   #      #   #         #       ###   #   #   ###     #    #   ###   #   #  ####
	//******************************************************************************************************************

	@FXML private void initialize ()
		{
		//**************************************************************************************************************
		// Initialisation de l'ui.
		//**************************************************************************************************************

		//--------------------------------------------------------------------------------------------------------------
		// Sélécteur de méta-séquences
		//--------------------------------------------------------------------------------------------------------------
		metaSeqComboBox.setItems ( metaSequences );
		metaSequences  .addAll   ( metaSequenceContainer.getMetaSequences () );

		metaSeqComboBox.getSelectionModel ().select ( 0 );
		//--------------------------------------------------------------------------------------------------------------
		// Table
		//--------------------------------------------------------------------------------------------------------------
		metaSeqTableColumnName    .setCellValueFactory ( new PropertyValueFactory<> ( "name"   ) );
		metaSeqTableColumnDuration.setCellValueFactory ( new PropertyValueFactory<> ("duration") );
		metaSeqTableColumnOption  .setCellValueFactory ( new PropertyValueFactory<> ("name"    ) );


		Callback<TableColumn<Sequence, String>, TableCell<Sequence, String>> cellFactory = new Callback<TableColumn<Sequence, String>, TableCell<Sequence, String>>()
			{
			@Override
			public TableCell<Sequence, String> call(final TableColumn<Sequence, String> param)
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

							tableViewOptionButton.setOnAction(event ->
			                    {
								Sequence sequence = getTableView().getItems().get(getIndex());
								System.out.println(sequence.getName ());
								});
							setGraphic(hBox);
							}
						setText ( null );
						}
					};
				}
			};

		metaSeqTableColumnOption.setCellFactory(cellFactory);

		metaSeqTable.getColumns().clear ();
		metaSeqTable.getColumns ().addAll ( metaSeqTableColumnName,
		                                    metaSeqTableColumnDuration,
		                                    metaSeqTableColumnOption );
		metaSeqTable.setItems ( FXCollections.observableList (metaSequences.get (0).getListSequences ())  );
		//--------------------------------------------------------------------------------------------------------------
		// Ajout méta-séquences
		//--------------------------------------------------------------------------------------------------------------
		metaSeqAdd    .setGraphic ( addIcon );
		metaSeqAddName.setVisible ( false   );
		metaSeqAddName.setManaged ( false   );

		metaSeqAddQuit.setVisible ( false );
		metaSeqAddQuit.setManaged ( false );
		//--------------------------------------------------------------------------------------------------------------
		// Barre de progression
		//--------------------------------------------------------------------------------------------------------------
		progressBar.setProgress ( 0.5 );
		//--------------------------------------------------------------------------------------------------------------
		}

	@FXML private void addMetaSeq ()
		{
		if ( this.addState != 0 )
			{
			metaSequences.add ( new MetaSequence ( metaSeqAddName.getText () ) );
			metaSeqComboBox.getSelectionModel ().select ( metaSequences.size () - 1 );
			}
		toggleMetaSeqOptions ();
		}

	@FXML private void addQuitMetaSeq ()
		{
		toggleMetaSeqOptions ();
		}

	@FXML private void modifyMetaSeq ()
		{
		ModificationListener modificationListener = new ModificationListener ()
			{
			@Override
			public void onModified (MetaSequence newMetaSequence)
				{
				self.metaSeqComboBox.getSelectionModel ().clearSelection ();
				self.metaSeqComboBox.getSelectionModel ().select ( newMetaSequence );
				}
			};

		new modifyMetaSeqPopUp (this.metaSeqComboBox.getScene ().getWindow (),
								this.metaSequences,
		                        this.metaSeqComboBox.getSelectionModel ().getSelectedItem (),
		                        modificationListener
								);
		}


	@FXML private void checkMetaSeqName ()
		{
		metaSeqAdd.setDisable ( metaSequences.findName ( metaSeqAddName.getText () ) );
		}

	//******************************************************************************************************************
	//  #  #   #  #####  #####  ####    ###   #             #####  #   #  #   #   ###   #####  #   ###   #   #   ####
	//  #  ##  #    #    #      #   #  #   #  #             #      #   #  ##  #  #   #    #    #  #   #  ##  #  #
	//  #  # # #    #    ###    ####   #####  #             ###    #   #  # # #  #        #    #  #   #  # # #   ###
	//  #  #  ##    #    #      #   #  #   #  #             #      #   #  #  ##  #   #    #    #  #   #  #  ##      #
	//  #  #   #    #    #####  #   #  #   #  #####         #       ###   #   #   ###     #    #   ###   #   #  ####
	//******************************************************************************************************************

	private void toggleMetaSeqOptions ()
		{
		if ( this.addState == 0 )
			{
			metaSeqAdd.setGraphic ( checkIcon );
			metaSeqAdd.setDisable ( true      );

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
			metaSeqAdd.setDisable ( false   );
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


