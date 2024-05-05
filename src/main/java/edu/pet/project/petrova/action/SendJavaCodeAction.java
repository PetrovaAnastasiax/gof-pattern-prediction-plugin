package edu.pet.project.petrova.action;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import edu.pet.project.petrova.client.GofPatternPredictService;
import edu.pet.project.petrova.model.FileForPrediction;
import edu.pet.project.petrova.model.PatternPrediction;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class SendJavaCodeAction extends AnAction {
    private final GofPatternPredictService gofPatternPredictService;

    public SendJavaCodeAction() {
        super();
        gofPatternPredictService = new GofPatternPredictService();
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        // Set the availability based on whether a project is open
        Project project = e.getProject();
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);

        boolean isVisible = project != null && psiFile != null && psiFile.getName().endsWith(".java");
        e.getPresentation().setVisible(isVisible);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return super.getActionUpdateThread();
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PsiJavaFile psiJavaFile = (PsiJavaFile) e.getData(CommonDataKeys.PSI_FILE);

        if (psiJavaFile != null) {
            FileForPrediction fileForPrediction = FileForPrediction.builder()
                    .packagePath(psiJavaFile.getPackageName())
                    .className(psiJavaFile.getName())
                    .classText(psiJavaFile.getText())
                    .build();
            PatternPrediction response = gofPatternPredictService.predict(fileForPrediction);
            if (response != null) {
                log.debug(response.toString());
                Messages.showInfoMessage("The class: %s%nLocated in package: %s%nIs %s".formatted(
                        response.getClassName(),
                        response.getPackagePath(),
                        response.getPredictedPattern()), "Prediction");
            }
        }
    }
}
