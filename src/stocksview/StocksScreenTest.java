package stocksview;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StocksScreenTest extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		StackPane root = new StackPane();
		StocksScreen stocksScreen = new StocksScreen();
		stocksScreen.getStocksList().addStockCell(new StockCell());
		stocksScreen.getStocksList().addStockCell(new StockCell());
		root.getChildren().add(stocksScreen);
		
		Scene scene = new Scene(root,250,500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}