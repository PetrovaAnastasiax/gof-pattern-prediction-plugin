package edu.pet.project.petrova.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileForPrediction {
    private String packagePath;
    private String className;
    private String classText;
}
