package PaooGame.Tiles;
import PaooGame.Game;
import PaooGame.Graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BeerTile extends Tile{
    public int x, y;

    public BeerTile(int id, int x, int y) {
        super(Assets.Beer, id);
        this.x = x;
        this.y = y;
    }

    public Rectangle getBound() {
        return new Rectangle(x, y,Tile.CONSUMABLE_WIDTH, Tile.CONSUMABLE_HEIGHT);
    }

    @Override
    public void setPickedup(boolean pickedup) {

        if (!this.isPickedup() && pickedup) {
            Game.scor = Game.scor + 25;
            System.out.println(Game.scor);
        }
        super.setPickedup(pickedup);
    }
}
