package com.example.testapp2;

public class CategoryModel {
    private String categoryIconIink;
    private String categorynName;

    public CategoryModel(String categoryIconIink, String categorynName) {

        this.categoryIconIink = categoryIconIink;
        this.categorynName = categorynName;
    }

    public String getCategoryIconIink() {
        return categoryIconIink;
    }

    public void setCategoryIconIink(String categoryIconIink) {
        this.categoryIconIink = categoryIconIink;
    }

    public String getCategorynName() {
        return categorynName;
    }

    public void setCategorynName(String categorynName) {
        this.categorynName = categorynName;
    }
}
