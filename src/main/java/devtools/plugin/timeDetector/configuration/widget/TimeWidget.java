package devtools.plugin.timeDetector.configuration.widget;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.CustomStatusBarWidget;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.impl.status.EditorBasedWidget;
import com.intellij.ui.awt.RelativePoint;
import devtools.plugin.timeDetector.configuration.TimeDetector;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimeWidget extends EditorBasedWidget implements CustomStatusBarWidget {
    private final JLabel timerLabel = new JLabel("00:00:00");

    private final ExecutorService service = Executors.newSingleThreadExecutor();
    private static long restTime = 0L;

    private static boolean isRest = false;
    private static LocalTime restStartTime = LocalTime.now();
    private static LocalTime restEndTime = LocalTime.now();

    protected TimeWidget(@NotNull Project project) {
        super(project);
        timerLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (isRest) {
                    stopRest();
                } else {
                    startRest();
                }
            }
        });
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
        int lastInfo = 600;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if (!isRest) {
                    if (restEndTime.until(LocalTime.now(), ChronoUnit.HOURS) > 4L) {
                        if (lastInfo == 600) {
                            lastInfo = 0;
                            getInfoMessage(statusBar);
                        }
                        ++lastInfo;
                    }

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

    private void getInfoMessage(StatusBar statusBar) {
        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder("You have been working for a long time without a rest. Please, stop for a while",
                        MessageType.WARNING, null)
                .setFadeoutTime(5000)
                .createBalloon()
                .show(RelativePoint.getCenterOf(statusBar.getComponent()),
                        Balloon.Position.atRight);
    }

    public static boolean isRest() {
        return isRest;
    }

    public static String getWorkTime() {
        return LocalTime
                .ofSecondOfDay(TimeDetector.startTime.until(LocalTime.now(), ChronoUnit.SECONDS) - restTime)
                .format(TimeDetector.dateFormat);
    }

    public static String getRestTime() {
        return LocalTime.ofSecondOfDay(restTime).format(TimeDetector.dateFormat);
    }

    public static void startRest() {
        if (!isRest) {
            isRest = true;
            restStartTime = LocalTime.now();
        }
    }

    public static void stopRest() {
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
