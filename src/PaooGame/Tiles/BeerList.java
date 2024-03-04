package PaooGame.Tiles;
import PaooGame.Characters.Character;
import PaooGame.DBHandler;
import PaooGame.Game;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class BeerList {

    static List<BeerTile> List = new ArrayList<>();

     {
        if (Game.level == 1) {
            addBeer(12, 10);
            addBeer(7, 27);
            addBeer(21, 15);


        }
    }
        public  void addBeer(int x, int y) {
            List.add(new BeerTile(6, x * Tile.CONSUMABLE_WIDTH, y * Tile.CONSUMABLE_HEIGHT));
        }

        public  void draw(Graphics g) {

            for (BeerTile beer : List) {
                if (!beer.isPickedup())
                    beer.DrawPaper(g, beer.x, beer.y);

            }
        }

    public  void Update() {
        Iterator<BeerTile> iterator = List.iterator();
        while (iterator.hasNext()) {
            BeerTile beer = iterator.next();
            if (beer.getBound().intersects(Character.Player.getBound())) {
                beer.setPickedup(true);
                try {
                    DBHandler.updateHighestScore(Game.scor, Character.Player.getId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                iterator.remove();

                // Decrease player's energy and speed
                Character.Player.speed -= 5;
                Character.Player.Energy -= 1000;
            }
        }
    }
        public  int returnSize(){
            return List.size();
        }
        public void clear(){
         List.clear();
        }
}
