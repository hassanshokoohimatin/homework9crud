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
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String username;

    @Column
    private String nationalCode;

    @Column
    private String birthday;

    @Column
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Article> articleList = new ArrayList<>();


    @ManyToMany
    @JoinTable(name = "user_role" , joinColumns = {@JoinColumn(name = "user_id")} , inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles = new ArrayList<>();


    @Override
    public String toString() {
        return "User(" + id + ')' +
                "( username= " + username +
                ", birthday= " + birthday +
                ", roles= " + roles.get(0).getTitle() +
                ')';
    }
}
