package ir.maktab;

import ir.maktab.entities.*;
import ir.maktab.repositories.*;
import ir.maktab.share.AuthenticationService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Application {
    static UserRepository userRepository = UserRepository.getInstance();
    static ArticleRepository articleRepository = ArticleRepository.getInstance();
    static CategoryRepository categoryRepository = CategoryRepository.getInstance();
    static TagRepository tagRepository = TagRepository.getInstance();
    static RoleRepository roleRepository = RoleRepository.getInstance();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String manner = "";
        while ( ! manner.equals("exit")){
            System.out.print("Sign in\nSign up\nSee articles\nexit\n");
            manner = scanner.nextLine();
            if (manner.equals("sign up")){


                User loginUser = AuthenticationService.getInstance().getLoginUser();
                if (loginUser == null) {
                    int existUserCount = 0;
                    System.out.println("Enter username : ");
                    String username = scanner.next();
                    System.out.println("Enter national code : ");
                    String nationalCode = scanner.next();
                    List<User> userList = userRepository.findAll();
                    for (User u : userList) {
                        if (u.getUsername().equals(username) || u.getNationalCode().equals(nationalCode)) {
                            existUserCount++;
                            break;
                        }
                    }
                    if (existUserCount == 0) {
                        System.out.println("Enter birthday : ");
                        String birthday = scanner.next();
                        Role role = new Role(null,"writer");
                        List<Role> roleList = new ArrayList<>();
                        roleList.add(role);
                        User user = new User(null, username, nationalCode, birthday, nationalCode,null,roleList);
                        userRepository.save(user);
                        System.out.println("signed up successfully...you can sign in now as a writer");
                    } else {
                        System.out.println("This username or password already exist...try another");
                    }

                } else {
                    System.out.printf("the user %s is logged in...", loginUser.getUsername());
                    AuthenticationService.getInstance().setLoginUser(null);
                    System.out.println("signed out successfully...");
                }
            }
            if(manner.equals("see articles")){

                List<Article> articles = articleRepository.findAll();

                if (articles.size() == 0) {
                    System.out.println("there is no articles...");
                } else {
                    System.out.println("id\t\ttitle\t\tbrief");
                    for (Article a : articles) {
                        System.out.printf("%d\t\t%s\t\t%s\n", a.getId(), a.getTitle(), a.getBrief());
                    }
                    System.out.println("enter the id of the article too see more detail or press 0 to back to main menu");
                    Long id = scanner.nextLong();
                    if (id != 0) {
                        for (Article a : articles) {
                            if (id == a.getId()) {
                                System.out.println(articleRepository.findById(id));
                                break;
                            }
                        }
                    }
                }
            }
            if (manner.equals("exit")){
                System.out.println("END");
            }
            if (manner.equals("sign in")){
                User loginUser = AuthenticationService.getInstance().getLoginUser();
                if (loginUser == null) {
                    System.out.println("Enter username : ");
                    String username = scanner.next();
                    System.out.println("Enter password : ");
                    String password = scanner.next();
                    try {
                        List<User> users = userRepository.findAll().stream().
                                filter(User -> User.getUsername().equals(username)).
                                filter(User -> User.getPassword().equals(password)).
                                collect(Collectors.toList());
                        loginUser = users.get(0);
                    }catch (Exception e){ loginUser =null; }

                    AuthenticationService.getInstance().setLoginUser(loginUser);
                    if (loginUser == null) {
                        System.out.println("Wrong username or password...");
                    } else {
                        //After successful sign in
                        if (loginUser.getRoles().size()==1){
                            if (loginUser.getRoles().get(0).getTitle().equals("admin")){
                                admin(loginUser);
                            }
                            else{
                                writer(loginUser);
                            }
                        }
                        else{
                            adminWriter(loginUser);
                        }
                    }
                }else
                {
                    System.out.printf("the user %s is logged in...getting sign out...\n", loginUser.getUsername());
                    AuthenticationService.getInstance().setLoginUser(null);
                    System.out.println("signed out successfully...");
                }
            }
        }
    }

    public static void adminWriter(User user){
        Scanner scanner = new Scanner(System.in);
        int command = 0;
        while(command!=13) {
            System.out.println("You signed in as an admin...choose an action");
            System.out.println("1.See all articles\n2.Publish an article\n3.Publish off an article\n4.Remove an article\n" +
                    "5.Create a category\n6.Create a tag\n7.Change the role of users\n8.Change password\n9.See your articles\n10.Enter new article\n" +
                    "11.Edit article\n12.Dashboard\n13.exit");
            command = scanner.nextInt();
            if (command==1){
                articleRepository.findAll().stream().
                        forEach(Article-> System.out.println(Article));
            }
            if (command==2){
                articleRepository.findAll().stream().
                        filter(Article->Article.getIsPublished().equals("no")||Article.getIsPublished().equals(null)).
                        forEach(Article-> System.out.println(Article));

                System.out.print("Which article do you want to publish?\nEnter id : ");
                Long id = scanner.nextLong();
                Article article = articleRepository.findById(id);
                article.setIsPublished("yes");
                articleRepository.update(article);
            }
            if (command==3){
                articleRepository.findAll().stream().
                        filter(Article->Article.getIsPublished().equals("yes")).
                        forEach(Article-> System.out.println(Article));

                System.out.print("Which article do you want to publish off?\nEnter the id : ");
                Long id = scanner.nextLong();
                Article article = articleRepository.findById(id);
                article.setIsPublished("no");
                articleRepository.update(article);
            }
            if (command==4){
                articleRepository.findAll().stream().
                        forEach(Article-> System.out.println(Article));

                System.out.print("Which article do you want to remove?\nEnter id : ");
                Long id = scanner.nextLong();

                articleRepository.removeById(id);
            }
            if (command==5){
                Category category = new Category();
                System.out.println("Enter title : ");
                String title = scanner.next();
                System.out.println("Enter description : ");
                String description = scanner.next();
                category.setId(null);
                category.setTitle(title);
                category.setDescription(description);
                categoryRepository.save(category);
            }
            if (command==6){
                Tag tag = new Tag();
                System.out.println("Enter title : ");
                String title = scanner.next();
                tag.setId(null);
                tag.setTitle(title);
                tagRepository.save(tag);
            }
            if (command==7){
                System.out.println("1.You can change the role of the writer to adminWriter...\nor\n2.You can change the role of the admin to adminWriter" +
                        "\nselect one...");
                int choice = scanner.nextInt();

                if (choice==1) {
                    userRepository.findAll().stream().
                            filter(User -> User.getRoles().size() == 1).
                            filter(User -> User.getRoles().get(0).getTitle().equals("writer")).
                            forEach(User -> System.out.println(User));

                    System.out.print("Which user do you want to change its role?\nenter id : ");
                    Long id = scanner.nextLong();
                    User selectUser = userRepository.findById(id);
                    Role role1 = new Role(null, "admin");
                    Role role2 = new Role(null, "writer");
                    List<Role> roles = new ArrayList<>();
                    roles.add(role1);
                    roles.add(role2);
                    selectUser.setRoles(roles);
                    userRepository.update(selectUser);
                }
                if (choice==2){
                    userRepository.findAll().stream().
                            filter(User -> User.getRoles().size() == 1).
                            filter(User -> User.getRoles().get(0).getTitle().equals("admin")).
                            forEach(User -> System.out.println(User));

                    System.out.print("Which user do you want to change its role?\nenter id : ");
                    Long id = scanner.nextLong();
                    User selectUser = userRepository.findById(id);
                    Role role1 = new Role(null, "admin");
                    Role role2 = new Role(null, "writer");
                    List<Role> roles = new ArrayList<>();
                    roles.add(role1);
                    roles.add(role2);
                    selectUser.setRoles(roles);
                    userRepository.update(selectUser);
                }

            }
            if (command==8){

                System.out.println("Enter your old password : ");
                String oldPassword = scanner.next();
                if (user.getPassword().equals(oldPassword)) {
                    System.out.println("Enter new password");
                    String newPassword = scanner.next();
                    user.setPassword(newPassword);
                    userRepository.update(user);
                    System.out.println("password changed successfully...");
                } else {
                    System.out.println("Wrong password...you can not change it");
                }

            }
            if (command==9){
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
            if (command==10){
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
                article.setIsPublished(null);

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
                List<Tag> tags = null;
                int tagChoice = 1;
                while (tagChoice != 0){
                    tagChoice = scanner.nextInt();
                    try {
                        tags.add(tagList.get(tagChoice - 1));
                        System.out.println("added");
                    }catch (Exception e){
                        System.out.println("done");
                    }
                }
                article.setTags(tags);

                articleRepository.save(article);
            }
            if (command==11){
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

                System.out.println("Enter the id of article you want to edit...");
                Long id = scanner.nextLong();
                Article article = articleRepository.findById(id);
                System.out.println("Enter new title : ");
                String title = scanner.next();
                article.setTitle(title);
                System.out.println("Enter new brief : ");
                String brief = scanner.next();
                article.setBrief(brief);
                System.out.println("Enter new content : ");
                String content = scanner.next();
                article.setContent(content);

                List<Category> categories = categoryRepository.findAll();
                System.out.println("id\t\t\ttitle");
                for (Category c : categories) {
                    System.out.printf("%d\t\t\t%s\n", c.getId(), c.getTitle());
                }
                System.out.println("Enter the id of category for adding to your article...");
                int categoryChoice = scanner.nextInt();
                article.setCategory(categories.get(categoryChoice - 1));
                article.setLastUpdateDate(new Date().toString());

                List<Tag> tagList = tagRepository.findAll();
                System.out.println("id\t\t\ttitle");
                for (Tag t : tagList){
                    System.out.printf("%d\t\t\t%s\n",t.getId(),t.getTitle());
                }
                System.out.println("Which tags do you want to add?...enter 0 to end entering id...");
                List<Tag> tags = null;
                int tagChoice = 1;
                while (tagChoice != 0){
                    tagChoice = scanner.nextInt();
                    try {
                        tags.add(tagList.get(tagChoice - 1));
                    }catch (Exception e){
                        System.out.println();
                    }
                }
                article.setTags(tags);

                articleRepository.update(article);
            }
            if (command==12){

                System.out.println("choose a number to see  details...\n1.See number of all of your articles\n" +
                        "2.See number of all of your published articles");
                int dashboardChoice = scanner.nextInt();
                if (dashboardChoice == 1) {
                    Long count = 0L;
                    try {
                        count = articleRepository.findAll().stream().
                                filter(Article -> Article.getUser().getId().equals(user.getId())).
                                count();
                    }catch (Exception e){}
                    System.out.printf("%s%d\n","Number of all of your articles : ",count);

                } else if (dashboardChoice == 2) {
                    Long count = 0L;
                    try {
                        count = articleRepository.findAll().stream().
                                filter(Article -> Article.getUser().getId().equals(user.getId())).
                                filter(Article -> Article.getIsPublished().equals("yes")).
                                count();
                    }catch (Exception e){}
                    System.out.printf("%s%d\n","Number of all of your published articles : ",count);
                }
            }
        }

    }
    public static void admin(User user){

        Scanner scanner = new Scanner(System.in);
        int command = 0;
        while(command!=9) {
            System.out.println("You signed in as an admin...choose an action");
            System.out.println("1.See all articles\n2.Publish an article\n3.Publish off an article\n4.Remove an article\n" +
                    "5.Create a category\n6.Create a tag\n7.Change the role of users\n8.Change password\n9.exit");
            command = scanner.nextInt();
            if (command==1){
                articleRepository.findAll().stream().
                                  forEach(Article-> System.out.println(Article));
            }
            if (command==2){
                articleRepository.findAll().stream().
                        filter(Article->Article.getIsPublished().equals("no")||Article.getIsPublished().equals(null)).
                        forEach(Article-> System.out.println(Article));

                System.out.print("Which article do you want to publish?\nEnter id : ");
                Long id = scanner.nextLong();
                Article article = articleRepository.findById(id);
                article.setIsPublished("yes");
                articleRepository.update(article);
            }
            if (command==3){
                articleRepository.findAll().stream().
                                filter(Article->Article.getIsPublished().equals("yes")).
                                forEach(Article-> System.out.println(Article));

                System.out.print("Which article do you want to publish off?\nEnter the id : ");
                Long id = scanner.nextLong();
                Article article = articleRepository.findById(id);
                article.setIsPublished("no");
                articleRepository.update(article);
            }
            if (command==4){
                articleRepository.findAll().stream().
                        forEach(Article-> System.out.println(Article));

                System.out.print("Which article do you want to remove?\nEnter id : ");
                Long id = scanner.nextLong();

                articleRepository.removeById(id);
            }
            if (command==5){
                Category category = new Category();
                System.out.println("Enter title : ");
                String title = scanner.next();
                System.out.println("Enter description : ");
                String description = scanner.next();
                category.setId(null);
                category.setTitle(title);
                category.setDescription(description);
                categoryRepository.save(category);
            }
            if (command==6){
                Tag tag = new Tag();
                System.out.println("Enter title : ");
                String title = scanner.next();
                tag.setId(null);
                tag.setTitle(title);
                tagRepository.save(tag);
            }
            if (command==7){
                System.out.println("You can only change the role of the writers to adminWriters...");

                userRepository.findAll().stream().
                        filter(User->User.getRoles().size() == 1).
                        filter(User->User.getRoles().get(0).getTitle().equals("writer")).
                        forEach(User-> System.out.println(User));

                System.out.print("Which user do you want to change its role?\nenter id : ");
                Long id = scanner.nextLong();
                User selectUser = userRepository.findById(id);
                    Role role1 = new Role(null,"admin");
                    Role role2 = new Role(null,"writer");
                    List<Role> roles = new ArrayList<>();
                    roles.add(role1);
                    roles.add(role2);
                    selectUser.setRoles(roles);
                    userRepository.update(selectUser);

            }
            if (command==8){

                System.out.println("Enter your old password : ");
                String oldPassword = scanner.next();
                if (user.getPassword().equals(oldPassword)) {
                    System.out.println("Enter new password");
                    String newPassword = scanner.next();
                    user.setPassword(newPassword);
                    userRepository.update(user);
                    System.out.println("password changed successfully...");
                } else {
                    System.out.println("Wrong password...you can not change it");
                }

            }
        }

    }
    public static void writer(User user){
        Scanner scanner = new Scanner(System.in);
        int command = 0;
        while(command!=6){
            System.out.println("You signed in as a writer...choose an action");
            System.out.println("1.See your articles\n2.Create new article\n3.Edit article\n4.Change password\n5.dashboard" +
                    "\n6.exit");
            command = scanner.nextInt();
            if (command == 1){
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


            if (command == 2){

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
                article.setIsPublished(null);

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
                List<Tag> tags = null;
                int tagChoice = 1;
                while (tagChoice != 0){
                    tagChoice = scanner.nextInt();
                    try {
                        tags.add(tagList.get(tagChoice - 1));
                        System.out.println("added");
                    }catch (Exception e){
                        System.out.println("done");
                    }
                }
                article.setTags(tags);

                articleRepository.save(article);
            }


            if (command == 3){

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

                    System.out.println("Enter the id of article you want to edit...");
                    Long id = scanner.nextLong();
                    Article article = articleRepository.findById(id);
                    System.out.println("Enter new title : ");
                    String title = scanner.next();
                    article.setTitle(title);
                    System.out.println("Enter new brief : ");
                    String brief = scanner.next();
                    article.setBrief(brief);
                    System.out.println("Enter new content : ");
                    String content = scanner.next();
                    article.setContent(content);

                    List<Category> categories = categoryRepository.findAll();
                    System.out.println("id\t\t\ttitle");
                    for (Category c : categories) {
                        System.out.printf("%d\t\t\t%s\n", c.getId(), c.getTitle());
                    }
                    System.out.println("Enter the id of category for adding to your article...");
                    int categoryChoice = scanner.nextInt();
                    article.setCategory(categories.get(categoryChoice - 1));
                    article.setLastUpdateDate(new Date().toString());

                    List<Tag> tagList = tagRepository.findAll();
                    System.out.println("id\t\t\ttitle");
                    for (Tag t : tagList){
                        System.out.printf("%d\t\t\t%s\n",t.getId(),t.getTitle());
                    }
                    System.out.println("Which tags do you want to add?...enter 0 to end entering id...");
                    List<Tag> tags = null;
                    int tagChoice = 1;
                    while (tagChoice != 0){
                        tagChoice = scanner.nextInt();
                        try {
                            tags.add(tagList.get(tagChoice - 1));
                        }catch (Exception e){
                            System.out.println();
                        }
                    }
                    article.setTags(tags);

                    articleRepository.update(article);
            }


            if (command == 4){

                System.out.println("Enter your old password : ");
                String oldPassword = scanner.next();
                if (user.getPassword().equals(oldPassword)) {
                    System.out.println("Enter new password");
                    String newPassword = scanner.next();
                    user.setPassword(newPassword);
                    userRepository.update(user);
                    System.out.println("password changed successfully...");
                } else {
                    System.out.println("Wrong password...you can not change it");
                }

            }


            if (command == 5){

                System.out.println("choose a number to see  details...\n1.See number of all of your articles\n" +
                        "2.See number of all of your published articles");
                int dashboardChoice = scanner.nextInt();
                if (dashboardChoice == 1) {
                    Long count = 0L;
                    try {
                        count = articleRepository.findAll().stream().
                                filter(Article -> Article.getUser().getId().equals(user.getId())).
                                count();
                    }catch (Exception e){}
                    System.out.printf("%s%d\n","Number of all of your articles : ",count);

                } else if (dashboardChoice == 2) {
                    Long count = 0L;
                    try {
                        count = articleRepository.findAll().stream().
                                filter(Article -> Article.getUser().getId().equals(user.getId())).
                                filter(Article -> Article.getIsPublished().equals("yes")).
                                count();
                    }catch (Exception e){}
                    System.out.printf("%s%d\n","Number of all of your published articles : ",count);
                }
            }
        }
    }
}
