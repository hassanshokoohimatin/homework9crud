package ir.maktab.features.articlemanagement.usecase;

import ir.maktab.entities.User;

public interface SeeArticlesByUserId {

    void listArticles(User user);
}
