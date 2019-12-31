package ir.maktab.repositories;

import ir.maktab.config.repositories.CrudRepository;
import ir.maktab.entities.Role;

public class RoleRepository extends CrudRepository<Role , Long> {

    private static RoleRepository roleRepository;
    private RoleRepository(){}
    public static RoleRepository getInstance(){
        if (roleRepository == null)
            roleRepository = new RoleRepository();
        return roleRepository;
    }

    @Override
    protected Class<Role> getEntityClass() {
        return Role.class;
    }
}
