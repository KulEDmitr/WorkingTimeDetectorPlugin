package devtools.plugin.timeDetector.configuration.widget;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.CustomStatusBarWidget;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.impl.status.EditorBasedWidget;
import devtools.plugin.timeDetector.configuration.TimeDetector;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimeWidget extends EditorBasedWidget implements CustomStatusBarWidget {
    private final JLabel timerLabel = new JLabel("00:00:00");

    private final ExecutorService service = Executors.newSingleThreadExecutor();
    private long restTime = 0L;

    private static boolean isRest = false;
    private static LocalTime restStartTime;
    private static LocalTime restEndTime;


    protected TimeWidget(@NotNull Project project) {
        super(project);
    }

    @Override
    public JComponent getComponent() {
        return timerLabel;
    }

    @NonNls
    @NotNull
    @Override
    public String ID() {
        return TimeWidget.class.getName();
    }

    @Override
    public void install(@NotNull StatusBar statusBar) {
        service.submit(() -> continuousTimeWidgetStatusUpdate(statusBar));
    }

    private void continuousTimeWidgetStatusUpdate(StatusBar statusBar) {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if (!isRest) {
                    timerLabel.setText(LocalTime
                            .ofSecondOfDay(TimeDetector.startTime.until(LocalTime.now(), ChronoUnit.SECONDS) - restTime)
                            .format(TimeDetector.dateFormat));
                    statusBar.updateWidget(ID());
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isRest() {
        return isRest;
    }

    public static void startRest() {
        if (!isRest) {
            isRest = true;
            restStartTime = LocalTime.now();
        }
    }

    public void stopRest() {
        if (isRest) {
            isRest = false;
            restTime += restStartTime.until(LocalTime.now(), ChronoUnit.SECONDS);
            restEndTime = LocalTime.now();
        }
    }

    @Override
    public void dispose() {
        service.shutdownNow();
    }

}
