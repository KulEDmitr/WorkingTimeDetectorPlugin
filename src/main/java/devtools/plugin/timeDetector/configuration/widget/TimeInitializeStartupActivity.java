package devtools.plugin.timeDetector.configuration.widget;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.WindowManager;
import org.jetbrains.annotations.NotNull;

public class TimeInitializeStartupActivity implements StartupActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        StatusBar window = WindowManager
                .getInstance()
                .getStatusBar(project);
        StatusBarWidget widget = new TimeWidgetFactory().createWidget(project);
        window.addWidget(widget, "last", project);
        widget.install(window);

        Messages.showMessageDialog("Work time detector was started. See more if click in down right corner",
                "TimeDetector",
                Messages.getInformationIcon());
    }
}
