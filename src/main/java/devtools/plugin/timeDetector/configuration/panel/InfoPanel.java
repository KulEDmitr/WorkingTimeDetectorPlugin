package devtools.plugin.timeDetector.configuration.panel;

import com.intellij.ui.JBColor;
import devtools.plugin.timeDetector.configuration.widget.TimeWidget;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JFrame {

    public InfoPanel() {
        super("Detected Time Information");

        JButton start = new JButton("Start");
        start.setBackground(JBColor.CYAN);
        start.addActionListener(e -> TimeWidget.stopRest());

        JButton stop = new JButton("Stop");
        stop.setBackground(JBColor.PINK);
        stop.addActionListener(e -> TimeWidget.startRest());

        JLabel timeInWork = new JLabel(String.format("time spend in work: %s", TimeWidget.getWorkTime()));
        JLabel timeInRest = new JLabel(String.format("time spend for rest: %s", TimeWidget.getRestTime()));

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(2, 3, 2, 2));
        container.add(timeInWork);
        container.add(stop);
        container.add(timeInRest);
        container.add(start);
    }
}
