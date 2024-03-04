package PaooGame;

import PaooGame.Characters.DrunkFriend;
import PaooGame.Characters.Student;
import PaooGame.GameWindow.GameWindow;
import PaooGame.Characters.Character;
import PaooGame.Tiles.BeerList;
import PaooGame.Tiles.PaperList;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static PaooGame.Graphics.Assets.Init;

public class Game implements Runnable {
    private boolean paidTaxes = false;
    public Energy Energy;

    public enum states {MENU, INSTRUCTIONS, IN_GAME, END_GAME}

    public static int highlighted = 0;
    public static int firstStarted = 1;
    public static states currentState;
    private GameWindow wnd;        /*!< Fereastra in care se va desena tabla jocului*/
    private boolean runState;   /*!< Flag ce starea firului de executie.*/
    private Thread gameThread; /*!< Referinta catre thread-ul de update si draw al ferestrei*/
    private BufferStrategy bs;/*!< Referinta catre un mecanism cu care se organizeaza memoria complexa pentru un canvas.*/
    private int frame = 0;
    public static int level = 1;
    public static int[][] coliziune;
    private static PaperList paperList;
    private static BeerList beerList;
    private List<Student> students;
    public static int scor = 0;
    private Graphics g;          /*!< Referinta catre un context grafic.*/

    /*! \fn public Game(String title, int width, int height)
        \brief Constructor de initializare al clasei Game.

        Acest constructor primeste ca parametri titlul ferestrei, latimea si inaltimea
        acesteia avand in vedere ca fereastra va fi construita/creata in cadrul clasei Game.

        \param title Titlul ferestrei.
        \param width Latimea ferestrei in pixeli.
        \param height Inaltimea ferestrei in pixeli.
     */
    public Game(String title, int width, int height) {
        Energy = new Energy();
        currentState = states.MENU;
        /// Obiectul GameWindow este creat insa fereastra nu este construita
        /// Acest lucru va fi realizat in metoda init() prin apelul
        /// functiei BuildGameWindow();
        wnd = new GameWindow(title, width, height);
        /// Resetarea flagului runState ce indica starea firului de executie (started/stoped)
        runState = false;
    }

    /*! \fn private void init()
        \brief  Metoda construieste fereastra jocului, initializeaza aseturile, listenerul de tastatura etc.

        Fereastra jocului va fi construita prin apelul functiei BuildGameWindow();
        Sunt construite elementele grafice (assets): dale, player, elemente active si pasive.

     */
    private void InitGame() {
        //wnd = new GameWindow("Schelet Proiect PAOO", Tile.MAP_WIDTH, Tile.MAP_HEIGHT);
        /// Este construita fereastra grafica.
        wnd.BuildGameWindow();

        /// Se incarca toate elementele grafice (dale)
        Init();
        students = new ArrayList<>();
        paperList = new PaperList();
        beerList = new BeerList();
        readCollision();
    }


    /*! \fn public void run()
        \brief Functia ce va rula in thread-ul creat.

        Aceasta functie va actualiza starea jocului si va redesena tabla de joc (va actualiza fereastra grafica)
     */
    public void run() {
        /// Initializeaza obiectul game
        InitGame();
        long oldTime = System.nanoTime();   /*!< Retine timpul in nanosecunde aferent frame-ului anterior.*/
        long curentTime;                    /*!< Retine timpul curent de executie.*/

        /// Apelul functiilor Update() & Draw() trebuie realizat la fiecare 16.7 ms
        /// sau mai bine spus de 60 ori pe secunda.

        final int framesPerSecond = 60; /*!< Constanta intreaga initializata cu numarul de frame-uri pe secunda.*/
        final double timeFrame = 1000000000 / framesPerSecond; /*!< Durata unui frame in nanosecunde.*/

        /// Atat timp timp cat threadul este pornit Update() & Draw()
        while (runState == true) {
            /// Se obtine timpul curent
            curentTime = System.nanoTime();
            /// Daca diferenta de timp dintre curentTime si oldTime mai mare decat 16.6 ms
            if ((curentTime - oldTime) > timeFrame) {
                /// Actualizeaza pozitiile elementelor
                if (currentState == states.IN_GAME) {
                    Update();
                    if (PaperList.returnSize() == 0) {
                        if (level == 1) {
                            level++;
                            readCollision();
                            Character.Player.setX(550);
                            Character.Player.setY(1);
                            Character.createStudents();
                            paperList = new PaperList();
                            beerList.clear();
                            currentState = states.MENU;
                            firstStarted = 0;
                        } else if (level == 2 && paidTaxes) {
                            level++;
                            readCollision();
                            paperList = new PaperList();
                            currentState = states.MENU;
                            firstStarted = 0;

                        } else if (level == 3) {
                            // TODO: level 3
                            readCollision();
                            paperList = new PaperList();
                            currentState = states.END_GAME;
                        }

                    }

                    if (Character.Player.Energy / 100 <= 0) {
                        paperList = new PaperList();

                        Character.Player.setX(550);
                        Character.Player.setY(100);
                        if (Character.PrietenBetiv != null) {
                            Character.PrietenBetiv.setX(103);
                            Character.PrietenBetiv.setY(90);
                            Character.PrietenBetiv.Energy = Character.PrietenBetiv.maxEnergy;
                        }

                        Character.Player.Energy = Character.Player.maxEnergy;
                        firstStarted = 1;
                        level = 1;
                        readCollision();
                        scor = 0;
                        currentState = states.MENU;


                    }
                    if (Character.PrietenBetiv != null && Character.PrietenBetiv.Energy / 100 <= 0) {
                        Update();
                        level=2;
                        readCollision();
                        Character.Player.setX(550);
                        Character.Player.setY(1);
                        Character.createStudents();
                        paperList = new PaperList();
                        beerList.clear();
                        currentState = states.MENU;
                        firstStarted = 0;


                    }
                }
                /// Deseneaza elementele grafica in fereastra.
                Draw();
                oldTime = curentTime;
            }
        }

    }

    /*! \fn public synchronized void start()
        \brief Creaza si starteaza firul separat de executie (thread).

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StartGame() {
        if (runState == false) {
            /// Se actualizeaza flagul de stare a threadului
            runState = true;
            /// Se construieste threadul avand ca parametru obiectul Game. De retinut faptul ca Game class
            /// implementeaza interfata Runnable. Threadul creat va executa functia run() suprascrisa in clasa Game.
            gameThread = new Thread(this);
            /// Threadul creat este lansat in executie (va executa metoda run())
            gameThread.start();
        } else {
            /// Thread-ul este creat si pornit deja
            return;
        }
    }

    /*! \fn public synchronized void stop()
        \brief Opreste executie thread-ului.

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StopGame() {
        if (runState == true) {
            /// Actualizare stare thread
            runState = false;
            /// Metoda join() arunca exceptii motiv pentru care trebuie incadrata intr-un block try - catch.
            try {
                /// Metoda join() pune un thread in asteptare panca cand un altul isi termina executie.
                /// Totusi, in situatia de fata efectul apelului este de oprire a threadului.
                gameThread.join();
            } catch (InterruptedException ex) {
                /// In situatia in care apare o exceptie pe ecran vor fi afisate informatii utile pentru depanare.
                ex.printStackTrace();
            }
        } else {
            /// Thread-ul este oprit deja.
            return;
        }
    }

    /*! \fn private void Update()
        \brief Actualizeaza starea elementelor din joc.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Update() {
        frame++;
        frame = frame % 60;
        Character.Player.Update(frame);
        if (Character.PrietenBetiv != null) {
            Character.PrietenBetiv.Update(frame);
        }
        if (level == 2) {
            if (Character.Player.atingereSecretara()) {
                paidTaxes = true;
            }
            for (Student student : Character.Students) {
                student.Update(frame);
            }

        }
        if (level == 3) {
            Character.FinalBoss.Update(frame);
        }
        paperList.Update();
        beerList.Update();


    }

    /*! \fn private void Draw()
        \brief Deseneaza elementele grafice in fereastra coresponzator starilor actualizate ale elementelor.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Draw() {
        if (currentState == states.IN_GAME) {
            /// Returnez bufferStrategy pentru canvasul existent
            bs = wnd.GetCanvas().getBufferStrategy();
            /// Verific daca buffer strategy a fost construit sau nu
            if (bs == null) {
                /// Se executa doar la primul apel al metodei Draw()
                try {
                    /// Se construieste tripul buffer
                    wnd.GetCanvas().createBufferStrategy(3);
                    return;
                } catch (Exception e) {
                    /// Afisez informatii despre problema aparuta pentru depanare.
                    e.printStackTrace();
                }
            }
            /// Se obtine contextul grafic curent in care se poate desena.
            g = bs.getDrawGraphics();
            /// Se sterge ce era
            Image gameBg = null;
            //Incarcare harti pentru ambele levele
            if (level == 1) {
                gameBg = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "/res/textures/MapR.png");
            } else if (level == 2) {
                gameBg = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "/res/textures/LVL2_Map.png");
            } else if (level == 3) {
                gameBg = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "/res/textures/nivel3.png");
            }
            g.drawImage(gameBg, 0, 0, wnd.GetWndWidth(), wnd.GetWndHeight(), null);
            /// operatie de desenare
            // ...............
            //Tile.mapTile.Drawmap(g);
            Character.Player.DrawCharacter(g);
            if (level == 1) {
                if (Character.PrietenBetiv == null) {
                    Character.PrietenBetiv = new DrunkFriend(5);
                }
                Character.PrietenBetiv.DrawCharacter(g);
            }
            if (level == 2) {
                // cod stergere prieten
                Character.removePrietenBetiv();
                Character.Secretara.DrawCharacter(g);
                for (Student student : Character.Students) {
                    student.DrawCharacter(g);
                    // Draw other elements specific to each student
                }
            }
            if (level == 3) {
                Character.FinalBoss.DrawCharacter(g);

            }
            paperList.draw(g);
            beerList.draw(g);

            // g.drawRect(1 * Tile.TILE_WIDTH, 1 * Tile.TILE_HEIGHT, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

            Energy.draw(g);
            // end operatie de desenare
            /// Se afiseaza pe ecran


            bs.show();

            /// Elibereaza resursele de memorie aferente contextului grafic curent (zonele de memorie ocupate de
            /// elementele grafice ce au fost desenate pe canvas).
            g.dispose();


        } else if (currentState == states.MENU) {
            /// Returnez bufferStrategy pentru canvasul existent
            bs = wnd.GetCanvas().getBufferStrategy();
            /// Verific daca buffer strategy a fost construit sau nu
            if (bs == null) {
                /// Se executa doar la primul apel al metodei Draw()
                try {
                    /// Se construieste tripul buffer
                    wnd.GetCanvas().createBufferStrategy(3);
                    return;
                } catch (Exception e) {
                    /// Afisez informatii despre problema aparuta pentru depanare.
                    e.printStackTrace();
                }
            }
            /// Se obtine contextul grafic curent in care se poate desena.
            g = bs.getDrawGraphics();
            /// Se sterge ce era
            g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());


            ///Incarcare pagina meniu
            Image menuBg = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "/res/textures/logo_joc 2.png");
            g.drawImage(menuBg, 0, 0, wnd.GetWndWidth(), wnd.GetWndHeight(), null);


            g.setColor(new Color(0, 0, 0));
            g.setFont(new Font("Algerian", Font.PLAIN, 50));

            if (highlighted == 0) {
                g.setColor(new Color(255, 255, 255));
            } else {
                g.setColor(new Color(110, 2, 168));
            }

            if (firstStarted == 1) {
                g.drawString("PLAY", 600, 600);
            } else if (firstStarted == 0) {
                g.drawString("CONTINUE", 550, 600);
            }

            if (highlighted == 1) {
                g.setColor(new Color(255, 255, 255));
            } else {
                g.setColor(new Color(110, 2, 168));
            }
            g.drawString("QUIT", 600, 650);


            bs.show();
            /// Elibereaza resursele de memorie aferente contextului grafic curent (zonele de memorie ocupate de
            /// elementele grafice ce au fost desenate pe canvas).
            g.dispose();
        }

        if (currentState == states.INSTRUCTIONS) {
            bs = wnd.GetCanvas().getBufferStrategy();
            if (bs == null) {
                try {
                    wnd.GetCanvas().createBufferStrategy(3);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            g = bs.getDrawGraphics();
            g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());


            // Draw background image
            Image instructionsBg = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "/res/textures/background.jpg");
            g.drawImage(instructionsBg, 0, 0, wnd.GetWndWidth(), wnd.GetWndHeight(), null);

            // Set font and colors for text
            g.setFont(new Font("Algerian", Font.PLAIN, 24));
            g.setColor(new Color(255, 255, 255));

            if(level == 1){
                // Draw instructions text
                String controlsText = "Controls:";
                String controlsInfo = "- Foloseste sagetile pentru a te misca pe harta";
                String storylineText = "Storyline:";
                String storylineInfo = "- Culege hartiile si evita berea pentru a putea ajunge la facultate";
                String enemiesText = "Enemies:";
                String enemiesInfo = "- Prietenul beat al protagonistului";

                // Draw controls information
                g.drawString(controlsText, 100, 200);
                g.drawString(controlsInfo, 100, 240);

                // Draw storyline information
                g.drawString(storylineText, 100, 300);
                g.drawString(storylineInfo, 100, 340);

                // Draw enemies information
                g.drawString(enemiesText, 100, 400);
                g.drawString(enemiesInfo, 100, 440);

                bs.show();
                g.dispose();
            } else if (level == 2) {
                // Draw instructions text
                String controlsText = "Controls:";
                String controlsInfo = "- Foloseste sagetile pentru a te misca pe harta";
                String storylineText = "Storyline:";
                String storylineInfo = "- Fereste-te de studentii care invata in borcane si culege hartiile. " +
                                        "Pentru a finaliza nivelul plateste taxa de examen la Secretara";
                String enemiesText = "Enemies:";
                String enemiesInfo = "- Studenti calculatoristi";

                // Draw controls information
                g.drawString(controlsText, 100, 200);
                g.drawString(controlsInfo, 100, 240);

                // Draw storyline information
                g.drawString(storylineText, 100, 300);
                g.drawString(storylineInfo, 100, 340);

                // Draw enemies information
                g.drawString(enemiesText, 100, 400);
                g.drawString(enemiesInfo, 100, 440);

                bs.show();
                g.dispose();
            }
            else {
                // Draw instructions text
                String controlsText = "Controls:";
                String controlsInfo = "- Foloseste sagetile pentru a te misca pe harta";
                String storylineText = "Storyline:";
                String storylineInfo = "- Dovedeste-i profesorului ca ai invatat pentru examen!";
                String enemiesText = "Enemies:";
                String enemiesInfo = "- Profesorul";

                // Draw controls information
                g.drawString(controlsText, 100, 200);
                g.drawString(controlsInfo, 100, 240);

                // Draw storyline information
                g.drawString(storylineText, 100, 300);
                g.drawString(storylineInfo, 100, 340);

                // Draw enemies information
                g.drawString(enemiesText, 100, 400);
                g.drawString(enemiesInfo, 100, 440);

                bs.show();
                g.dispose();
            }

        }
        if (currentState == states.END_GAME) {
            bs = wnd.GetCanvas().getBufferStrategy();
            if (bs == null) {
                try {
                    wnd.GetCanvas().createBufferStrategy(3);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            g = bs.getDrawGraphics();
            g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());


            Image menuBg = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "/res/textures/meniu.png");
            g.drawImage(menuBg, 0, 0, wnd.GetWndWidth(), wnd.GetWndHeight(), null);


            g.setColor(new Color(0, 0, 0));
            g.setFont(new Font("Arial", Font.BOLD, 37));

            g.setColor(new Color(255, 255, 255));

            g.drawString("Felicitari!", 5, 495);


            try {
                DBHandler.updateHighestScore(Game.scor, Character.Player.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            bs.show();
            g.dispose();
        }
    }

    public void readCollision() {
        Scanner input;

        coliziune = new int[13][25];


        // read in the data
        try {
            if (level == 1) {
                input = new Scanner(new File(System.getProperty("user.dir") + "/res/textures/matricecoliziuni.txt"));
            } else if (level == 2) {
                input = new Scanner(new File(System.getProperty("user.dir") + "/res/textures/matricecoliziuni2.txt"));
            } else {
                input = new Scanner(new File(System.getProperty("user.dir") + "/res/textures/matricecoliziuni3.txt"));
            }
            for (int i = 0; i < 13; ++i) {
                for (int j = 0; j < 25; ++j) {
                    if (input.hasNextInt()) {
                        coliziune[i][j] = input.nextInt();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}


