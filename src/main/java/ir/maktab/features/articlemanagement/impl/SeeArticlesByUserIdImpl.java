package ir.maktab.features.articlemanagement.impl;
import ir.maktab.entities.Article;
import ir.maktab.entities.User;
import ir.maktab.features.articlemanagement.usecase.SeeArticlesByUserId;
import ir.maktab.repositories.ArticleRepository;

import java.util.List;
import java.util.stream.Collectors;

public class SeeArticlesByUserIdImpl implements SeeArticlesByUserId {
    static ArticleRepository articleRepository = ArticleRepository.getInstance();
    @Override
    public void listArticles(User user) {
        List<Article> articles = null;
        try {
            articles = articleRepository.findAll().stream().
                    filter(Article -> Article.getUser().getId() == user.getId()).
                    collect(Collectors.toList());
        }catch (Exception e){}

        if (articles.size() == 0)
            System.out.println("You dont have any articles...");
        else {
            for (Article a : articles) {
                System.out.println(a);
            }
        }
    }
}
