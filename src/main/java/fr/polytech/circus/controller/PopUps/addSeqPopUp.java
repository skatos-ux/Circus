package fr.polytech.circus.controller.PopUps;

import fr.polytech.circus.CircusApplication;
import fr.polytech.circus.controller.MetaSequenceController;
import fr.polytech.circus.model.Internals.ObservableMetaSequenceSet;
import fr.polytech.circus.model.MetaSequence;
import fr.polytech.circus.model.Sequence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.List;

public class addSeqPopUp
	{
	//******************************************************************************************************************
	// Composants UI
	//******************************************************************************************************************
	@FXML private TextField nameNewSeq;
	@FXML private Button    addSeqCancel;
	@FXML private Button    addSeqSave;
	@FXML private RadioButton addNewSeq;
	@FXML private RadioButton addCopySeq;
	@FXML private ComboBox nameListSeq;
	//******************************************************************************************************************

	//******************************************************************************************************************
	// Gestionnaires méta-sequences
	//******************************************************************************************************************
	private ObservableList                   listSequences        = null;
	private MetaSequence                                metaSequence         = null;
	private Stage                                       popUpStage           = null;
	private MetaSequenceController.AddListener addListener = null;
	//******************************************************************************************************************

	//******************************************************************************************************************
	//   ###    ###   #   #   ####  #####  ####   #   #   ###   #####   ###   ####    ####
	//  #   #  #   #  ##  #  #        #    #   #  #   #  #   #    #    #   #  #   #  #
	//  #      #   #  # # #   ###     #    ####   #   #  #        #    #   #  ####    ###
	//  #   #  #   #  #  ##      #    #    #   #  #   #  #   #    #    #   #  #   #      #
	//   ###    ###   #   #  ####     #    #   #   ###    ###     #     ###   #   #  ####
	//******************************************************************************************************************

	public addSeqPopUp(Window owner,
					   ObservableList<Sequence> listSequences,
                       MetaSequence metaSequence,
                       MetaSequenceController.AddListener addListener )
		{

		FXMLLoader fxmlLoader = new FXMLLoader ( CircusApplication.class.getResource ( "views/popups/add_seq_popup.fxml" ) );
		fxmlLoader.setController ( this );

		try
			{
			this.metaSequence   = metaSequence;
			this.listSequences = listSequences;
			this.addListener    = addListener;

			Scene dialogScene  = new Scene ( fxmlLoader.load (), 500, 100 );
			Stage dialog       = new Stage ();

			this.popUpStage = dialog;

			dialog.initModality ( Modality.APPLICATION_MODAL                 );
			dialog.initOwner    ( owner                                      );
			dialog.setScene     ( dialogScene                                );
			dialog.setResizable ( false                                      );
			dialog.setTitle     ( "Ajout Séquence à " + this.metaSequence.getName () );

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
	@FXML private void initialize ()
		{
		this.nameListSeq.setItems(listSequences);
		this.nameListSeq.getSelectionModel ().select ( 0 );
		this.nameListSeq.setDisable(true);
		this.addSeqSave.setDisable (true);

		this.nameNewSeq.setOnKeyReleased  ( keyEvent   -> checkNameNewSeqFilled           () );
		this.addCopySeq.setOnMouseClicked(mouseEvent -> selectAddCopySeq ());
		this.addNewSeq.setOnMouseClicked(mouseEvent -> selectAddNewSeq ());
		this.addSeqCancel.setOnMouseClicked ( mouseEvent -> cancelAddSeq () );
		this.addSeqSave.setOnMouseClicked ( mouseEvent -> addSeqToMetaseq   () );
		}

	@FXML private void checkNameNewSeqFilled() {
		if(this.addNewSeq.isSelected()) {
			if (this.nameNewSeq.getText().length() > 0) {
				this.addSeqSave.setDisable(false);
			} else {
				this.addSeqSave.setDisable(true);
			}
		}
	}

	@FXML private void selectAddNewSeq() {
		if(addCopySeq.isSelected()){
			this.addCopySeq.fire();
		}
		this.nameListSeq.setDisable(true);

		if(this.nameNewSeq.getText().length() > 0){
			this.addSeqSave.setDisable (false);
		} else {
			this.addSeqSave.setDisable(true);
		}
	}

	@FXML private void selectAddCopySeq() {
		if(this.addNewSeq.isSelected()){
			this.addNewSeq.fire();
		}
		this.nameListSeq.setDisable(false);
		this.addSeqSave.setDisable(false);
	}

	@FXML private void cancelAddSeq() {
		this.popUpStage.close ();
	}

	@FXML private void addSeqToMetaseq()
		{
		Alert alert = new Alert( Alert.AlertType.CONFIRMATION,
		                         "Etes-vous sûr de vouloir enregistrer les modifications de " + this.metaSequence.getName () + " ?",
		                         ButtonType.YES,
		                         ButtonType.NO);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES)
			{
				if(this.addNewSeq.isSelected()) {
					this.metaSequence.addSequence(new Sequence(this.nameNewSeq.getText()));
				} else {
					this.metaSequence.addSequence((Sequence) this.nameListSeq.getSelectionModel().getSelectedItem());
				}
				this.addListener.onModified ( this.metaSequence );
				this.popUpStage.close ();
			}
		}

	}
