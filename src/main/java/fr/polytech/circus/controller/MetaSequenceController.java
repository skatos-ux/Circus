package fr.polytech.circus.controller;

import fr.polytech.circus.model.MetaSequence;
import fr.polytech.circus.utils.MetaSequenceContainer;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.Duration;

public class MetaSequenceController
	{

	//******************************************************************************************************************
	// Composants UI
	//******************************************************************************************************************
	@FXML private ComboBox< MetaSequence > metaSeqComboBox;
	@FXML private Spinner< Integer > luminositySpinner;
	@FXML private ProgressBar        progressBar;
	@FXML private TextField          metaSeqAddName;
	@FXML private Button             metaSeqAdd;
	@FXML private Button             metaSeqBackward;
	@FXML private Button             metaSeqPlay;
	@FXML private Button             metaSeqForward;

	private final FontIcon addIcon   = new FontIcon ("fa-plus");
	private final FontIcon checkIcon = new FontIcon ("fa-check");
	//******************************************************************************************************************

	//******************************************************************************************************************
	// Gestionnaires méta-sequences
	//******************************************************************************************************************
	private final MetaSequenceContainer     metaSequenceContainer = new MetaSequenceContainer     ();
	private final ObservableMetaSequenceSet metaSequences         = new ObservableMetaSequenceSet ();
	//******************************************************************************************************************

	//******************************************************************************************************************
	// Gestionnaires d'état interne
	//******************************************************************************************************************
	private       int                       addState              = 0;
	//******************************************************************************************************************

	public MetaSequenceController () {}

	static class ObservableMetaSequenceSet extends SimpleListProperty<MetaSequence>
		{

			public ObservableMetaSequenceSet()
				{
				super(FXCollections.observableArrayList ());
				}

			public boolean findName ( String name )
				{
				ObservableList<MetaSequence> metaSequences = super.get ();

				for ( MetaSequence metaSequence: metaSequences )
					{
					if ( metaSequence.getName().equals ( name ) )
						{
						return true;
						}
					}
				return false;
				}
			public boolean add ( MetaSequence metaSequence )
				{
				if ( !super.get ().contains ( metaSequence ) )
					{
					return super.get ().add ( metaSequence );
					}
				return false;
				}
		}

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
		// Ajout méta-séquences
		//--------------------------------------------------------------------------------------------------------------
		metaSeqAdd    .setGraphic ( addIcon );
		metaSeqAddName.setManaged ( false   );
		//--------------------------------------------------------------------------------------------------------------
		// Luminosité
		//--------------------------------------------------------------------------------------------------------------
		luminositySpinner.setValueFactory ( new SpinnerValueFactory.IntegerSpinnerValueFactory( 0, 100, 50));
		//--------------------------------------------------------------------------------------------------------------
		// Barre de progression
		//--------------------------------------------------------------------------------------------------------------
		progressBar.setProgress ( 0.5 );
		//--------------------------------------------------------------------------------------------------------------
		}

	@FXML private void addMetaSeq ()
		{
		if ( this.addState == 0 )
			{
			metaSeqAdd.setGraphic ( checkIcon );
			metaSeqAdd.setDisable ( true      );

			metaSeqAddName.setVisible ( true );
			metaSeqAddName.setManaged ( true );
			this.addState++;
			}
		else
			{
			metaSequences.add ( new MetaSequence ( metaSeqAddName.getText (), Duration.ZERO) );
			metaSeqComboBox.getSelectionModel ().select ( metaSequences.size () - 1 );
			metaSeqAdd.setGraphic ( addIcon );
			metaSeqAddName.setText ( "" );
			metaSeqAddName.setVisible ( false );
			metaSeqAddName.setManaged ( false );
			this.addState = 0;
			}
		}

	@FXML private void checkMetaSeqName ( KeyEvent keyEvent )
		{
		metaSeqAdd.setDisable ( metaSequences.findName ( metaSeqAddName.getText () ) );
		}

	@FXML private void deleteMetaSeq ()
		{
		//TODO: MSGBOX
		}

	}


