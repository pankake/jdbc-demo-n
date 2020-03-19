package com.kaleyra.db.orm;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public  class Book {

    @Id
    @GeneratedValue
    public Long id;

    public String title;

    public String isbn;

    @ManyToOne
    public Person author;

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

//    @ManyToMany(cascade = { CascadeType.ALL })
//    @JoinTable
//    public Set<Person> projects = new HashSet<>();

}
