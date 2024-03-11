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


        Timer timer = new Timer(5000, e -> displaySystemInformation());
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
