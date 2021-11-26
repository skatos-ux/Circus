package fr.polytech.circus.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class MetaSequenceController
	{

	@FXML
	ComboBox< String > metaSeqComboBox;

	@FXML
	Spinner< Integer > luminositySpinner;

	@FXML
	ProgressBar        progressBar;


	public MetaSequenceController ()
		{

		}

	@FXML
	private void initialize ()
		{
		//**************************************************************************************************************
		// Initialisation de l'ui.
		//**************************************************************************************************************

		//--------------------------------------------------------------------------------------------------------------
		// Sélécteur de méta-séquences
		//--------------------------------------------------------------------------------------------------------------
		metaSeqComboBox.setItems ( FXCollections.observableArrayList ( "meta1", "meta2" ) );
		metaSeqComboBox.getSelectionModel ().select ( 0 );
		//--------------------------------------------------------------------------------------------------------------
		// Luminosité
		//--------------------------------------------------------------------------------------------------------------
		luminositySpinner.setValueFactory ( new SpinnerValueFactory.IntegerSpinnerValueFactory( 0, 100, 50));
		//--------------------------------------------------------------------------------------------------------------
		// Barre de progression
		//--------------------------------------------------------------------------------------------------------------
		progressBar.setProgress ( 50 );
		}

	}
