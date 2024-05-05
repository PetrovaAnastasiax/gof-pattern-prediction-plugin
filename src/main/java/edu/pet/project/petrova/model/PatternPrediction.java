package edu.pet.project.petrova.model;

import lombok.Data;

@Data
public class PatternPrediction {
    private String packagePath;
    private String className;
    private Pattern predictedPattern;
}
