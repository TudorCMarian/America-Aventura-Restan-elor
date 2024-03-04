package PaooGame.Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/*! \class public class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets {
    /// Referinte catre elementele grafice (dale) utilizate in joc.


    public static BufferedImage map;

    public static BufferedImage[][] Player = new BufferedImage[4][9];
    public static BufferedImage[][] PrietenBetiv = new BufferedImage[4][9];
    public static BufferedImage[][] Student = new BufferedImage[4][9];
    public static BufferedImage[][] Secretara = new BufferedImage[4][9];
    public static BufferedImage[][] FinalBoss = new BufferedImage[4][9];
    public static BufferedImage Paper;
    public static BufferedImage Beer;

    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init() {
        /// Se creaza temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
        SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/MapR.png"));


        /// Se obtin subimaginile corespunzatoare elementelor necesare.
        map = sheet.cropmap(0, 0);
        SpriteSheet sheet2 = null;
        SpriteSheet sheet3 = null;
        SpriteSheet sheet4 = null;
        SpriteSheet sheet5 = null;
        SpriteSheet sheet6 = null;

        try {
            sheet2 = new SpriteSheet(ImageLoader.LoadImage("/textures/tudor_bun.png"));
            sheet3 = new SpriteSheet(ImageLoader.LoadImage("/textures/prieten betiv 2.png"));
            sheet4 = new SpriteSheet(ImageLoader.LoadImage("/textures/doamna_secretara.png"));
            sheet5 = new SpriteSheet(ImageLoader.LoadImage("/textures/studenti.png"));
            sheet6 = new SpriteSheet(ImageLoader.LoadImage("/textures/final_boss.png"));

        } catch (Exception e) {
            System.out.println("Eroare la incarcare personaje");
        }

        int i, j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 9; j++) {
                Player[i][j] = sheet2.cropCharacter(j, i + 8);
                PrietenBetiv[i][j] = sheet3.cropCharacter(j, i + 8);
                Secretara[i][j] = sheet4.cropCharacter(j, i + 8);
                Student[i][j] = sheet5.cropCharacter(j, i + 8);
                FinalBoss[i][j] = sheet6.cropCharacter(j, i + 8);
            }
        }
        try {
            Paper = ImageIO.read(new File(System.getProperty("user.dir") + "/res/textures/papers.png"));
            Beer = ImageIO.read(new File(System.getProperty("user.dir") + "/res/textures/bere.png"));
        } catch (Exception e) {
            System.out.println("eroare");
        }

    }
}
