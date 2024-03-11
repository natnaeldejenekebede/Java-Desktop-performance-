import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;



public class battery {

    private JLabel chargeLabel;
    private JLabel statusLabel;
    private JLabel pluggedInLabel;
    private JLabel leftLabel;
    private JLabel titleLabel;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            battery batteryInfo = new battery();
            batteryInfo.createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Battery Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        chargeLabel = new JLabel();
        statusLabel = new JLabel();
        pluggedInLabel = new JLabel();
        pluggedInLabel.setForeground(Color.RED);
        pluggedInLabel.setFont(new Font("Serif", Font.BOLD, 20));
        pluggedInLabel.setHorizontalAlignment(JLabel.CENTER);
        pluggedInLabel.setVerticalAlignment(JLabel.CENTER);
        leftLabel = new JLabel();

        panel.add(new JLabel("Charge:"));
        panel.add(chargeLabel);
        panel.add(new JLabel("Status:"));
        panel.add(statusLabel);
        panel.add(new JLabel("Plugged In:"));
        panel.add(pluggedInLabel);
        panel.add(new JLabel("Left:"));
        panel.add(leftLabel);

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(300, 150);
        frame.setVisible(true);

        showBatteryInformation();
    }
    
    private void showBatteryInformation() {
        Battery battery = Battery.getSensorsBattery();
        if (battery == null) {
            System.exit(1); 
        }

        chargeLabel.setText(round(battery.getPercent(), 2) + "%");

        if (battery.isPowerPlugged()) {
            statusLabel.setText(battery.getPercent() < 100 ? "Charging" : "Fully Charged");
            pluggedInLabel.setText("Yes");
        } else {
            leftLabel.setText(secsToHours(battery.getSecsLeft()));
            statusLabel.setText("Discharging");
            pluggedInLabel.setText("No");
        }
    }

    private String round(double value, int decimalPlaces) {
        DecimalFormat format = new DecimalFormat("#." + "0".repeat(decimalPlaces));
        return format.format(value);
    }

    private String secsToHours(long secs) {
        long mm = secs / 60;
        long ss = secs % 60;
        long hh = mm / 60;
        mm %= 60;
        return String.format("%d:%02d:%02d", hh, mm, ss);
    }

    private static class Battery {

        private double percent;
        private long secsLeft;
        private boolean powerPlugged;

        private Battery(double percent, long secsLeft, boolean powerPlugged) {
            this.percent = percent;
            this.secsLeft = secsLeft;
            this.powerPlugged = powerPlugged;
        }

        public double getPercent() {
            return percent;
        }

        public long getSecsLeft() {
            return secsLeft;
        }

        public boolean isPowerPlugged() {
            return powerPlugged;
        }

        public static Battery getSensorsBattery() {
          
            return new Battery(74, 2 * 60 * 60, false);
        
        }
    }
}
