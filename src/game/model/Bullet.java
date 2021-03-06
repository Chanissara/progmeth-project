package game.model;

import constants.ConfigConstant;
import game.logic.MapCell;
import game.logic.MapCellHolder;
import game.logic.Renderable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bullet extends Entity implements Renderable {

	private double speed;
	private int direction;
	private int damage;
	private Ship shooter;
	private Image image;

	public Ship getShooter() {
		return shooter;
	}

	public int getDamage() {
		return damage;
	}

	public Bullet(double x, double y, double speed, int direction, int damage, Ship shooter) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.direction = direction;
		this.damage = damage;
		this.shooter = shooter;
		this.radius = 5;
		this.z = 100;
		if (shooter instanceof PlayerShip) {
			this.image = ConfigConstant.Resource.BULLET_IMAGE;
			ConfigConstant.Resource.LASER_SOUND.play(ConfigConstant.volumeSFX);
		} else {
			this.image = ConfigConstant.Resource.BULLET_ENEMY_IMAGE;
			ConfigConstant.Resource.LASER_SOUND.play(ConfigConstant.volumeSFX);
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		this.x += Math.cos(Math.toRadians(this.direction)) * speed;
		this.y += Math.sin(Math.toRadians(this.direction)) * speed;
		if (x > ConfigConstant.mapCellWidth + radius || x < -radius || y > ConfigConstant.mapCellHeight + radius
				|| y < -radius) {
			this.destroyed = true;
		}
		MapCell mc = MapCellHolder.instance.getPlayerCell();
		for (Entity entity : mc.getEntities()) {
			if (entity instanceof Ship && entity != this.getShooter() && this.isCollideWith(entity)) {
				this.destroyed = true;
				((Ship) entity).hit(this.getDamage());
				if (shooter instanceof PlayerShip) {
					mc.addNewEffect(new HitEffect(this.x, this.y, 6, true));
				} else {
					mc.addNewEffect(new HitEffect(this.x, this.y, 6, false));
				}
			}

		}
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.translate(x, y);
		gc.rotate(this.direction + 90);
		gc.drawImage(image, -image.getWidth() / 2, -image.getHeight() / 2);
		gc.rotate(-this.direction - 90);
		gc.translate(-x, -y);
	}

}
