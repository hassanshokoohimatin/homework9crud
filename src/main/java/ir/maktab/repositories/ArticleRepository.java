package ir.maktab.repositories;

import ir.maktab.config.repositories.CrudRepository;
import ir.maktab.entities.Article;

public class ArticleRepository extends CrudRepository<Article , Long> {

    private static ArticleRepository articleRepository;
    private ArticleRepository(){}

    public static ArticleRepository getInstance(){
        if (articleRepository == null)
            articleRepository = new ArticleRepository();
        return articleRepository;
    }

    @Override
    protected Class<Article> getEntityClass() {
        return Article.class;
    }
}
