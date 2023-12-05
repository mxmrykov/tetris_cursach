package screens;

import jaco.mp3.player.MP3Player;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;

public class Screens extends JFrame {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    final double width = screenSize.getWidth();
    final double height = screenSize.getHeight();
    private final JPanel panelContainer;
    private final CardLayout cardLayout;
    public Font font;
    public final JFrame frame;

    public Screens() {
        frame = new JFrame();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setPreferredSize(new Dimension(400, 560));
        frame.setPreferredSize(new Dimension(400, 560));
        setBounds((int) width / 2 - 200, (int) height / 2 - 280, 400, 560);
        frame.setLayout(new BorderLayout());
        panelContainer = new JPanel();
        cardLayout = new CardLayout();
        panelContainer.setLayout(cardLayout);
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/lib/LLPixel.ttf"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Game game = new Game(cardLayout, font, panelContainer);
        Home home = new Home(cardLayout, font, panelContainer);
        Scores scores = new Scores(cardLayout, font, panelContainer);

        home.game = game;
        home.scores = scores;
        game.home = home;
        game.scores = scores;

        panelContainer.add(home, "Home");
        panelContainer.add(scores, "Score");
        panelContainer.add(game, "Game");
        add(panelContainer);

        MP3Player mp3Player = new MP3Player(new File("src/java/lib/move.mp3"));


        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                mp3Player.play();
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    game.moveLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    game.moveRight();
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    game.rotate();
                }
            }
        });
    }
}
