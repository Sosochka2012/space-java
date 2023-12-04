import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Asteroid {
    public Rectangle boundingBox;
    public BufferedImage sprite; 
    public int posX, posY;
    public Boolean isAlive;
    public int speed;
    private int randPosX;
    Random random = new Random(System.currentTimeMillis());

    public Asteroid(BufferedImage newSprite) {
        sprite = newSprite;
        speed = (random.nextInt(6)) + 2;
        isAlive = true;
        // int gameWidth = 280;
        // int gameHeight = 400;
        randPosX = random.nextInt(1920);
        posY = -30;
        boundingBox = new Rectangle(randPosX, posY, 64, 64);
    }

    public void Update() {
        posY = posY + speed;
        boundingBox = new Rectangle(randPosX, posY, 64, 64);

        if (posY > 1080) {
            randPosX = random.nextInt(1920);
            posY = -30;
        }

    }

    public void Draw(Graphics2D g) {
        if (isAlive) {
            g.drawImage(sprite, randPosX, posY, 64, 64, null);
        }
    }
}