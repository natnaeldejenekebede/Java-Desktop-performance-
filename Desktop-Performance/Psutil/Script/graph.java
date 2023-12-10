import javax.swing.*;
import java.awt.*;
import java.lang.management.*;

public class graph extends JFrame {
    private JTextArea textArea;

    public graph() {
        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setAlwaysOnTop(rootPaneCheckingEnabled);


        // Initialize timer for real-time updates (every 5 seconds)
        Timer timer = new Timer(5000, e -> displaySystemInformation());
        // add new line here to start timer 
        // give me more additional functionality
        timer.start();
    }

    private void displaySystemInformation() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
        double cpuLoad = ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage();

        textArea.setText("Heap Memory Usage: " + heapUsage.getUsed() + "\nCPU Load: " + cpuLoad);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(graph::new);
    }
}