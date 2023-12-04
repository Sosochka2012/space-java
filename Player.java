import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.imageio.ImageIO;
import javax.swing.text.html.BlockView;

public class Player {

    public Rectangle boundingBox;
    int x, y;
    int velocity;
    int health;
    int heath;
    float bulletDelay;

    BufferedImage sprite;
    BufferedImage bulletSprite;

    public List<Bullet> bulletList;

    public Player() {
        x = 960;
        y = 900;
        velocity = 12;
        heath = 0;
        bulletDelay = 1;

        boundingBox = new Rectangle(x, y, 96, 128);

        try {
            sprite = ImageIO.read(new FileInputStream("graphics/xwing.png"));
            bulletSprite = ImageIO.read(new FileInputStream("graphics/bullet.png"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        bulletList = new ArrayList<Bullet>();
    }

    public void Update(boolean up, boolean down, boolean left, boolean right, boolean space) {
        if (up == true) {
            y -= velocity;
        }

        if (down == true) {
            y += velocity;
        }

        if (left == true) {
            x -= velocity;
        }

        if (right == true) {
            x += velocity;
        }

        if (space == true) {
            shoot();
        }

        if (space == false) {
            bulletDelay = 1;
        }

        if (x > 1920) {
            x = -10;
        } else if (x < -10) {
            x = 1920;
        }
        boundingBox = new Rectangle(x, y, 96, 128);
        updateBullets();
    }

    public void Draw(Graphics2D g2) {
        g2.drawImage(sprite, x, y, 96, 128, null);

        for (Bullet b : bulletList) {
            b.Draw(g2);
        }
    }

    public void shoot() {
        if (bulletDelay >= 0) {
            bulletDelay--;
        }

        if (bulletDelay <= 0) {
            Bullet newBullet = new Bullet(bulletSprite);
            newBullet.posX = x + 40;
            newBullet.posY = y + 10;

            if (bulletList.size() < 20) {
                bulletList.add(newBullet);
            }
        }
        if (bulletDelay <= 0) {
            bulletDelay = 20;
        }
    }

    public void updateBullets() {
        try {
            for (Bullet b : bulletList) {
                b.posY -= b.speed;

                b.boundingBox = new Rectangle(b.posX, b.posY, 16, 16);

                if (b.posY <= 0) {
                    b.isAlive = false;
                }
            }

            for (int i = 0; i < bulletList.size(); i++) {
                if (bulletList.get(i).isAlive == false) {
                    bulletList.remove(i);
                    i--;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
