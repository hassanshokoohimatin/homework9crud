package ir.maktab.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String title;

    @Column
    private String brief;

    @Column
    private String content;

    @Column
    private String createDate;

    @Column
    private String lastUpdateDate;

    @Column
    private String publishDate;

    @Column
    private String isPublished;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;


    @ManyToMany
    @JoinTable(name = "article_tag" , joinColumns = {@JoinColumn(name = "article_id")} , inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tag> tags = new ArrayList<>();

    @Override
    public String toString() {
        System.out.print("Article{"+ id);
        for(Tag t : tags){
            System.out.printf("%s%s\t","tag : ",t.getTitle());
        }
        return
                ", title : " + title +
                        ", content : " + content +
                        ", createDate : " + createDate +
                        ", publishDate : " + publishDate +
                        ", isPublished : " + isPublished +
                        ", user=" + user.getUsername() +
                        ", category=" + category.getTitle() +
                        '}';
    }
}
