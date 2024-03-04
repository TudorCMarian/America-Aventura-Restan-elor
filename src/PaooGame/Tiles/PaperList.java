package PaooGame.Tiles;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import PaooGame.Characters.Character;
import PaooGame.DBHandler;
import PaooGame.Game;

public class PaperList {
    static List<PaperTile> List = new ArrayList<>();

    {
        if (Game.level == 1) {
            addPaper(7, 5);
            addPaper(40, 13);
            addPaper(5, 20);
            addPaper(20, 19);
            addPaper(10, 19);
            addPaper(16, 11);
            addPaper(19, 5);
            addPaper(23, 9);
            addPaper(30, 21);
            addPaper(35, 11);
            addPaper(35, 20);
        } else if(Game.level == 2) {
            addPaper(2, 7);
            addPaper(2, 12);
            addPaper(2, 16);
            addPaper(40, 7);
            addPaper(40, 11);
            addPaper(40, 16);
            addPaper(21, 5);
            addPaper(21, 8);
            addPaper(21, 16);
        }else{
            addPaper(2, 12);
            addPaper( 19, 4);
            addPaper(21, 5);
            addPaper(10, 11);
            addPaper(20,11);
//            addPaper();
//            addPaper();
//            addPaper();
        }
    }

    public void addPaper(int x, int y) {
        List.add(new PaperTile(6, x * Tile.CONSUMABLE_WIDTH, y * Tile.CONSUMABLE_HEIGHT));
    }


    public void draw(Graphics g) {

        for (PaperTile paper : List) {
            if (!paper.isPickedup())
                paper.DrawPaper(g, paper.x, paper.y);

        }
    }

    public void Update() {
        for (int i = 0; i < List.size(); i++) {
            PaperTile paper = List.get(i);
            if (paper.getBound().intersects(Character.Player.getBound())) {
                paper.setPickedup(true);
                List.remove(paper);
            }
        }
    }

    public static int returnSize() {
        return List.size();
    }
}
