import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import javax.swing.filechooser.FileSystemView;
public class Memory extends JFrame {

    private JTable table;
    public Memory() {
        setTitle("Memory Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createTable(), BorderLayout.CENTER);

        displayMemoryInformation();

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(65, 105, 225)); // Royal Blue
        headerPanel.setForeground(Color.WHITE);
        headerPanel.setFont(new Font("Arial", Font.BOLD, 24));
        // headerPanel.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        headerPanel.setOpaque(true);
        headerPanel.setBackground(new Color(65, 105, 225));
        
  
    

        JLabel titleLabel = new JLabel("Memory Information");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER); 
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        titleLabel.setOpaque(true); 
        titleLabel.setBackground(new Color(65, 105, 225)); 
        titleLabel.setForeground(getForeground());
        titleLabel.setFont(getFont());
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        titleLabel.setOpaque(true); 
        titleLabel.setBackground(new Color(65, 105, 225)); 
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(new JLabel(" "), BorderLayout.SOUTH);


        return headerPanel;
    }

    private JPanel createTable() {
        JPanel tablePanel = new JPanel(new BorderLayout());

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{{"", "", "", "", "", "", ""}},
                new String[]{"Device", "Total Space", "Used Space", "Free Space", "Use", "Type", "Mount"}
                // add additional functionaliy
                
        ));
        table.getTableHeader().setReorderingAllowed(false);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private void displayMemoryInformation() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        File[] roots = File.listRoots();
        for (File root : roots) {
            String device = root.getAbsolutePath();
            long totalSpace = root.getTotalSpace();
            long freeSpace = root.getFreeSpace();
            long usedSpace = totalSpace - freeSpace;
            long usePercent = (long) (usedSpace * 100.0 / totalSpace);
            String type = FileSystemView.getFileSystemView().getSystemTypeDescription(root);
        
                   
            model.addRow(new Object[]{
                device,
                bytes2human(totalSpace),
                bytes2human(usedSpace),
                bytes2human(freeSpace),
                usePercent + "%",
                type,
                root.getAbsolutePath()
            });
        }
    }

    private String bytes2human(long size) {
        int unit = 1024;
        if (size < unit) return size + " B";
        int exp = (int) (Math.log(size) / Math.log(unit));
        String pre = "KMGTPE".charAt(exp - 1) + "i";
        return String.format("%.1f %sB", size / Math.pow(unit, exp), pre);
        // add additional functionality give me the functionalit of the code

    }
   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Memory::new);
    }
}
