package testGamePong;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Driver extends Application {

	private static final int	FIELD_SIZE_WIDTH	= 600;
	private static final int	FIELD_SIZE_HEIGHT	= 450;
	private static final int	PADDLE_SIZE			= 45;
	private static final int	BALL_SIZE			= 3;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Ping Pong Machine Learning Test");
		Group root = new Group();
		Scene scene = new Scene(root, 1000, 700, Color.BLACK);

		Rectangle playField = new Rectangle(10, 10, FIELD_SIZE_WIDTH + 10, FIELD_SIZE_HEIGHT);
		playField.setFill(Color.BLUE);
		playField.setStroke(Color.WHITE);
		root.getChildren().add(playField);
		
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
