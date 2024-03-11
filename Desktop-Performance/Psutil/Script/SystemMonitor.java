import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Timer;
import java.util.TimerTask;

public class SystemMonitor extends JFrame {

    private JPanel mainPanel;
    private Timer timer;
    private JLabel memoryLabel;
    private JLabel titleLabel;

    public SystemMonitor() {
        setTitle("System Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new GridLayout(0, 1));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel = new JLabel("System Monitor", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        titleLabel = new JLabel("System Monitor", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        


        titleLabel = new JLabel("System Monitor", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));


        memoryLabel = new JLabel();
        memoryLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        mainPanel.add(memoryLabel);

        add(mainPanel);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateMemoryUsage();
            }
        }, 0, 1000);
    }

    private void updateMemoryUsage() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        memoryLabel.setText("Memory usage: " + heapMemoryUsage.getUsed() / 1024 / 1024 + " MB");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SystemMonitor systemMonitor = new SystemMonitor();
            systemMonitor.setVisible(true);
        });
    }
}
