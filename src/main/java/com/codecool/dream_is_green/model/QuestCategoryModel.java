package com.codecool.dream_is_green.model;

public class QuestCategoryModel extends AbstractTaskCategory {

    public QuestCategoryModel(String name) {

        this.name = name;
    }

    @Override
    public String toString() {

        return this.name;

    }
}
