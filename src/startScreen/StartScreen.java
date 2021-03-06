package startScreen;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class StartScreen extends StackPane {

	private static final StartScreen instance = new StartScreen();

	private BackgroundStartScreen backgroundStartScreen;
	private StartPane startPane;
	private HighScorePane highScorePane;
	private SettingPane settingPane;
	private UpdatingPane updatingPane;

	public static StartScreen getInstance() {
		return instance;
	}

	public BackgroundStartScreen getBackgroundStartScreen() {
		return backgroundStartScreen;
	}

	public StartPane getStartPane() {
		return startPane;
	}

	public SettingPane getSettingPane() {
		return settingPane;
	}

	public UpdatingPane getUpdatingPane() {
		return updatingPane;
	}

	public HighScorePane getHighScorePane(){
		return highScorePane;
	}

	public StartScreen() {
		super();

		this.backgroundStartScreen = new BackgroundStartScreen();
		this.getChildren().add(backgroundStartScreen);

		this.startPane = new StartPane();
		this.getChildren().add(startPane);
		
		this.highScorePane = new HighScorePane();

		this.settingPane = new SettingPane();

		this.updatingPane = new UpdatingPane();
	}

	public void changePane(GridPane pane) {
		this.getChildren().clear();
		this.getChildren().add(backgroundStartScreen);
		this.getChildren().add(pane);

		TranslateTransition transition = new TranslateTransition();
		transition.setFromX(-1120);
		transition.setToX(0);
		transition.setDuration(Duration.seconds(0.3));
		transition.setNode(pane);
		transition.play();
	}
}
