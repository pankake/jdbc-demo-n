package com.kaleyra.db;

import com.kaleyra.db.orm.Book;
import com.kaleyra.db.orm.Person;
import com.kaleyra.db.orm.utility.HibernateUtils;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.transaction.Transactional;
import java.io.Serializable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrmTest {

    private Session currentSession;

    @Before
    public void test() {
        currentSession = HibernateUtils.getSessionFactory().getCurrentSession();
        currentSession.beginTransaction();
    }

    @After
    public void teardown() {
        if(currentSession.getTransaction().isActive())
            currentSession.getTransaction().commit();
    }

    @Test
    @Transactional
    public void shouldPersistABook() {

        Book book = new Book();
        book.title = "IT";
        book.isbn = "32109213028493282";

        currentSession.persist(book);

    }

    @Test
    @Transactional
    public void shouldPersistABookWithAuthor() throws InterruptedException {
        Book book = new Book();
        book.title = "On the road";
        book.isbn = "12323423432";

        Person person = new Person();
        person.name = "Kerouac";
        book.setAuthor(person);

        currentSession.persist(person);

        Serializable id = currentSession.save(book);


        Book bookInsideDb = currentSession.find(Book.class, id);

        assertNotNull(bookInsideDb);
        assertNotNull(bookInsideDb.getAuthor());
        assertEquals(person.name, bookInsideDb.getAuthor().name);


    }
}
