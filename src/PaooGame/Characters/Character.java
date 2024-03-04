package PaooGame.Characters;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;


/*! \class public class Tile
    \brief Retine toate dalele intr-un vector si ofera posibilitatea regasirii dupa un id.
 */
abstract public class Character {
    private static Random random = new Random();

    private static final int NO_TILES = 32;
    /// De remarcat ca urmatoarele dale sunt statice si publice. Acest lucru imi permite sa le am incarcate
    /// o singura data in memorie
    private int drawnOnce = 0;

    public static Character Player = new Player(random.nextInt(100));
    public static Character PrietenBetiv = new DrunkFriend(5);
    //public static Character Tarzan = new Tarzan(6);
    public static Character Secretara = new Secretara(7);
    public static List<Student> Students  = new ArrayList<Student>();

    public static Character FinalBoss = new FinalBoss(99);
    public int maxEnergy;
    public int Energy;

    public static final int Character_WIDTH = 80;                       /*!< Latimea unei dale.*/
    public static final int Character_HEIGHT = 80;                       /*!< Inaltimea unei dale.*/
    public int speed = 0;

    protected BufferedImage[][] img;                                    /*!< Imaginea aferenta tipului de dala.*/
    protected final int id;/*!< Id-ul unic aferent tipului de dala.*/
    public int posx, posy;
    public int x, y;


    public static void createStudents() {
        List<Student> studentList = new ArrayList<>();

        // Create and add Student instances to the studentList
        Student student1 = new Student(8);
        student1.setX(103);
        student1.setY(90);
        studentList.add(student1);

        Student student2 = new Student(9);
        student2.setX(200);
        student2.setY(90);
        studentList.add(student2);

        Student student3 = new Student(10);
        student3.setX(300);
        student3.setY(80);
        studentList.add(student3);

        Students = studentList;
    }


    public Character(BufferedImage[][] image, int idd, int speed) {

        this.speed = speed;
        img = image;
        id = idd;
        if(id == 6)
            this.speed = 0;
        posx = 1;
        posy = 1;
        x = 130;
        y = 250;
        if(this.id == 6){
            x = 200+random.nextInt(100);
            y = 500;
        }
        maxEnergy = 10400;
        Energy = 10400;
    }


    /*! \fn public void Update()
        \brief Actualizeaza proprietatile dalei.
     */
    public void Update(int frame) {
        if (Player.intersects(PrietenBetiv)) {
            System.out.println(Player.Energy);
        }

    }

    /*! \fn public void Draw(Graphics g, int x, int y)
        \brief Deseneaza in fereastra dala.

        \param g Contextul grafic in care sa se realizeze desenarea
        \param x Coordonata x in cadrul ferestrei unde sa fie desenata dala
        \param y Coordonata y in cadrul ferestrei unde sa fie desenata dala
     */

    public Rectangle getRectangle() {

        return new Rectangle(x - Character_WIDTH, y - Character_HEIGHT, Character_WIDTH, Character_HEIGHT);

    }

    public boolean intersects(Character o) {
        Rectangle r1 = getRectangle();

        Rectangle r2 = o.getRectangle();

        return r1.intersects(r2);

    }

    public void DrawCharacter(Graphics g) {
            /// Desenare dala
            g.drawImage(img[posx][posy], x, y, Character_WIDTH, Character_HEIGHT, null);
            drawnOnce = 1;
    }

    public void atingere() {
        if (getBound().intersects(Character.PrietenBetiv.getBound())) {
            PrietenBetiv.Energy -= 95;

        }
    }

    public boolean atingereSecretara() {
        if (getBound().intersects(Character.Secretara.getBound())) {
            return true;
        }
        return false;
    }
    /*! \fn public boolean IsSolid()
        \brief Returneaza proprietatea de dala solida (supusa coliziunilor) sau nu.
     */

    public static Character getPlayer() {
        return Player;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rectangle getBound() {
        return null;
    }
    public int getId() {return id;}

    public static void removePrietenBetiv() {
        PrietenBetiv = null;
    }
}
