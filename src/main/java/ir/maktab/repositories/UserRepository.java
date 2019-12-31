package ir.maktab.repositories;

import ir.maktab.config.repositories.CrudRepository;
import ir.maktab.entities.User;

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
}
