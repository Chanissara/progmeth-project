package game.model;

import constants.ConfigConstant;
import game.logic.Renderable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import market.Trader;

public class SpaceStationEntity extends Entity implements Renderable {

	private Image image;
	private double direction;
	private double turnRate;
	private Trader trader;

	public SpaceStationEntity(double x, double y) {
		super();
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.image = ConfigConstant.Resource.SPACE_STATION;
		this.direction = 0;
		this.turnRate = 0.05;
		this.z = -1000;
		this.radius = this.image.getWidth() / 2;
		this.trader = null;
	}
	
	public Trader getTrader() {
		return this.trader;
	}
	
	public void setTrader(Trader trader) {
		this.trader = trader;
	}

	public void turn(boolean left) {
		if (left) {
			this.direction -= this.turnRate;
			if (this.direction < 0)
				this.direction += 360;
		} else {
			this.direction += this.turnRate;
			if (this.direction >= 360)
				this.direction -= 360;
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		this.turn(true);

	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.translate(x, y);
		gc.rotate(this.direction);
		gc.drawImage(image, -this.image.getWidth() / 2, -this.image.getHeight() / 2);
		gc.rotate(-this.direction);
		gc.translate(-x, -y);
	}

}
