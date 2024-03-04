package PaooGame.Characters;

import PaooGame.Graphics.Assets;

import java.awt.*;

public class DrunkFriend extends PaooGame.Characters.Character {
    public static int speed = 3;

    public DrunkFriend(int id) {
        /// Apel al constructorului clasei de baza
        super(Assets.PrietenBetiv, id, speed);
    }

    public void Update(int frame) {
        if (frame % 5 == 0) {
            posy++;
            posy = posy % 9;

        }
        int x1, y1, x2, y2;
        x1 = this.getX();
        y1 = this.getY();
        x2 = PaooGame.Characters.Character.getPlayer().getX();
        y2 = PaooGame.Characters.Character.getPlayer().getY();


        if (x1 < x2) {
            x++;
            posx = 3;

        } else {
            x--;
            posx = 1;
        }
        if (y1 < y2) {
            y++;
            posx = 2;
        } else {
            y--;
            posx = 0;
        }
        if (getBound().intersects(PaooGame.Characters.Character.Player.getBound())) {
            PaooGame.Characters.Character.Player.Energy -= 5;

            //setPickedup(true);
        }

    }

    public int getId() {
        return this.id;
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x, y, PaooGame.Characters.Character.Character_WIDTH, PaooGame.Characters.Character.Character_HEIGHT);
    }


}


