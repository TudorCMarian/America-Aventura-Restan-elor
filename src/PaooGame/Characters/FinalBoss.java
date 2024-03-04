package PaooGame.Characters;

import PaooGame.Graphics.Assets;

import java.awt.*;

public class FinalBoss extends Character {
    private int damage;
    public static int speed = 4;


    public FinalBoss(int id) {

        /// Apel al constructorului clasei de baza
        super(Assets.FinalBoss, id, speed);
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
            PaooGame.Characters.Character.Player.Energy -= 20;
        }

    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(x, y, PaooGame.Characters.Character.Character_WIDTH, PaooGame.Characters.Character.Character_HEIGHT);
    }

    // Add any additional methods specific to the final boss

    public void useSpecialAbility() {
        // Implement the special ability of the final boss
        // This method can perform actions or cause damage to the player
    }

    public int getDamage() {
        return damage;
    }
}