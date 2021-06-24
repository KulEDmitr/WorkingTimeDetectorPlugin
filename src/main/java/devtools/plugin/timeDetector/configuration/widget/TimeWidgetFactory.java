package devtools.plugin.timeDetector.configuration.widget;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.StatusBarWidgetFactory;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class TimeWidgetFactory implements StatusBarWidgetFactory {

    @NonNls
    @NotNull
    @Override
    public String getId() {
        return TimeWidget.class.getName();
    }

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "Time in work";
    }

    @Override
    public boolean isAvailable(@NotNull Project project) {
        return true;
    }

    @NotNull
    @Override
    public StatusBarWidget createWidget(@NotNull Project project) {
        return new TimeWidget(project);
    }

    @Override
    public void disposeWidget(@NotNull StatusBarWidget widget) {
    }

    @Override
    public boolean canBeEnabledOn(@NotNull StatusBar statusBar) {
        return true;
    }

    @Override
    public boolean isEnabledByDefault() {
        return StatusBarWidgetFactory.super.isEnabledByDefault();
    }

    @Override
    public boolean isConfigurable() {
        return StatusBarWidgetFactory.super.isConfigurable();
    }
}
