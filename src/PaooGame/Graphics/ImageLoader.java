package PaooGame.Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/*! \class public class ImageLoader
    \brief Clasa ce contine o metoda statica pentru incarcarea unei imagini in memorie.
 */
public class ImageLoader {
    /*! \fn  public static BufferedImage loadImage(String path)
        \brief Incarca o imagine intr-un obiect BufferedImage si returneaza o referinta catre acesta.

        \param path Calea relativa pentru localizarea fisierul imagine.
     */
    public static BufferedImage LoadImage(String path) {
        try {
            InputStream input = ImageLoader.class.getResourceAsStream(path);
            if (input != null) {
                return ImageIO.read(input);
            } else {
                System.err.println("Error loading image: " + path);
                return null;
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + path);
            e.printStackTrace();
            return null;
        }
    }
}

