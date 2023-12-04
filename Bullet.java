import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Bullet {

    				private void playStarfield() {
			try {
				File audioFile = new File("graphics\\sounds\\starfield.wav"); // указываете путь к звуковому файлу
				AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
		
				Clip clip = AudioSystem.getClip();
				clip.open(audioStream);
				clip.start();
			} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
				e.printStackTrace();
			}
		}

    public Rectangle boundingBox;
    public BufferedImage sprite;
    public int posX, posY;
    public boolean isAlive;
    public float speed;

    public Bullet(BufferedImage newSprite) {
        speed = 5;
        sprite = newSprite;
        isAlive = true;
        playStarfield();
    }

    public void Draw(Graphics2D g2) {
        g2.drawImage(sprite, posX, posY, 30, 30, null);
    }
}
