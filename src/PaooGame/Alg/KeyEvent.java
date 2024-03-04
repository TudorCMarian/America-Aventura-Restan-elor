package PaooGame.Alg;

import PaooGame.Characters.Character;
import PaooGame.Game;
import PaooGame.Tiles.Tile;

import java.awt.event.KeyListener;

public class KeyEvent implements KeyListener {

    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == java.awt.event.KeyEvent.VK_UP) {
            if (Game.coliziune[((Character.Player.y - Character.Player.speed) / Tile.TILE_HEIGHT)][(Character.Player.x / Tile.TILE_WIDTH)] == 0) {
                Character.Player.y -= Character.Player.speed;
                Character.Player.posx = 0;
            }
        }
        if (e.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
            if (Game.coliziune[((Character.Player.y + Character.Player.speed) / Tile.TILE_HEIGHT)][(Character.Player.x / Tile.TILE_WIDTH)] == 0)
                Character.Player.y += Character.Player.speed;
            Character.Player.posx = 2;
        }
        if (e.getKeyCode() == java.awt.event.KeyEvent.VK_LEFT) {
            if (Game.coliziune[((Character.Player.y) / Tile.TILE_HEIGHT)][((Character.Player.x - Character.Player.speed) / Tile.TILE_WIDTH)] == 0)
                Character.Player.x -= Character.Player.speed;
            Character.Player.posx = 1;
        }
        if (e.getKeyCode() == java.awt.event.KeyEvent.VK_RIGHT) {
            if (Game.coliziune[((Character.Player.y) / Tile.TILE_HEIGHT)][((Character.Player.x + Character.Player.speed) / Tile.TILE_WIDTH)] == 0)
                Character.Player.x += Character.Player.speed;
            Character.Player.posx = 3;
        }

    }

    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {
        if (Game.currentState == Game.states.END_GAME) {
            if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                Game.currentState = Game.states.MENU;
            }
        }
        if (Game.currentState == Game.states.MENU) {
            if (e.getKeyCode() == java.awt.event.KeyEvent.VK_UP) {
                if (Game.highlighted == 0)
                    Game.highlighted = 1;
                else Game.highlighted = 0;
            }
            if (e.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
                if (Game.highlighted == 1)
                    Game.highlighted = 0;
                else Game.highlighted = 1;
            }

            if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                if (Game.highlighted == 0) {
                    Game.highlighted = 0;
                    Game.currentState = Game.states.IN_GAME;
                    Game.firstStarted = 0;
                } else System.exit(0);
            }
            /*if (e.getKeyCode() == java.awt.event.KeyEvent.VK_I) {
                Game.currentState = Game.states.INSTRUCTIONS;
            } else if (Game.currentState == Game.states.INSTRUCTIONS) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    Game.currentState = Game.states.MENU;
                }
                if(e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE){
                    Game.currentState = Game.states.MENU;
                }*/
        } else if (Game.currentState == Game.states.IN_GAME) {
            if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                if (Game.currentState == Game.states.IN_GAME) {
                    Game.currentState = Game.states.MENU;
                }
            }
            if (e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE) {
                Character.Player.atingere();
            }
            if (e.getKeyCode() == java.awt.event.KeyEvent.VK_I) {
                Game.currentState = Game.states.INSTRUCTIONS;
            }
        } else if (Game.currentState == Game.states.INSTRUCTIONS) {
            if (e.getKeyCode() == java.awt.event.KeyEvent.VK_I) {
                Game.currentState = Game.states.IN_GAME;
            }
        }
    }
}




