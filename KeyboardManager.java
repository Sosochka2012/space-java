import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardManager implements KeyListener {

    public boolean keyLeft;
    public boolean keyRight;
    public boolean keyUp;
    public boolean keyDown;
    public boolean keySpace;

    public KeyboardManager() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            keyUp = true;
        }

        if (keyCode == KeyEvent.VK_S) {
            keyDown = true;
        }

        if (keyCode == KeyEvent.VK_A) {
            keyLeft = true;
        }

        if (keyCode == KeyEvent.VK_D) {
            keyRight = true;
        }

        if (keyCode == KeyEvent.VK_SPACE) {
            keySpace = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            keyUp = false;
        }

        if (keyCode == KeyEvent.VK_S) {
            keyDown = false;
        }

        if (keyCode == KeyEvent.VK_A) {
            keyLeft = false;
        }

        if (keyCode == KeyEvent.VK_D) {
            keyRight = false;
        }

        if (keyCode == KeyEvent.VK_SPACE) {
            keySpace = false;
        }
    }

    
}