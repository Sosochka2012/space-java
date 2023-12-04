import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.io.FileInputStream;
import java.awt.image.BufferedImage;

public class Starfield {

    BufferedImage image;
    int x, y, x2, y2;
    int velocity;

    public Starfield() {
        try {
            image = ImageIO.read(new FileInputStream("graphics/bg3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        velocity = 1;
        x2 = 0;
        y2 = -1070;
    }

    public void Update() {
        y += velocity;
        y2 += velocity;
        x = 0;

        if (y > 1070) {
            y = -1070;
        }
        if (y2 > 1070) {
            y2 = -1070;
        }
    }

    public void Draw(Graphics2D g2) {
        g2.drawImage(image, x, y, 1920, 1081, null);
        g2.drawImage(image, x2, y2, 1920, 1081, null);
    }

    public static void main(String[] args) {

    }
}
