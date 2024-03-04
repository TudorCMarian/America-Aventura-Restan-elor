package PaooGame.Tiles;

import PaooGame.Game;
import PaooGame.Graphics.Assets;

import java.awt.*;


public class PaperTile extends Tile {
    public int x, y;

    /*! \fn public PaperTile(int id)
        \brief Constructorul de initializare al clasei

        \param id Id-ul dalei util in desenarea hartii.
     */
    public PaperTile(int id, int x, int y) {
        super(Assets.Paper, id);
        this.x = x;
        this.y = y;
        /// Apel al constructorului clasei de baza
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
