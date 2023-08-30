// Necessary imports
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
class ImagePanel extends JPanel {
    private Image image;
    public ImagePanel(Image img) {
        this.image = img;

        // image layout null
        setLayout(null); 
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw image scaled to panel size
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this); 
    }
}
