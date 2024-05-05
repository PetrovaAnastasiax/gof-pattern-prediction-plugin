package edu.pet.project.petrova.extension.table;

import com.intellij.icons.AllIcons;
import com.intellij.ui.components.labels.BoldLabel;
import com.intellij.ui.components.labels.LinkLabel;
import com.intellij.util.ui.ColumnInfo;
import com.intellij.util.ui.ListTableModel;
import edu.pet.project.petrova.model.PatternPrediction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.table.TableCellRenderer;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PatternPredictionTableModel extends ListTableModel<PatternPrediction> {
    public PatternPredictionTableModel(ColumnInfo @NotNull [] columnNames, @NotNull List<PatternPrediction> patternPredictions) {
        super(columnNames, patternPredictions);
    }

    static final String[] COLUMNS = {"Package", "Class", "Predicted Pattern", "Info", "Read More"};

    public static ColumnInfo<PatternPrediction, String>[] generateColumnInfo() {
        ColumnInfo<PatternPrediction, String>[] columnInfos = new ColumnInfo[COLUMNS.length];
        AtomicInteger i = new AtomicInteger();
        Arrays.stream(COLUMNS).forEach(eachColumn -> {
                    columnInfos[i.get()] = new ColumnInfo<>(eachColumn) {
                        @Nullable
                        @Override
                        public String valueOf(PatternPrediction o) {
                            return switch (eachColumn) {
                                case "Package" -> o.getPackagePath();
                                case "Class" -> o.getClassName();
                                case "Predicted Pattern" -> o.getPredictedPattern().getPatternName();
                                case "Info" -> o.getPredictedPattern().getInfo();
                                case "Read More" -> o.getPredictedPattern().getAdditionalInfoUrl();
                                default -> "Not Available";
                            };
                        }

                        @Override
                        public TableCellRenderer getCustomizedRenderer(PatternPrediction o, TableCellRenderer renderer) {
                            return switch (eachColumn) {
                                case "Tags" ->
                                        (table, value, isSelected, hasFocus, row, column) -> new BoldLabel(value.toString());
                                case "Link" ->
                                        (table, value, isSelected, hasFocus, row, column) -> new LinkLabel<String>(value.toString(), AllIcons.Ide.External_link_arrow);
                                default -> super.getCustomizedRenderer(o, renderer);
                            };
                        }

                    };
                    i.getAndIncrement();
                }
        );
        return columnInfos;
    }
}