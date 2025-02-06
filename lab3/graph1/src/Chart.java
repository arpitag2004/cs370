import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Chart
{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new ChartFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class ChartFrame extends JFrame {
    private final ChartPanel panel;

    public ChartFrame() {
        setTitle("Bar Chart with Grid and Random Colors");
        setSize(300, 300);
        setLayout(new BorderLayout());

        // Create and add our custom chart panel.
        panel = new ChartPanel();
        add(panel, BorderLayout.CENTER);

        // Create a button to regenerate random data.
        JButton redrawButton = new JButton("Redraw");
        redrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.generateRandomData();
                panel.repaint();
            }
        });
        add(redrawButton, BorderLayout.SOUTH);
    }
}

class ChartPanel extends JPanel {
    private final Random random = new Random();
    private int[] data;
    private final int numberOfBars = 10;
    // Fixed bar width (thickness). Change this value to adjust the bar thickness.
    private int fixedBarWidth = 10;

    public ChartPanel() {
        generateRandomData();
    }

    /**
     * Generates an array of random values for the bars.
     */
    public void generateRandomData() {
        int panelHeight = getHeight();
        // If the panel isn't yet displayed, use a default height.
        if (panelHeight <= 0) {
            panelHeight = 300;
        }
        data = new int[numberOfBars];
        for (int i = 0; i < numberOfBars; i++) {
            // Random bar height between 0 and panelHeight.
            data[i] = random.nextInt(panelHeight);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        Graphics2D g2d = (Graphics2D) g;

        // Draw a light gray grid (lines every 10 pixels).
        g2d.setColor(Color.GRAY);
        for (int i = 0; i < panelWidth; i += 10) {
            g2d.drawLine(i, 0, i, panelHeight);
        }
        for (int i = 0; i < panelHeight; i += 10) {
            g2d.drawLine(0, i, panelWidth, i);
        }

        // Calculate gap between bars based on the fixed bar width.
        // The gap is computed so that all bars plus the spaces between them fit across the panel.
        int gap = (panelWidth - (numberOfBars * fixedBarWidth)) / (numberOfBars + 1);
        if (gap < 0)
        { // Avoid negative gap if the window is too small.
            gap = 0;
        }

        // Draw each bar.
        for (int i = 0; i < numberOfBars; i++) {
            int barHeight = data[i];
            // Calculate x position: a gap before the first bar and between subsequent bars.
            int x = gap + i * (fixedBarWidth + gap);
            // Calculate y so that the bar grows upward from the bottom.
            int y = panelHeight - barHeight;

            // Set a random color for this bar.
            Color randomColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            g2d.setColor(randomColor);
            g2d.fillRect(x, y, fixedBarWidth, barHeight);

            // Draw a black border around the bar for contrast.
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, fixedBarWidth, barHeight);
        }
    }
}
