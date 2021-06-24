package devtools.plugin.timeDetector.configuration.panel;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class StartPanelAction extends AnAction {
    /**
     * Implement this method to provide your action handler.
     *
     * @param e Carries information on the invocation place
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        InfoPanel panel = new InfoPanel();
        panel.pack();
        panel.setVisible(true);
    }

    @Override
    public boolean isDumbAware() {
        return super.isDumbAware();
    }
}
