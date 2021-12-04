package fr.polytech.circus;

import fr.polytech.circus.controller.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class CircusApplication extends Application
	{
	@Override
	public void start ( Stage stage ) throws IOException
		{
		FXMLLoader fxmlLoader = new FXMLLoader ( CircusApplication.class.getResource ( "views/main_window.fxml" ) );
		fxmlLoader.setController ( new MainWindowController () );

		Scene      scene      = new Scene ( fxmlLoader.load (), 1000, 500 );

		scene.getStylesheets().add ( BootstrapFX.bootstrapFXStylesheet () );
		stage.setTitle ( "Application Circus" );
		stage.setScene ( scene );
		stage.show ();
		}

	public static void main ( String[] args )
		{
		launch ();
		}
	}
