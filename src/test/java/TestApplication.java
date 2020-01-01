import ir.maktab.entities.*;
import ir.maktab.repositories.*;

import java.util.ArrayList;
import java.util.List;

public class TestApplication {

    static UserRepository userRepository = UserRepository.getInstance();
    static ArticleRepository articleRepository = ArticleRepository.getInstance();
    static CategoryRepository categoryRepository = CategoryRepository.getInstance();
    static TagRepository tagRepository = TagRepository.getInstance();
    static RoleRepository roleRepository = RoleRepository.getInstance();
    public static void main(String[] args) {

//        List<Role> roleList1 = new ArrayList<>();//contains 2 roles : admin and writer
//        List<Role> roleList2 = new ArrayList<>();//contains only one role // admin or writer-->>admin
//        List<Role> roleList3 = new ArrayList<>();//contains only one role // admin or writer-->>writer
//        List<Tag> tagList1 = new ArrayList<>();
//        List<Tag> tagList2 = new ArrayList<>();
//        List<Tag> tagList3 = new ArrayList<>();
//
//        Role role1 = new Role(null , "admin");
//        Role role2 = new Role(null , "writer");
//
//        Tag tag1 = new Tag(null,"java");
//        Tag tag2 = new Tag(null,"c##");
//        Tag tag3 = new Tag(null,"hibernate");
//
//        roleList1.add(role1);
//        roleList1.add(role2);
//        roleList2.add(role1);//admin
//        roleList3.add(role2);//writer
//        tagList1.add(tag1);
//        tagList1.add(tag2);
//        tagList1.add(tag3);
//        tagList2.add(tag1);
//        tagList3.add(tag1);
//        tagList3.add(tag3);
//
//        User user1 = new User(null,"hassan_shokoohi","1111111111","1365","11111",null,roleList1);
//        User user2 = new User(null,"amin_ghasemi","2222222222","1366","22222",null,roleList2);
//        User user3 = new User(null,"bahram_mirzayi","3333333333","1367","33333",null,roleList3);
//
//        Category category1 = new Category(1L,"programming");
//        Category category2 = new Category(2L,"physics");
//
//        Article article1 = new Article(null,"ttt","bbb","ccc","2019",
//                "2019","desember","yes",user1,category1,tagList1);
//        Article article2 = new Article(null,"title2","brief2","content2","2018",
//                "2018-01-01","2018","yes",user2,category2,tagList3);
//
//        roleRepository.save(role1);
//        roleRepository.save(role2);
//
//        tagRepository.save(tag1);
//        tagRepository.save(tag2);
//        tagRepository.save(tag3);
//
//        userRepository.save(user1);
//        userRepository.save(user2);
//        userRepository.save(user3);
//
//        categoryRepository.save(category1);
//        categoryRepository.save(category2);
//
//        articleRepository.save(article1);
//        articleRepository.save(article2);
//
//        category1.setDescription("about object-oriented languages");
//        category2.setDescription("modern theories");
//
//        categoryRepository.update(category1);
//        categoryRepository.update(category2);
    }
}
