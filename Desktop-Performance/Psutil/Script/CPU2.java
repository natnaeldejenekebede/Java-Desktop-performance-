import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class CPU2 extends JFrame {

    private JPanel mainPanel;
    private Timer timer;
    private DefaultListModel<String> metricListModel;



    public CPU2() {
        setTitle("System Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        JLabel header = new JLabel("System Information");
        header.setFont(new Font("Verdana", Font.BOLD, 24));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        metricListModel = new DefaultListModel<>();
        JList<String> metricList = new JList<>(metricListModel);
        JScrollPane metricScrollPane = new JScrollPane(metricList);
        metricScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> displaySystemInformation());
        refreshButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(header);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(metricScrollPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(refreshButton);

        // Initialize timer for real-time updates (every 5 seconds)
        timer = new Timer(5000, e -> displaySystemInformation());
        timer.start();

        displaySystemInformation();

        add(mainPanel);
    }

    private void displaySystemInformation() {
        metricListModel.clear();

        // Example: CPU Usage
        double cpuUsage = getCPUPercentage();
        metricListModel.addElement("CPU Usage: " + cpuUsage + "%");

        // Example: Memory Usage 
        MemoryUsage heapMemoryUsage = getHeapMemoryUsage();
        metricListModel.addElement("Used Heap Memory: " + formatBytes(heapMemoryUsage.getUsed()));
        metricListModel.addElement("Max Heap Memory: " + formatBytes(heapMemoryUsage.getMax()));
        metricListModel.addElement(getName());
        metricListModel.addElement(getVersion());
        metricListModel.addElement(getAvailableProcessors());
        metricListModel.addElement(getSystemLoadAverage());

        // Example: Disk Information (C: Drive)
        File cDrive = new File("C:\\");
        long totalSpace = cDrive.getTotalSpace();
        long freeSpace = cDrive.getFreeSpace();
        long usedSpace = totalSpace - freeSpace;
        double usedSpacePercentage = ((double) usedSpace / totalSpace) * 100;
        float totalSpaceGB = (float) totalSpace / 1024 / 1024 / 1024;
        float freeSpaceGB = (float) freeSpace / 1024 / 1024 /1024;
        float usedSpaceGB = (float) usedSpace /1024 / 1024 / 1024;
    

        metricListModel.addElement("Total Disk Space (C:): " + formatBytes(totalSpace));
        metricListModel.addElement("Free Disk Space (C:): " + formatBytes(freeSpace));
        metricListModel.addElement("Used Disk Space (C:): " + formatBytes(usedSpace));
        metricListModel.addElement("Used Disk Space Percentage (C:): " + usedSpacePercentage + "%");
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        metricListModel.addElement("Total Disk Space (C:): " + totalSpaceGB + " GB");
        metricListModel.addElement("Free Disk Space (C:): " + freeSpaceGB + " GB");
        metricListModel.addElement("Used Disk Space (C:): " + usedSpaceGB + " GB");

        metricListModel.addElement("Total Disk Space (C:): " + formatBytes(totalSpace));
        metricListModel.addElement("Free Disk Space (C:): " + formatBytes(freeSpace));
        metricListModel.addElement("Used Disk Space (C:): " + formatBytes(usedSpace));
        metricListModel.addElement("Used Disk Space Percentage (C:): " + usedSpacePercentage + "%");
     

  try {
    Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
    while (networks.hasMoreElements()) {
        NetworkInterface network = networks.nextElement();
        if (network.getDisplayName().contains("Ethernet")) {
            metricListModel.addElement("Ethernet Display Name: " + network.getDisplayName());
            metricListModel.addElement("Ethernet MTU: " + network.getMTU());

            // Get IP addresses of the Ethernet interface
            Enumeration<InetAddress> addresses = network.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress address = addresses.nextElement();
                metricListModel.addElement("Ethernet IP Address: " + address.getHostAddress());
            }
        }
    }
} catch (SocketException e) {
    // Handle SocketException
    e.printStackTrace();
} catch (SecurityException e) { 
    // Handle SecurityException
    e.printStackTrace();
} catch (Exception e) {
    // Handle other exceptions
    e.printStackTrace();
}


        // Add more metrics for Ethernet, WiFi, GPU, etc.

        // ... Add additional metrics here

        // Redraw the UI
        revalidate();
        repaint();
    }
 
    private String getVersion() {
        return null;
    }

    private String getSystemLoadAverage() {
        return null;
    }

    private String getAvailableProcessors() {
        return null;
    }

    private double getCPUPercentage() {
        // Implement logic to obtain CPU percentage (example)
        return Math.random() * 100;
    }

    private MemoryUsage getHeapMemoryUsage() {
        // Implement logic to obtain heap memory usage (example)
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        return memoryBean.getHeapMemoryUsage();
    }

    private String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        char pre = "KMGTPE".charAt(exp - 1);
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CPU2().setVisible(true));
    }
}
