package com.codecool.dream_is_green.model;

public class ArtifactCategoryModel extends AbstractTaskCategory {

    public ArtifactCategoryModel(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;

    }
}
