package market;

import java.util.ArrayList;
import java.util.List;

import constants.ConfigConstant;
import game.logic.Player;
import game.model.Item;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class TraderScreen extends StackPane {

	private static BorderPane front;
	private static BorderPane TradePane = new BorderPane();
	private static Pane upgradePane = new UpgradePane();

	public static void togglePane() {
		if (front.getCenter().equals(TradePane)) {
			front.setCenter(upgradePane);
			FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), upgradePane);
			fadeTransition.setFromValue(0);
			fadeTransition.setToValue(1);
			fadeTransition.play();
		} else {
			front.setCenter(TradePane);
			FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), TradePane);
			fadeTransition.setFromValue(0);
			fadeTransition.setToValue(1);
			fadeTransition.play();
		}
	}

	private Trader trader;
	private List<TraderScreenItemCell> itemCells;
	private VBox itemsOnSale;
	private TraderItemPricesList priceList;

	public TraderScreen(Trader trader) {
		super();
		this.setMaxSize(400, 400);
		front = new BorderPane();
		this.itemCells = new ArrayList<>();
		this.trader = trader;
		this.priceList = new TraderItemPricesList(trader);

		front.setCenter(TradePane);

		this.itemsOnSale = new VBox();
		this.itemsOnSale.setAlignment(Pos.TOP_CENTER);

		front.setPadding(new Insets(20, 0, 0, 10));

		((BorderPane) front.getCenter()).setCenter(itemsOnSale);
		((BorderPane) front.getCenter()).setRight(this.priceList);

		updateItemList();

		TraderScreenTop top = new TraderScreenTop(trader);
		front.setTop(top);

		Canvas back = new Canvas(400, 400);
		back.getGraphicsContext2D().setEffect(new Glow(1));
		back.getGraphicsContext2D().drawImage(ConfigConstant.Resource.PANEL_BACKGROUND_M, 0, 0, 400, 400);

		back.getGraphicsContext2D().setEffect(null);

		this.getChildren().addAll(back, front);
	}

	public void updateItemList() {
		this.itemCells.clear();
		this.itemsOnSale.getChildren().clear();
		Label buyLabel = new Label("Buy");
		buyLabel.setFont(ConfigConstant.Resource.HUD_FONT);
		this.itemsOnSale.getChildren().add(buyLabel);

		for (Item item : trader.getItemsOnSale()) {
			// create a TraderScreenItemCell(see below) for the item
			TraderScreenItemCell itemCell = new TraderScreenItemCell(item, trader);

			// player can click the cell to buy item
			itemCell.setOnMouseClicked(event -> {
				Item product = trader.playerBuyItem(item);
				if (product != null) {
					Player.instance.addItemtoInventory(product);
					updateItemList();
					System.out.println(Player.instance.getInventory() + " " + Player.instance.getMoney());
				}
			});

			this.itemCells.add(itemCell);
			this.itemsOnSale.getChildren().add(itemCell);
		}
	}

	public class TraderScreenItemCell extends HBox {
		class ItemCellIcon extends Canvas {
			public ItemCellIcon(Item item) {
				super(40, 40);
				GraphicsContext gc = this.getGraphicsContext2D();
				Image icon = Item.ItemType.getItemIcon(item.getItemType());
				gc.drawImage(icon, 0, 0, 40, 40);
			}
		}

		Item item;
		private ItemCellIcon itemCellIcon;
		private Label priceLabel;

		public TraderScreenItemCell(Item item, Trader trader) {
			super();
			this.setPrefHeight(50);
			this.item = item;
			this.setAlignment(Pos.CENTER);
			this.setSpacing(5);
			this.itemCellIcon = new ItemCellIcon(item);
			this.priceLabel = new Label(Integer.toString((int) (trader.getBuyPriceMultipliers().get(item.getItemType())
					* Market.getItemPrice(item.getItemType()))));
			priceLabel.setFont(ConfigConstant.Resource.HUD_FONT);

			this.getChildren().addAll(itemCellIcon, priceLabel);
		}
	}

	public class TraderScreenTop extends BorderPane {
		public TraderScreenTop(Trader trader) {
			super();
			Label name = new Label("Trader : " + trader.getName());
			name.setFont(ConfigConstant.Resource.HUD_MID_FONT);
			BorderPane.setAlignment(name, Pos.CENTER);
			this.setCenter(name);
		}
	}
}