package fr.polytech.circus.controller;

import fr.polytech.circus.model.MetaSequence;
import fr.polytech.circus.utils.MetaSequenceContainer;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class MetaSequenceController
	{

	//******************************************************************************************************************
	// Composants UI
	//******************************************************************************************************************
	@FXML private ComboBox< MetaSequence > metaSeqComboBox;
	@FXML private Spinner< Integer > luminositySpinner;
	@FXML private ProgressBar        progressBar;
	@FXML private Button             addMetaSeq;
	@FXML private Button             metaSeqBackward;
	@FXML private Button             metaSeqPlay;
	@FXML private Button             metaSeqForward;
	//******************************************************************************************************************

	private final MetaSequenceContainer     metaSequenceContainer = new MetaSequenceContainer ();
	private final ObservableMetaSequenceSet metaSequences         = new ObservableMetaSequenceSet ();

	public MetaSequenceController () {}

	static class ObservableMetaSequenceSet extends SimpleListProperty<MetaSequence>
		{

			public ObservableMetaSequenceSet()
				{
				super(FXCollections.observableArrayList ());
				}

			public void addUnique(MetaSequence metaSequence)
				{
				if ( !super.get ().contains ( metaSequence ) )
					{
					super.get ().add ( metaSequence );
					}
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
		// Luminosité
		//--------------------------------------------------------------------------------------------------------------
		luminositySpinner.setValueFactory ( new SpinnerValueFactory.IntegerSpinnerValueFactory( 0, 100, 50));
		//--------------------------------------------------------------------------------------------------------------
		// Barre de progression
		//--------------------------------------------------------------------------------------------------------------
		progressBar.setProgress ( 0.5 );
		//--------------------------------------------------------------------------------------------------------------
		}

	@FXML private void addMetaSeq ( MouseEvent mouseEvent )
		{
			//TODO: Faire equals dans MetaSequence
			metaSequences.addUnique ( new MetaSequence () );
			metaSeqComboBox.getSelectionModel ().select ( metaSequences.size () - 1 );
		}
	}
