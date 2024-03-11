import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;


public class GUI extends JFrame {

    private JTable table;
    private JPanel mainPanel;
    private Timer timer;
    private DefaultTableModel tableModel;

    public GUI() {
        setTitle("Memory Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(new Insets(15, 15, 15, 15)));
        mainPanel.setBackground(Color.WHITE);

        JLabel header = new JLabel("Memory Information");
        header.setFont(new Font("Verdana", Font.BOLD, 30));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        table = new JTable();
        tableModel = new DefaultTableModel();
        table.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> displayMemoryInformation());
        refreshButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton exportButton = new JButton("Export Data");
        exportButton.addActionListener(e -> exportMemoryInformation());
        exportButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton toggleDarkModeButton = new JButton("Toggle Dark Mode");
        toggleDarkModeButton.addActionListener(e -> toggleDarkMode());
        toggleDarkModeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton showSystemInfoButton = new JButton("Show System Info");
        showSystemInfoButton.addActionListener(e -> showSystemInfo());
        showSystemInfoButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton toggleGraphButton = new JButton("Toggle Memory Usage Graph");
        toggleGraphButton.addActionListener(e -> toggleMemoryGraph());
        toggleGraphButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(header);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(refreshButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(exportButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(toggleDarkModeButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(showSystemInfoButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(toggleGraphButton);

        displayMemoryInformation();

        add(mainPanel);

        // Initialize timer for real-time updates (every 5 seconds)
        timer = new Timer(5000, e -> displayMemoryInformation());
        timer.start();
    }

    private void displayMemoryInformation() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryBean.getNonHeapMemoryUsage();

        // Format memory values
        String usedHeap = formatBytes(heapMemoryUsage.getUsed());
        String maxHeap = formatBytes(heapMemoryUsage.getMax());
        String usedNonHeap = formatBytes(nonHeapMemoryUsage.getUsed());
        String maxNonHeap = formatBytes(nonHeapMemoryUsage.getMax());

        // Update table model
        tableModel.setDataVector(new Object[][]{
                {"Used Heap Memory", usedHeap, maxHeap},
                {"Used Non-Heap Memory", usedNonHeap, maxNonHeap}
        }, new Object[]{"Memory Type", "Used", "Max"});
    }

    private String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        char pre = "KMGTPE".charAt(exp - 1);
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }

    private void exportMemoryInformation() {
        // Implement logic to export memory information to a file
    }

    private void toggleDarkMode() {
        // Implement dark mode toggle logic here
    }

    private void showSystemInfo() {
        // Implement logic to display system information in a dialog
        String os = System.getProperty("os.name");
        String architecture = System.getProperty("os.arch");
        int processors = Runtime.getRuntime().availableProcessors();

        String message = String.format("Operating System: %s%nArchitecture: %s%nProcessors: %d", os, architecture, processors);

        JOptionPane.showMessageDialog(this, message, "System Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void toggleMemoryGraph() {
        // Implement logic to toggle the visibility of the memory usage graph
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI().setVisible(true));
    }
}
