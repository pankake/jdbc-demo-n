package com.kaleyra.db.orm;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Person {

    @Id
    @GeneratedValue
    public Long id;

    public String name;

    @OneToMany(mappedBy = "author")
    public List<Book> books = new ArrayList< >();


//    @ManyToMany(mappedBy = "projects")
//    public Set<Book> employees = new HashSet<>();

}
