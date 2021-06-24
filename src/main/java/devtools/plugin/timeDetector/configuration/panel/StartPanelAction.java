package devtools.plugin.timeDetector.configuration.panel;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class StartPanelAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        new InfoPanel();
    }

    @Override
    public boolean isDumbAware() {
        return super.isDumbAware();
    }
}
