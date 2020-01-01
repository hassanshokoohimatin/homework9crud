package ir.maktab.repositories;

import ir.maktab.config.HibernateUtil;
import ir.maktab.config.repositories.CrudRepository;
import ir.maktab.entities.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserRepository extends CrudRepository<User, Long> {

    private static UserRepository userRepository;
    private UserRepository(){}
    public static UserRepository getInstance(){
        if (userRepository == null)
            userRepository = new UserRepository();
        return userRepository;
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
    private Session getSession() {
        return HibernateUtil.getSession();
    }
}
