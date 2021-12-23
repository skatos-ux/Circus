package fr.polytech.circus.controller.PopUps;

import fr.polytech.circus.CircusApplication;
import fr.polytech.circus.controller.MetaSequenceController;
import fr.polytech.circus.model.MetaSequence;
import fr.polytech.circus.model.Sequence;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class modifySeqPopUp
	{
	//******************************************************************************************************************
	// Composants UI
	//******************************************************************************************************************
	@FXML private Button    saveAddMediaSeq;
	@FXML private Button    cancelAddMediaSeq;
	@FXML private Button    addMediaToSeq;

	//******************************************************************************************************************

	//******************************************************************************************************************
	// Gestionnaires méta-sequences
	//******************************************************************************************************************
	private Sequence sequence = null;
	private Stage popUpStage = null;
	//******************************************************************************************************************

	//******************************************************************************************************************
	//   ###    ###   #   #   ####  #####  ####   #   #   ###   #####   ###   ####    ####
	//  #   #  #   #  ##  #  #        #    #   #  #   #  #   #    #    #   #  #   #  #
	//  #      #   #  # # #   ###     #    ####   #   #  #        #    #   #  ####    ###
	//  #   #  #   #  #  ##      #    #    #   #  #   #  #   #    #    #   #  #   #      #
	//   ###    ###   #   #  ####     #    #   #   ###    ###     #     ###   #   #  ####
	//******************************************************************************************************************

	public modifySeqPopUp(Window owner,
                          Sequence sequence)
		{

		FXMLLoader fxmlLoader = new FXMLLoader ( CircusApplication.class.getResource ( "views/popups/modify_seq_popup.fxml" ) );
		fxmlLoader.setController ( this );

		try
			{
			this.sequence   = sequence;

			Scene dialogScene  = new Scene ( fxmlLoader.load (), 1000, 500 );
			Stage dialog       = new Stage ();

			this.popUpStage = dialog;

			dialog.initModality ( Modality.APPLICATION_MODAL                 );
			dialog.initOwner    ( owner                                      );
			dialog.setScene     ( dialogScene                                );
			dialog.setResizable ( false                                      );
			dialog.setTitle     ( "Modifier la Séquence " + this.sequence.getName () );

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
			this.cancelAddMediaSeq.setOnMouseClicked ( mouseEvent -> cancelAddSeq () );
			this.saveAddMediaSeq.setOnMouseClicked ( mouseEvent -> saveMediasToSeq () );
			this.addMediaToSeq.setOnMouseClicked ( mouseEvent -> addMediaToSeq () );
		}

	@FXML private void addMediaToSeq() {
		//TO DO
	}


	@FXML private void cancelAddSeq() {
		this.popUpStage.close ();
	}

	@FXML private void saveMediasToSeq()
		{
		Alert alert = new Alert( Alert.AlertType.CONFIRMATION,
		                         "Etes-vous sûr de vouloir enregistrer les modifications de " + this.sequence.getName () + " ?",
		                         ButtonType.YES,
		                         ButtonType.NO);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES)
			{
				this.popUpStage.close ();
			}
		}

	}
