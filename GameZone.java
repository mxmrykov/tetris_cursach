import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.*;

public class GameZone extends JPanel implements GameData {
    private int[][] coordinates;
    private int InternalIndex = 0;
    private boolean availToMove = false;
    private int lastAdded = 0;
    private boolean lastRotated = false;
    public boolean mustPaint = true;
    private Font font;
    private int counter = 0;
    private final int[] sizeY = new int[2];
    private final int[] sizeX = new int[2];
    private int lastFigureIndex = 0;
    public boolean gameOver = false;
    private final Polygon[] fragment = new Polygon[200];
    private final Color[] fragmentColors = new Color[200];
    protected Timer timer = new Timer(20, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            moveDown();
        }
    });

    public void restart() {
        for (int i = 0; i < 200; ++i) {
            fragment[i] = null;
            fragmentColors[i] = null;
        }
        InternalIndex = 0;
        availToMove = false;
        lastAdded = 0;
        lastRotated = false;
        mustPaint = true;
        counter = 0;
        lastFigureIndex = 0;
        repaint();
    }

    public void pause() {
        this.timer.stop();
    }

    public void resume() {
        this.timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (this.lastAdded == 0 && !this.availToMove) {
            for (int i = 0; i < 200; ++i) {
                fragment[i] = null;
                fragmentColors[i] = null;
            }
        } else {
            if (mustPaint) for (int i = 0; i < this.lastAdded; ++i) {
                g.drawPolygon(fragment[i]);
                g.setColor(fragmentColors[i]);
                g.fillPolygon(fragment[i]);
                g.setColor(fragmentColors[i]);
            }
            else {
                System.out.println("Game generateLeaders");
                try {
                    font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/LLPixel.ttf"));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                g.setColor(Color.white);
                g.setFont(font.deriveFont(Font.PLAIN, 24f));
                g.drawString("Game over", 110, 220);
                gameOver = true;
            }
        }
        if (!mustPaint) {
            return;
        }
        if (!availToMove) {
            generateActivePart(g);
        } else {
            traceCurrent(g);
            lifeCycle();
        }
    }

    public int getCount() {
        return this.counter;
    }

    public void moveDown() {
        if (this.fragment[lastAdded] != null) {
            int[] newCoordinatesY = new int[this.fragment[lastAdded].npoints];
            for (int i = 0; i < this.fragment[lastAdded].npoints; ++i) {
                newCoordinatesY[i] = this.fragment[lastAdded].ypoints[i] + 1;
            }
            if (!isIntersects(fragment[lastAdded].xpoints, newCoordinatesY)) {
                this.fragment[lastAdded].ypoints = newCoordinatesY;
            }
        }
    }

    public void rotate() {
        if (this.fragment[lastAdded] != null) {
            int[] xPoints = fragment[lastAdded].xpoints;
            int[] yPoints = fragment[lastAdded].ypoints;
            if (GameData.TYPE[InternalIndex].equals("L")) {
                if (!lastRotated) {
                    xPoints[1] -= 20;
                    xPoints[2] += 20;
                    xPoints[4] += 20;
                    xPoints[5] += 60;

                    yPoints[0] += 40;
                    yPoints[1] += 20;
                    yPoints[2] -= 20;
                    yPoints[3] -= 40;
                    yPoints[4] -= 60;
                    yPoints[5] -= 20;
                    lastRotated = true;
                } else {
                    xPoints[1] += 20;
                    xPoints[2] -= 20;
                    xPoints[4] -= 20;
                    xPoints[5] -= 60;

                    yPoints[0] -= 40;
                    yPoints[1] -= 20;
                    yPoints[2] += 20;
                    yPoints[3] += 40;
                    yPoints[4] += 60;
                    yPoints[5] += 20;
                    lastRotated = false;
                }
            } else if (GameData.TYPE[InternalIndex].equals("LINE")) {
                if (!lastRotated) {
                    xPoints[1] -= 20;
                    xPoints[2] += 40;
                    xPoints[3] += 60;

                    yPoints[0] += 20;
                    yPoints[2] -= 60;
                    yPoints[3] -= 40;
                    lastRotated = true;
                } else {
                    xPoints[1] += 20;
                    xPoints[2] -= 40;
                    xPoints[3] -= 60;

                    yPoints[0] -= 20;
                    yPoints[2] += 60;
                    yPoints[3] += 40;
                    lastRotated = false;
                }
            } else if (GameData.TYPE[InternalIndex].equals("Z")) {
                if (!lastRotated) {
                    xPoints[3] -= 20;
                    xPoints[4] -= 20;
                    xPoints[5] -= 20;
                    xPoints[6] -= 20;

                    yPoints[0] -= 20;
                    yPoints[1] -= 20;
                    yPoints[2] += 20;
                    yPoints[3] += 20;
                    yPoints[4] += 40;
                    yPoints[5] += 40;
                    lastRotated = true;
                } else {
                    xPoints[3] += 20;
                    xPoints[4] += 20;
                    xPoints[5] += 20;
                    xPoints[6] += 20;

                    yPoints[0] += 20;
                    yPoints[1] += 20;
                    yPoints[2] -= 20;
                    yPoints[3] -= 20;
                    yPoints[4] -= 40;
                    yPoints[5] -= 40;
                    lastRotated = false;
                }
            } else if (GameData.TYPE[InternalIndex].equals("SHORT_T")) {
                if (!lastRotated) {
                    xPoints[5] -= 40;
                    xPoints[6] -= 40;

                    yPoints[0] -= 20;
                    yPoints[1] -= 20;
                    yPoints[2] += 20;
                    yPoints[3] += 20;
                    yPoints[4] += 20;
                    yPoints[5] += 20;
                    yPoints[6] += 20;
                    yPoints[7] += 20;
                    lastRotated = true;
                } else {
                    xPoints[5] += 40;
                    xPoints[6] += 40;

                    yPoints[0] += 20;
                    yPoints[1] += 20;
                    yPoints[2] -= 20;
                    yPoints[3] -= 20;
                    yPoints[4] -= 20;
                    yPoints[5] -= 20;
                    yPoints[6] -= 20;
                    yPoints[7] -= 20;
                    lastRotated = false;
                }
            } else if (GameData.TYPE[InternalIndex].equals("L_REV")) {
                if (!lastRotated) {
                    xPoints[0] -= 20;
                    xPoints[3] += 20;
                    xPoints[4] += 20;
                    xPoints[5] -= 20;

                    yPoints[4] -= 20;
                    yPoints[5] -= 20;
                    lastRotated = true;
                } else {
                    xPoints[0] += 20;
                    xPoints[3] -= 20;
                    xPoints[4] -= 20;
                    xPoints[5] += 20;

                    yPoints[4] += 20;
                    yPoints[5] += 20;
                    lastRotated = false;
                }
            }
            if (!isIntersects(xPoints, yPoints)) {
                fragment[lastAdded].xpoints = xPoints;
                fragment[lastAdded].ypoints = yPoints;
            }
            repaint();
        }
    }

    private boolean isIntersects(int[] Xcords, int[] Ycords) {
        Polygon part = new Polygon(Xcords, Ycords, Ycords.length);
        for (int i = 0; i < lastAdded; ++i) {
            Polygon temp = new Polygon(this.fragment[i].xpoints, this.fragment[i].ypoints, this.fragment[i].npoints);
            Area temp1 = new Area(part);
            Area temp2 = new Area(temp);
            temp1.intersect(temp2);
            if (!temp1.isEmpty()) {
                return true;
            }
        }
        return false;
    }


    public void moveLeft() {
        calculateMinimum(this.fragment[lastAdded].xpoints, this.fragment[lastAdded].ypoints);
        if (sizeX[0] >= 20) {
            boolean can = true;
            int[] newCoordinatesX = new int[this.fragment[lastAdded].npoints];
            for (int i = 0; i < this.fragment[lastAdded].npoints; ++i) {
                newCoordinatesX[i] = this.fragment[lastAdded].xpoints[i] - 20;
            }
            if (!isIntersects(newCoordinatesX, fragment[lastAdded].ypoints)) {
                this.fragment[lastAdded].xpoints = newCoordinatesX;
            }
            repaint();
        }
    }

    public void moveRight() {
        calculateMinimum(this.fragment[lastAdded].xpoints, this.fragment[lastAdded].ypoints);
        if (sizeX[1] <= 370) {
            int[] newCoordinatesX = new int[this.fragment[lastAdded].npoints];
            for (int i = 0; i < this.fragment[lastAdded].npoints; ++i) {
                newCoordinatesX[i] = this.fragment[lastAdded].xpoints[i] + 20;
            }
            if (!isIntersects(newCoordinatesX, fragment[lastAdded].ypoints)) {
                this.fragment[lastAdded].xpoints = newCoordinatesX;
            }
            repaint();
        }
    }

    public void generateActivePart(Graphics g) {
        this.coordinates = new int[2][GameData.allFigures[lastFigureIndex][0].length];
        this.coordinates = GameData.allFigures[lastFigureIndex];
        InternalIndex = lastFigureIndex;
        if (isIntersects(GameData.allFigures[lastFigureIndex][0], GameData.allFigures[lastFigureIndex][1]) && lastAdded != 0) {
            this.mustPaint = false;
            repaint();
        } else {
            this.availToMove = true;
            this.fragment[this.lastAdded] = new Polygon(this.coordinates[0], this.coordinates[1], this.coordinates[0].length);
            this.fragmentColors[this.lastAdded] = getRandomColor();
            if (this.lastFigureIndex == 5) {
                this.lastFigureIndex = 0;
            } else {
                this.lastFigureIndex += 1;
            }
            calculateMinimum(this.fragment[lastAdded].xpoints, this.fragment[lastAdded].ypoints);
            repaint();
        }
    }

    public boolean ifUnpaintedCells(int rectX, int rectY, int rectWidth, int rectHeight, Polygon[] polygons) {
        for (int i = rectX; i < rectX + rectWidth; i++) {
            for (int j = rectY; j < rectY + rectHeight; j++) {
                boolean isFilled = false;
                for (int in = 0; in < lastAdded; ++in) {
                    if (fragment[i].contains(i, j)) {
                        isFilled = true;
                        break;
                    }
                }
                if (!isFilled) {
                    return false;
                }
            }
        }
        return true;
    }


    public void traceCurrent(Graphics g) {
        g.drawPolygon(this.fragment[lastAdded]);
        g.setColor(this.fragmentColors[lastAdded]);
        g.fillPolygon(this.fragment[lastAdded]);
        g.setColor(this.fragmentColors[lastAdded]);
    }

    public Color getRandomColor() {
        switch (new Random().nextInt(0, 5)) {
            case 0:
                return Color.red;
            case 1:
                return Color.blue;
            case 2:
                return Color.green;
            case 3:
                return Color.magenta;
            case 4:
                return Color.yellow;
            default:
                return Color.red;
        }
    }

    public void calculateMinimum(int[] frX, int[] frXY) {
        int minX = 100, maxX = 0, maxY = 0, minY = 100;
        for (int i = 0; i < frX.length; ++i) {
            if (frX[i] > maxX) {
                maxX = this.fragment[lastAdded].xpoints[i];
            }
            if (frXY[i] < minY) {
                minY = this.fragment[lastAdded].ypoints[i];
            }
            if (frXY[i] > maxY) {
                maxY = this.fragment[lastAdded].ypoints[i];
            }
            if (frX[i] < minX) {
                minX = this.fragment[lastAdded].xpoints[i];
            }
        }
        this.sizeX[0] = minX;
        this.sizeX[1] = maxX;
        this.sizeY[0] = minY;
        this.sizeY[1] = maxY;
    }

    public boolean isAvailToMoveFragment() {
        if (lastAdded == 0) {
            return true;
        }
        int[] newCoordinatesY = new int[this.fragment[lastAdded].npoints];
        for (int i = 0; i < this.fragment[lastAdded].npoints; ++i) {
            newCoordinatesY[i] = this.fragment[lastAdded].ypoints[i] + 1;
        }
        repaint();
        Polygon part = new Polygon(this.fragment[lastAdded].xpoints, newCoordinatesY, this.fragment[lastAdded].npoints);
        for (int i = 0; i < lastAdded; ++i ) {
            Polygon temp = new Polygon(this.fragment[i].xpoints, this.fragment[i].ypoints, this.fragment[i].npoints);
            Area temp1 = new Area(part);
            Area temp2 = new Area(temp);
            temp1.intersect(temp2);
            if (!temp1.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void lifeCycle() {
        calculateMinimum(this.fragment[lastAdded].xpoints, this.fragment[lastAdded].ypoints);

        if (this.sizeY[1] <= 418 && isAvailToMoveFragment()) {
            this.timer.start();
            repaint();
        } else {
            if (lastAdded > 0) {
                System.out.println(ifUnpaintedCells(0, 380, 380, 20, fragment));
                System.out.println(ifUnpaintedCells(0, 400, 380, 20, fragment));
                System.out.println(ifUnpaintedCells(0, 420, 380, 20, fragment));
            }
            this.counter += 10;
            this.availToMove = false;
            this.lastRotated = false;
            this.lastAdded += 1;
            repaint();
        }
    }
}