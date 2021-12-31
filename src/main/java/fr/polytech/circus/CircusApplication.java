package fr.polytech.circus;

import fr.polytech.circus.controller.MainWindowController;
import fr.polytech.circus.model.DataCircus;
import fr.polytech.circus.model.MetaSequence;
import fr.polytech.circus.utils.Serializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CircusApplication extends Application
	{
		public static DataCircus dataCircus;

	@Override
	public void start (Stage stage) throws IOException
		{

		CircusApplication.dataCircus = new DataCircus();
		FXMLLoader fxmlLoader = new FXMLLoader ( CircusApplication.class.getResource ( "views/main_window.fxml" ) );
		fxmlLoader.setController ( new MainWindowController () );

		try {
			CircusApplication.dataCircus = Serializer.readDataCircus();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene = new Scene ( fxmlLoader.load (), 1000, 500 );
		stage.setTitle ( "Application Circus" );

		stage.setOnCloseRequest(event -> {
			try {
				Serializer.writeDataCircus(CircusApplication.dataCircus);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		stage.setScene ( scene );
		stage.show ();
		}

	public static void main ( String[] args )
		{
		launch ();
		}
	}
