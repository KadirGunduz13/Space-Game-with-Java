import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

class Fire {
    private int x;
    private int y;

    public Fire(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

public class Game extends JPanel implements KeyListener, ActionListener {

    Timer timer = new Timer(5, this);


    private int passing_time = 0;
    private int spent_fire = 0;

    private BufferedImage image;

    private ArrayList<Fire> fires = new ArrayList<Fire>();

    private int firedirY = 1;

    private int topX = 0;

    private int topdirX = 2;

    private int spaceShipX = 0;

    private int dirSpaceX = 20;

    public boolean check() {
        for (Fire fire : fires) {
            if (new Rectangle(fire.getX(),fire.getY(),10,20).intersects(new Rectangle(topX,0,20,20))) {
                return true;
            }
        }
        return false;
    }

    public Game() {

        try {
            image = ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png")));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }

        setBackground(Color.BLACK);

        timer.start();


    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        passing_time += 5;

        g.setColor(Color.RED);

        g.fillOval(topX, 0, 20, 20);

        g.drawImage(image, spaceShipX, 490, image.getWidth() / 10, image.getHeight() / 10, this);

        for (Fire fire : fires) {
            if (fire.getY() < 0) {
                fires.remove(fire);
            }
        }

        g.setColor(Color.BLUE);

        for (Fire fire : fires) {
            g.fillRect(fire.getX(), fire.getY(), 10, 20);
        }

        if (check()) {
            timer.stop();
            String message = "You won...\n"
                    +"Spent fire: " + spent_fire
                    +"\nPassing time: " + passing_time/1000.0 + " seconds";
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }

    }

    @Override
    public void repaint() {
        super.repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        for (Fire fire : fires) {
            fire.setY(fire.getY() - firedirY);
        }

        topX += topdirX;

        if (topX >= 750) {
            topdirX = -topdirX;
        }
        if (topX <= 0) {
            topdirX = -topdirX;
        }

        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {

        int c = e.getKeyCode();

        if (c == KeyEvent.VK_LEFT) {
            if (spaceShipX <= 0) {
                spaceShipX = 0;
            } else {
                spaceShipX -= dirSpaceX;
            }
        } else if (c == KeyEvent.VK_RIGHT) {

            if (spaceShipX >= 750) {
                spaceShipX = 750;
            } else {
                spaceShipX += dirSpaceX;
            }

        } else if (c == KeyEvent.VK_SPACE) {

            fires.add(new Fire(spaceShipX+15, 470));
            spent_fire++;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
