package ir.maktab.repositories;

import ir.maktab.config.repositories.CrudRepository;
import ir.maktab.entities.Tag;

public class TagRepository extends CrudRepository<Tag , Long> {

    private static TagRepository tagRepository;
    private TagRepository(){}

    public static TagRepository getInstance(){
        if (tagRepository == null)
            tagRepository = new TagRepository();
        return tagRepository;
    }

    @Override
    protected Class<Tag> getEntityClass() {
        return Tag.class;
    }
}
