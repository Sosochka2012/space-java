import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class Boss {
    private int x;
    private int y;
    private int velocity;
    public int health = 10;
    private float bulletDelay;
    BufferedImage bulletSprite;
    private BufferedImage sprite;
    public List<Bullet> bulletList;

     public Rectangle boundingBox;
 
    public int posX, posY;
    public Boolean isAlive;
    public int speed;
    private int randPosX = 1000;
    Random random = new Random(System.currentTimeMillis());

    public Boss(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocity = 12;
        this.health = 3;
        this.bulletDelay = 1;
    

        this.bulletList = new ArrayList<Bullet>();

        try {
           bulletSprite = ImageIO.read(new FileInputStream("graphics/bullet.png"));
            this.sprite = ImageIO.read(getClass().getResourceAsStream("graphics/111.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
    }

    public void Update() {
        
        //хитбокс босса
        boundingBox = new Rectangle(randPosX, y, 64, 64);
        //скорость передвижения
        randPosX+= 3;
        shoot();
        
        //в случае выхода за рамки - вернуться в начало
        if (randPosX > 1920) {
            randPosX = 10;   
        }

        updateBullets();
    }
        
    public void draw(Graphics2D g) {
        
        if (health > 0) {
            // Отрисовка босса
            g.drawImage(sprite, randPosX, y, 64, 64, null);

            // Отрисовка пуль
            for (Bullet bullet : bulletList) {
                bullet.Draw(g);
            }
        }
        
    }

    public int takeDamage(int damage) {
        health -= damage; // Уменьшение здоровья босса
        if (health <= 0) {
            return 1000;
        }
        return 0;
    }

    public void shoot() {
        if (bulletDelay >= 0) {
            bulletDelay--;
        }

        if (bulletDelay <= 0) {
            Bullet newBullet = new Bullet(bulletSprite);
            newBullet.posX = randPosX;
            newBullet.posY = y - 10;

            if (bulletList.size() < 20) {
                bulletList.add(newBullet);
            } else {
                for (int i = 0; i < bulletList.size(); i++) {
                
                    bulletList.remove(i);
                    i--;
                
            }
            }
        }
        if (bulletDelay <= 0) {
            bulletDelay = 20;
        }
    }

    public void updateBullets() {
        try {
            for (Bullet b : bulletList) {
                b.posY += b.speed;

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
