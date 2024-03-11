import com.sun.management.OperatingSystemMXBean;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;




public class CPU {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("System Monitor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        CpuPanel cpuPanel = new CpuPanel();
        mainPanel.add(cpuPanel, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cpuPanel.updateInformation();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);

        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class CpuPanel extends JPanel {

    private static final int NUM_CPUS = Runtime.getRuntime().availableProcessors();
    private static final boolean CPUS_HIDDEN = NUM_CPUS > 8;
    private double[] cpusPercent;
    private Map<Integer, List<String>> processes;
    private SystemInfo systemInfo;

    public CpuPanel() {
        cpusPercent = new double[NUM_CPUS];
        processes = getProcesses();
        systemInfo = new SystemInfo();
        updateInformation();
    }

    private Map<Integer, List<String>> getProcesses() {
        return null;
    }

    public void updateInformation() {
        for (int i = 0; i < NUM_CPUS; i++) {
            cpusPercent[i] = getCPUPercent(i);
        }
        processes = getProcesses();
        repaint();
    }

    private double getCPUPercent(int cpuNum) {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return osBean.getProcessCpuLoad() * 100;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 12));

        int yOffset = 20;

        // Header
        g2d.drawString(String.format("%-12s", "CPU"), 10, yOffset);
        for (int i = 0; i < NUM_CPUS; i++) {
            g2d.drawString(String.format("%-12s", i), 10 + (i + 1) * 80, yOffset);
        }
        if (CPUS_HIDDEN) {
            g2d.drawString("(+ hidden)", 10 + (NUM_CPUS + 1) * 80, yOffset);
        }

        // CPU Percentages
        for (int i = 0; i < NUM_CPUS; i++) {
            g2d.drawString(String.format("%-12s", String.format("%.1f%%", cpusPercent[i])),
                    10 + (i + 1) * 80, yOffset + 20);
        }

        // Processes
        int currLine = 3;
        while (true) {
            for (int num = 0; num < NUM_CPUS; num++) {
                try {
                    String pname = processes.get(num).remove(0);
                    g2d.drawString(String.format("%-12s", pname.length() > 12 ? pname.substring(0, 12) : pname),
                            10 + (num + 1) * 80, yOffset + (currLine + 1) * 20);
                } catch (IndexOutOfBoundsException e) {
                    g2d.drawString(String.format("%-12s", ""), 10 + (num + 1) * 80, yOffset + (currLine + 1) * 20);
                }
            }
            currLine++;

            if (currLine >= getHeight() / 20) {
                break;
            }
        }

        int textX = 10;
        int textY = getHeight() - 50;

        // Display real-time CPU usage
        g2d.drawString(String.format("%-12s", "Real-Time CPU"), textX, textY);
        for (int i = 0; i < NUM_CPUS; i++) {
            g2d.drawString(String.format("%-12s", String.format("%.1f%%", cpusPercent[i])),
                    10 + (i + 1) * 80, textY);
        }

        // Display CPU Details and Memory Information
        g2d.drawString(systemInfo.getCpuDetails(), textX, textY + 20);
        g2d.drawString(systemInfo.getMemoryInfo(), textX, textY + 35);

        // Display Timestamp
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = dateFormat.format(new Date());
        g2d.drawString("Last Refresh: " + timestamp, textX, textY + 50);
    }
}

class SystemInfo {

    public String getCpuDetails() {
        return null;
    }
   

    public String getMemoryInfo() {
        return null;
    }

    public HardwareAbstractionLayer getHardware() {
        return null;
    }
}
