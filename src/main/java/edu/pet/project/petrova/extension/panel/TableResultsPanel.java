package edu.pet.project.petrova.extension.panel;

import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.components.panels.NonOpaquePanel;
import com.intellij.ui.table.TableView;
import edu.pet.project.petrova.model.PatternPrediction;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import static java.awt.BorderLayout.CENTER;

public class TableResultsPanel extends NonOpaquePanel {
    private final TableView<PatternPrediction> resultsTable;

    public TableResultsPanel(TableView<PatternPrediction> resultsTable) {
        this.resultsTable = resultsTable;
        this.init();
    }

    private void init() {
        this.setBorder(BorderFactory.createEmptyBorder());
        JPanel scrollPanel = new JPanel();
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setLayout(new BorderLayout());
        scrollPanel.add(ScrollPaneFactory.createScrollPane(this.resultsTable), CENTER);
        this.setLayout(new BorderLayout());
        this.add(scrollPanel, CENTER);
    }
}