package edu.pet.project.petrova.extension.panel;

import com.intellij.icons.AllIcons;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.ProjectScope;
import com.intellij.ui.components.panels.NonOpaquePanel;
import com.intellij.ui.table.TableView;
import com.intellij.util.indexing.FileBasedIndex;
import edu.pet.project.petrova.client.GofPatternPredictService;
import edu.pet.project.petrova.extension.table.PatternPredictionTableModel;
import edu.pet.project.petrova.model.FileForPrediction;
import edu.pet.project.petrova.model.PatternPrediction;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.BorderFactory;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
public class AnalyzePanel extends NonOpaquePanel {
    private final transient GofPatternPredictService gofPatternPredictService;
    private final TableView<PatternPrediction> resultsTable;
    private final PatternPredictionTableModel patternPredictionTableModel;

    public AnalyzePanel(PatternPredictionTableModel tableModel, TableView<PatternPrediction> resultsTable) {
        this.gofPatternPredictService = new GofPatternPredictService();
        this.patternPredictionTableModel = tableModel;
        this.resultsTable = resultsTable;
        this.init();
    }

    private void init() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBorder(BorderFactory.createEmptyBorder());

        ActionToolbar toolbar = this.createToolbar();
        toolbar.setTargetComponent(this);
        this.add(toolbar.getComponent());
    }

    @NotNull
    private ActionToolbar createToolbar() {
        DefaultActionGroup actionGroup = new DefaultActionGroup();
        actionGroup.add(new PredictPatternAction());
        actionGroup.add(new ClearTableAction());
        return ActionManager.getInstance().createActionToolbar(ActionPlaces.TOOLBAR, actionGroup, true);
    }

    private void predictAllClasses() {
        List<PsiJavaFile> filesForPrediction = findAllClasses();
        List<PatternPrediction> predictions = new ArrayList<>();

        for (PsiJavaFile file : filesForPrediction) {
            FileForPrediction fileForPrediction = FileForPrediction.builder()
                    .packagePath(file.getPackageName())
                    .className(file.getName())
                    .classText(file.getText())
                    .build();
            PatternPrediction response = gofPatternPredictService.predict(fileForPrediction);
            if (response != null) {
                log.debug(response.toString());
                predictions.add(response);
            }
        }

        if (predictions.isEmpty()) {
            this.resultsTable.getEmptyText().setText("No results found for your search criteria");
        }
        this.patternPredictionTableModel.setItems(predictions);
        this.resultsTable.updateColumnSizes();
    }

    private List<PsiJavaFile> findAllClasses() {
        List<PsiJavaFile> javaFiles = new ArrayList<>();
        for (Project project : ProjectManager.getInstance().getOpenProjects()) {
            PsiManager psiManager = PsiManager.getInstance(project);
            GlobalSearchScope scope = ProjectScope.getProjectScope(project);

            Collection<VirtualFile> virtualFiles = FileTypeIndex.getFiles(JavaFileType.INSTANCE, scope);
            for (VirtualFile virtualFile : virtualFiles) {
                PsiFile psiFile = psiManager.findFile(virtualFile);
                if (psiFile instanceof PsiJavaFile psiJavaFile) {
                    javaFiles.add(psiJavaFile);
                }
            }
        }
        return javaFiles;
    }

    private void clearResults() {
        this.patternPredictionTableModel.setItems(Collections.emptyList());
    }

    public class PredictPatternAction extends DumbAwareAction {
        protected PredictPatternAction() {
            super("Predict Patterns", "Predict GoF patterns for all Java classes", AllIcons.Actions.Execute);
        }

        @Override
        public void actionPerformed(@NotNull AnActionEvent e) {
            AnalyzePanel.this.predictAllClasses();
        }
    }

    public class ClearTableAction extends DumbAwareAction {
        protected ClearTableAction() {
            super("Clear Results", "Clear results", AllIcons.Actions.Refresh);
        }

        @Override
        public void actionPerformed(@NotNull AnActionEvent e) {
            AnalyzePanel.this.clearResults();
        }
    }

}