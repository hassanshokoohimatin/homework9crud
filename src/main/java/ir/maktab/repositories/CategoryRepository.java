package ir.maktab.repositories;

import ir.maktab.config.repositories.CrudRepository;
import ir.maktab.entities.Category;

public class CategoryRepository extends CrudRepository<Category , Long> {

    private static CategoryRepository categoryRepository;
    private CategoryRepository(){}

    public static CategoryRepository getInstance(){
        if (categoryRepository == null)
            categoryRepository = new CategoryRepository();
        return categoryRepository;
    }

    @Override
    protected Class<Category> getEntityClass() {
        return Category.class;
    }
}
