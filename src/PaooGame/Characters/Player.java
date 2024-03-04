package PaooGame.Characters;

import PaooGame.Graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends PaooGame.Characters.Character {
    public static int speed=7;
    private boolean isPickingUpPaper;
    private BufferedImage[] animationFrames;


    public Player(int id)
    {
        /// Apel al constructorului clasei de baza
        super(Assets.Player, id, speed);
    }

    public void Update(int frame)
    {
        if(frame % 5 == 0)
        {
            posy++;
            posy = posy % 9;
        }

       /* if (isPickingUpPaper) {
            // Update position, image, or any other animation-related logic using the animationFrames
            // ...

            // Check if the picking up animation is complete
            if (animationFinished()) {
                isPickingUpPaper = false;
                // Continue with normal behavior...
            }
        }*/
    }
    @Override
    public Rectangle getBound(){
        return new Rectangle(x, y, PaooGame.Characters.Character.Character_WIDTH, PaooGame.Characters.Character.Character_HEIGHT);
    }



    /*public void pickUpPaper() {
        // Code for picking up a paper...
        isPickingUpPaper = true;
        animationFrames = loadPickingUpAnimationFrames();
    }
*/
  /*  private BufferedImage[] loadPickingUpAnimationFrames() {
        // Load and return the animation frames for picking up a paper
        // ...
    }

    private boolean animationFinished() {
        // Determine if the picking up animation has reached its last frame
        // ...
    }*/

}


