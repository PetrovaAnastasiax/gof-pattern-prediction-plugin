package edu.pet.project.petrova.extension.table;

import com.intellij.ui.table.TableView;
import com.intellij.util.ui.ListTableModel;
import edu.pet.project.petrova.model.PatternPrediction;

import java.awt.Cursor;

import static java.awt.Cursor.HAND_CURSOR;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

public class ResultsTable extends TableView<PatternPrediction> {
    public ResultsTable(ListTableModel<PatternPrediction> model) {
        super(model);
        this.init();
    }

    private void init() {
        this.getSelectionModel().setSelectionMode(SINGLE_SELECTION);
        this.setCellSelectionEnabled(true);
        this.setStriped(true);
        this.setCursor(new Cursor(HAND_CURSOR));
        this.setAutoCreateRowSorter(true);
        this.getEmptyText().setText("Analyze your project to find out which of the GoF pattern you used");
    }
}
