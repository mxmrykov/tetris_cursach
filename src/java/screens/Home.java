package screens;

import database.DataBase;
import lib.ImagePanel;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Home extends JPanel {
    private final CardLayout cardLayout;
    private final Font font;
    private final JPanel panelContainer;
    public Game game;
    public Clip homeScreeClip;
    public final DataBase dataBase;
    public Scores scores;
    public JLabel score;
    public int biggest;
    public Home(CardLayout cardLayout, Font font, JPanel panelContainer) {
        this.cardLayout = cardLayout;
        this.font = font;
        this.panelContainer = panelContainer;
        dataBase = new DataBase();
        if (!dataBase.getAll().isEmpty()) {
            biggest = Integer.parseInt(dataBase.getAll().get(0)[1]);
        }
        try {
            URL url = new URL("https://dreamity.ru/internal/main_menu.wav");
            homeScreeClip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream( url );
            homeScreeClip.open(ais);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        add(homeScreen());
    }

    public JPanel homeScreen() {
        homeScreeClip.loop(Clip.LOOP_CONTINUOUSLY);
        ImagePanel mainPanel = generateMainPanel();
        JLabel mainTitle = generateTetrisTitle();
        JButton startNewGameButton = generateNewGameButton();
        JButton exitButton = generateExitButton();
        score = generateScoreTitle();
        updateScore(biggest);
        mainPanel.add(mainTitle);
        mainPanel.add(startNewGameButton);
        mainPanel.add(exitButton);
        mainPanel.add(generateScoresButton());
        mainPanel.add(score);
        mainPanel.setName("Home");
        return mainPanel;
    }

    public void updateScore(int value) {
        score.setText("Last record: " + value);
    }

    private ImagePanel generateMainPanel() {
        ImagePanel mainPanel = new ImagePanel();
        mainPanel.setPreferredSize(new Dimension(400, 550));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        return mainPanel;
    }

    private JLabel generateTetrisTitle() {
        JLabel mainTitle = new JLabel("Sigma Tetris");
        mainTitle.setBounds(0, 0, 150, 50);
        mainTitle.setFont(font.deriveFont(Font.BOLD, 48f));
        mainTitle.setForeground(Color.WHITE);
        Border mainTitleborder = mainTitle.getBorder();
        Border marginTopFifty = new EmptyBorder(35, 0, 50, 0);
        mainTitle.setBorder(new CompoundBorder(mainTitleborder, marginTopFifty));
        mainTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        return mainTitle;
    }

    private JButton generateNewGameButton() {
        JButton startNewGameButton = new JButton("New Game");
        startNewGameButton.setBounds(50, 220, 150, 25);
        startNewGameButton.setFont(font.deriveFont(Font.PLAIN, 32f));
        startNewGameButton.setForeground(Color.WHITE);
        startNewGameButton.setOpaque(false);
        startNewGameButton.setContentAreaFilled(false);
        startNewGameButton.setBorder(new CompoundBorder(startNewGameButton.getBorder(), new EmptyBorder(12, 0, 12, 0)));
        startNewGameButton.setFocusable(false);
        startNewGameButton.setBorderPainted(false);
        startNewGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startNewGameButton.setFocusPainted(false);

        startNewGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.getScore() != 0) {
                    dataBase.saveData(game.getScore());
                    scores.repaint();
                }
                homeScreeClip.stop();
                game.restartGame();
                cardLayout.show(panelContainer, "Game");
            }
        });
        return startNewGameButton;
    }

    private JButton generateExitButton() {
        JButton exitButton = new JButton("Resume game");
        exitButton.setBounds(50, 220, 150, 25);
        exitButton.setFont(font.deriveFont(Font.PLAIN, 32f));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBorder(new CompoundBorder(exitButton.getBorder(), new EmptyBorder(12, 0, 12, 0)));
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.play();
                homeScreeClip.stop();
                cardLayout.show(panelContainer, "Game");
            }
        });
        return exitButton;
    }

    private JButton generateScoresButton() {
        JButton scoresButton = new JButton("Scores");
        scoresButton.setBounds(50, 220, 150, 25);
        scoresButton.setFont(font.deriveFont(Font.PLAIN, 32f));
        scoresButton.setForeground(Color.WHITE);
        scoresButton.setBorder(new CompoundBorder(scoresButton.getBorder(), new EmptyBorder(12, 0, 12, 0)));
        scoresButton.setOpaque(false);
        scoresButton.setContentAreaFilled(false);
        scoresButton.setFocusPainted(false);
        scoresButton.setBorderPainted(false);
        scoresButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.play();
                cardLayout.show(panelContainer, "Score");
            }
        });
        return scoresButton;
    }


    private JLabel generateScoreTitle() {
        JLabel scoreTitle = new JLabel("Last record: " + 0);
        scoreTitle.setFont(font.deriveFont(Font.PLAIN, 32f));
        scoreTitle.setForeground(Color.white);
        Border scoreTitleborder = scoreTitle.getBorder();
        scoreTitle.setBorder(new CompoundBorder(scoreTitleborder, new EmptyBorder(100, 0, 0, 0)));
        scoreTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        return scoreTitle;
    }
}
