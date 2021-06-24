package devtools.plugin.timeDetector.configuration.panel;

import devtools.plugin.timeDetector.configuration.widget.TimeWidget;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JFrame {
    private JButton start;
    private JButton stop;
    private JLabel timeInWork;
    private JLabel timeInRest;
    private JPanel infoPanel;

    public InfoPanel() {
        super("Working Time Detector");
        setParameters();
        getContentPane().add(infoPanel);
        setPreferredSize(new Dimension(700, 200));
        pack();
        setVisible(true);
    }

    public void setParameters() {
        start.addActionListener(e -> TimeWidget.stopRest());
        stop.addActionListener(e -> TimeWidget.startRest());
        timeInWork.setText(String.format("time spend in work: %s", TimeWidget.getWorkTime()));
        timeInRest.setText(String.format("time spend for rest: %s", TimeWidget.getRestTime()));
    }
}
