package PaooGame;

import PaooGame.Characters.Character;

import java.awt.*;

public class Energy {
    public void draw(Graphics g) {
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", Font.BOLD, 25));
        if (Character.Player.Energy / 100 <= 100) {
            g.drawString("Player Energy: " + Character.Player.Energy / 100, 550, 30);
        }
        else {
            g.drawString("Player Energy: " + 100, 550, 30);
        }

        if (Character.PrietenBetiv != null && Character.PrietenBetiv.Energy / 100 <= 100) {
            g.drawString("Enemy Energy: " + Character.PrietenBetiv.Energy / 100, 550, 50);
        }
        else {
            g.drawString("Enemy Energy: " + 100, 550, 50);
        }
    }
}