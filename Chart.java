/* TEAM 9 JAVA SWING APPLICATION

 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.Random;

public class Chart {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Random Bar Chart");
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //makes sure to close when progam is closed

        ChartPanel panel = new ChartPanel();
        frame.add(panel);

        // create the button
        JButton button = new JButton("Redraw");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.repaint(); // repaints  when button is clicked
            }
        });
        frame.add(button, BorderLayout.SOUTH); // adds button to the bottom of frame

        // make  frame visible
        frame.setVisible(true);
    }
}

class ChartPanel extends JPanel {
    private Random random; //  random generator

    public ChartPanel() {
        random = new Random();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // call the parent class paintComponent method

        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth(); //  panel width
        int height = getHeight(); //  panel height

        // drawing the grid
        g2d.setColor(Color.LIGHT_GRAY); //  grid color
        int gridSize = 10; // size of each grid square set to 10
        for (int i = 0; i < width; i += gridSize) {
            g2d.drawLine(i, 0, i, height); // draw the vertical lines
        }
        for (int j = 0; j < height; j += gridSize) {
            g2d.drawLine(0, j, width, j); // draw the horizontal lines
        }

        // drawing random bars
        int numBars = 15; // num of bars
        for (int i = 0; i < numBars; i++) {
            int x = random.nextInt(width); // random x position
            int barHeight = random.nextInt(height); // random height
            int barY = height - barHeight; // y position
            float thickness = 10f; // bar thickness

            // set a random color
            int red = random.nextInt(256);
            int green = random.nextInt(256);
            int blue = random.nextInt(256);
            Color randomColor = new Color(red, green, blue);
            g2d.setColor(randomColor);

            //thickness
            g2d.setStroke(new BasicStroke(thickness));

            //drawing the bar
            Line2D.Double line = new Line2D.Double(x, height, x, barY);
            g2d.draw(line);
        }
    }
}
