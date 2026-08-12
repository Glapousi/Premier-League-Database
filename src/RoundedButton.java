import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import static java.awt.Color.white;

public class RoundedButton extends JButton{
    
    public RoundedButton(String text){
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(new RoundedBorder(8));
        setPreferredSize(new Dimension(150, 40));
        //setBackground(new Color(140, 0, 140));
        //setBackground(new Color(100, 30, 100));
        setBackground(new Color(100, 50, 100));
        setForeground(Color.white);

    }
    
    @Override
    protected void paintComponent(Graphics g){
        if(getModel().isArmed()){
            g.setColor(Color.lightGray);
        }else{
            g.setColor(getBackground());
        }
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
        super.paintComponent(g);
    }
    
    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10); // Paint the border
    }

    

   
    
    private class RoundedBorder implements Border {
        private int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }
    }
    
}
