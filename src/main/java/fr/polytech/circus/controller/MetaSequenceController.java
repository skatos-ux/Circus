package fr.polytech.circus.controller;

import fr.polytech.circus.model.MetaSequence;
import fr.polytech.circus.utils.MetaSequenceContainer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

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

	private final ArrayList<MetaSequence> metaSequences = new ArrayList<> ();

	public MetaSequenceController ()
		{
		metaSequences.addAll ( new MetaSequenceContainer ().getMetaSequences () );
		}

	@FXML private void initialize ()
		{
		//**************************************************************************************************************
		// Initialisation de l'ui.
		//**************************************************************************************************************

		//--------------------------------------------------------------------------------------------------------------
		// Sélécteur de méta-séquences
		//--------------------------------------------------------------------------------------------------------------
		metaSeqComboBox.setItems ( FXCollections.observableArrayList ( metaSequences ) );
		metaSeqComboBox.getSelectionModel ().select ( 0 );
		//--------------------------------------------------------------------------------------------------------------
		// Luminosité
		//--------------------------------------------------------------------------------------------------------------
		luminositySpinner.setValueFactory ( new SpinnerValueFactory.IntegerSpinnerValueFactory( 0, 100, 50));
		//--------------------------------------------------------------------------------------------------------------
		// Barre de progression
		//--------------------------------------------------------------------------------------------------------------
		progressBar.setProgress ( 50 );
		//--------------------------------------------------------------------------------------------------------------
		}

	@FXML private void addMetaSeq ( MouseEvent mouseEvent )
		{

		}
	}
