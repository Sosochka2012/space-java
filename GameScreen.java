import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.awt.Toolkit;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.Font;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;


public class GameScreen extends JPanel implements Runnable {

    int GameWidth = 1980;
    int GameHeight = 1080;
  
    List<Asteroid> asteroidList = new ArrayList<Asteroid>();
    List<Boss> bossList = new ArrayList<Boss>();
  
    Thread gameThread;
  
    KeyboardManager keyManager = new KeyboardManager();
    Player player = new Player();
  
    Starfield stars = new Starfield();
  
    private Image playButtonImage;
    private boolean showPlayButton = true;

	private int score = 0; // новая переменная для счетчика
    private long startTime = System.currentTimeMillis();

		private void increaseScore() {
			score++; // увеличение счетчика при сбитии астероида
		}

		private void playShootSound() {
			try {
				File audioFile = new File("graphics\\sounds\\shoot.wav"); // указываете путь к звуковому файлу
				AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
		
				Clip clip = AudioSystem.getClip();
				clip.open(audioStream);
				clip.start();
			} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	
    public GameScreen() {
        this.setPreferredSize((new Dimension(GameWidth, GameHeight)));
        this.setBackground(Color.CYAN);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyManager);
        this.setFocusable(true);
        
        
        try {
            playButtonImage = ImageIO.read(new FileInputStream("graphics/play_button.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void StartGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

	

    @Override
    public void run() {

        double drawInterval = 2000000000 / 60;
        double nextDrawTime = System.nanoTime() + drawInterval;
		playShootSound();
        while (gameThread != null) {
            update();
            repaint();
            Toolkit.getDefaultToolkit().sync();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                gameThread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                // TOD Auto-Generated catch block
                e.printStackTrace();
            }
        }
    }

    public void update() {
		
        stars.Update();
        player.Update(keyManager.keyUp, keyManager.keyDown, keyManager.keyLeft, keyManager.keyRight,
                keyManager.keySpace);

        if (System.currentTimeMillis() % 2000 >= 0 && System.currentTimeMillis() % 2000 <= 100) {
            loadAsteroid();
        }
        //

        if (System.currentTimeMillis() <= (startTime + 11000) && System.currentTimeMillis()  >= (startTime + 10000)) {
            for (int i = 0; i < player.bulletList.size(); i++) {

                    player.bulletList.get(i).isAlive = false;

                }  
            if (player.bulletList.size() == 0) {
                loadBoss();
            }
            
            
        }


        



        for (int i = 0; i < asteroidList.size(); i++) {
            if (asteroidList.get(i).isAlive == false) {
                asteroidList.remove(i);
                i--;
            }
        }

        for (int i = 0; i < bossList.size(); i++) {
            if (bossList.get(i).health <= 0) {
                bossList.remove(i);
                i--;

            }    

            }
        


        for (Asteroid a : asteroidList) {
            for (int i = 0; i < player.bulletList.size(); i++) {
                if (a.boundingBox.intersects(player.bulletList.get(i).boundingBox)) {
                    a.isAlive = false;
                    player.bulletList.get(i).isAlive = false;
					increaseScore();
                }
            }

            if (a.boundingBox.intersects(player.boundingBox)) {
                int input = JOptionPane.showOptionDialog(null, "Вы умерли", "Смерть", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.ERROR_MESSAGE, null, null, null);

                if (input == JOptionPane.OK_OPTION) {
                    System.exit(0);
                } else {
                    System.exit(0);
                }
            }

            a.Update();
        }

        for (Boss b : bossList) {
            for (int i = 0; i < player.bulletList.size(); i++) {
                if (b.boundingBox.intersects(player.bulletList.get(i).boundingBox)) {
                    
                    player.bulletList.get(i).isAlive = false;
					score+=b.takeDamage(1);
                }

                
            }

            for (int s = 0; s < b.bulletList.size(); s++) {
                    System.out.println(s);
                    if (b.bulletList.get(s).boundingBox.intersects(player.boundingBox)) {
                        int input = JOptionPane.showOptionDialog(null, "Вы умерли", "Смерть", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.ERROR_MESSAGE, null, null, null);

                if (input == JOptionPane.OK_OPTION) {
                    System.exit(0);
                } else {
                    System.exit(0);
                }
            }
                    
                }

            b.Update();
        }

        if (showPlayButton && keyManager.keySpace) {
            showPlayButton = false;
        }

    }

    public void loadAsteroid() {
        Asteroid a;
        try {
             a = new Asteroid(ImageIO.read(new FileInputStream("graphics/asteroid.png")));
            if (asteroidList.size() < 10) {
                asteroidList.add(a);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void loadBoss() {
        Boss b;
        b = new Boss(100, 100);
        if (bossList.size() < 1) {
           bossList.add(b); 
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        stars.Draw(g2);
        player.Draw(g2);

		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.BOLD, 50)); 
        g.drawString("Score: " + score, 100, 50);

        for (Asteroid a : asteroidList) {
            a.Draw(g2);
        }

        if (showPlayButton) {
            int buttonWidth = playButtonImage.getWidth(null);
            int buttonHeight = playButtonImage.getHeight(null);

            int buttonX = (GameWidth - buttonWidth) / 2;
            int buttonY = (GameHeight - buttonHeight) / 2;

            g2.drawImage(playButtonImage, buttonX, buttonY, null);
        }

        for (Boss b : bossList) {
            b.draw(g2);
        }

    }
}