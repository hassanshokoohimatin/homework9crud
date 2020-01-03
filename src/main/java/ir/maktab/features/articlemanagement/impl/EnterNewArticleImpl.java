package ir.maktab.features.articlemanagement.impl;

import ir.maktab.entities.Article;
import ir.maktab.entities.Category;
import ir.maktab.entities.Tag;
import ir.maktab.entities.User;
import ir.maktab.features.articlemanagement.usecase.EnterNewArticle;
import ir.maktab.repositories.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class EnterNewArticleImpl implements EnterNewArticle {

    static ArticleRepository articleRepository = ArticleRepository.getInstance();
    static CategoryRepository categoryRepository = CategoryRepository.getInstance();
    static TagRepository tagRepository = TagRepository.getInstance();

    @Override
    public void createArticle(User user) {
        Scanner scanner = new Scanner(System.in);
        Article article = new Article();
        article.setId(null);
        article.setUser(user);
        System.out.println("Enter title : ");
        String title = scanner.next();
        article.setTitle(title);
        System.out.println("Enter brief : ");
        String brief = scanner.next();
        article.setBrief(brief);
        System.out.println("Enter content : ");
        String content = scanner.next();
        article.setContent(content);
        System.out.println("Enter create date : ");
        String createDate = scanner.next();
        article.setCreateDate(createDate);
        System.out.println("Enter publish date : ");
        String publishDate = scanner.next();
        article.setPublishDate(publishDate);
        article.setLastUpdateDate(new Date().toString());
        if (user.getRoles().size()==1){
            article.setIsPublished("no");
        }else {
            System.out.println("Enter publishing condition : (yes or no)");
            String isPublished = scanner.next();
            article.setIsPublished(isPublished);
        }
        List<Category> categories = categoryRepository.findAll();
        System.out.println("id\t\t\ttitle");
        for (Category c : categories) {
            System.out.printf("%d\t\t\t%s\n", c.getId(), c.getTitle());
        }
        System.out.println("Enter the id of category to add");
        int categoryChoice = scanner.nextInt();
        try {
            article.setCategory(categories.get(categoryChoice - 1));
            System.out.println("category added...");
        }catch (Exception e){
            System.out.println("failed to add category");
        }

        List<Tag> tagList = tagRepository.findAll();
        System.out.println("id\t\t\ttitle");
        for (Tag t : tagList){
            System.out.printf("%d\t\t\t%s\n",t.getId(),t.getTitle());
        }
        System.out.println("Which tags do you want to add?...enter 0 to end entering id...");
        List<Tag> tags = new ArrayList<>();
        int choice = 1;
        while (choice!=0){
            choice = scanner.nextInt();
            try {
                tags.add(tagList.get(choice - 1));
                System.out.print("added");
            }catch (Exception e){
                if (choice==0)
                    System.out.print("done");
                else
                    System.out.print("failed!!!");
            }
        }

        article.setTags(tags);

        articleRepository.save(article);
    }
}
