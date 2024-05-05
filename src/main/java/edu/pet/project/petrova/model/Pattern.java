package edu.pet.project.petrova.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Pattern {
    ADAPTER(
            "Adapter",
            "Adapter is a structural design pattern that allows objects with incompatible interfaces to collaborate",
            "https://www.digitalocean.com/community/tutorials/adapter-design-pattern-java"
    ),
    BUILDER(
            "Builder",
            "Builder is a creational design pattern that lets you construct complex objects step by step. The pattern allows you to produce different types and representations of an object using the same construction code",
            "https://www.digitalocean.com/community/tutorials/builder-design-pattern-in-java"
    ),
    PROTOTYPE(
            "Prototype",
            "Prototype is a creational design pattern that lets you copy existing objects without making your code dependent on their classes",
            "https://www.digitalocean.com/community/tutorials/prototype-design-pattern-in-java"
    ),
    SINGLETON(
            "Singleton",
            "Singleton is a creational design pattern that lets you ensure that a class has only one instance, while providing a global access point to this instance",
            "https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples"
    ),
    NONE("-","", "");
    private final String patternName;
    private final String info;
    private final String additionalInfoUrl;
}
