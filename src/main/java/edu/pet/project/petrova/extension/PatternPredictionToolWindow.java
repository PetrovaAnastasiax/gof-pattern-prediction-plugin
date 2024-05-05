package edu.pet.project.petrova.extension;

import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.ui.Splitter;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.OnePixelSplitter;
import com.intellij.ui.SideBorder;
import edu.pet.project.petrova.extension.panel.AnalyzePanel;
import edu.pet.project.petrova.extension.panel.TableResultsPanel;
import edu.pet.project.petrova.extension.table.PatternPredictionTableModel;
import edu.pet.project.petrova.extension.table.ResultsTable;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.util.ArrayList;

import static com.intellij.openapi.ui.Splitter.DividerPositionStrategy.KEEP_FIRST_SIZE;
import static com.intellij.ui.SideBorder.BOTTOM;
import static com.intellij.ui.SideBorder.RIGHT;
import static com.intellij.ui.SideBorder.TOP;

public class PatternPredictionToolWindow {
    private final JPanel contentToolWindow;

    public JComponent getContent() {
        return this.contentToolWindow;
    }

    public PatternPredictionToolWindow() {
        this.contentToolWindow = new SimpleToolWindowPanel(true, true);
        PatternPredictionTableModel tableModel = new PatternPredictionTableModel(PatternPredictionTableModel.generateColumnInfo(), new ArrayList<>());
        ResultsTable resultsTable = new ResultsTable(tableModel);

        TableResultsPanel tableResultsPanel = new TableResultsPanel(resultsTable);
        tableResultsPanel.setBorder(IdeBorderFactory.createBorder(TOP | RIGHT));

        AnalyzePanel analyzePanel = new AnalyzePanel(tableModel, resultsTable);
        analyzePanel.setBorder(IdeBorderFactory.createBorder(TOP | RIGHT | BOTTOM));

        OnePixelSplitter horizontalSplitter = new OnePixelSplitter(true, 0.0f);
        horizontalSplitter.setBorder(BorderFactory.createEmptyBorder());
        horizontalSplitter.setDividerPositionStrategy(KEEP_FIRST_SIZE);
        horizontalSplitter.setResizeEnabled(false);
        horizontalSplitter.setFirstComponent(analyzePanel);
        horizontalSplitter.setSecondComponent(tableResultsPanel);

        this.contentToolWindow.add(horizontalSplitter);

    }
}
