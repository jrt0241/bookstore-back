package com.project.bookstore.repository;

import com.project.bookstore.model.Book;
import com.project.bookstore.model.Language;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class BookRepositoryTest {

    @Inject
    private BookRepository bookRepository;

    @org.junit.Test
    public void create() throws Exception {
        //Test Counting Books
        assertEquals(Long.valueOf(0), bookRepository.countAll());
        assertEquals(0, bookRepository.findAll().size());
        //create a book
        Book book = new Book("isnb","a title",12F,123,Language.ENGLISH,new Date(),"http://image","description");
        book=bookRepository.create(book);
        Long bookId= book.getId();

        // Check created book
        assertNotNull(bookId);

        //Find Created book
        Book bookFound= bookRepository.find(bookId);

        //check the found book is correct
        assertEquals("a title",bookFound.getTitle());
        assertEquals(Long.valueOf(1), bookRepository.countAll());
        assertEquals(1, bookRepository.findAll().size());

        //Deleting the Book
        bookRepository.delete(bookId);
        assertEquals(Long.valueOf(0), bookRepository.countAll());
        assertEquals(0, bookRepository.findAll().size());

    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(BookRepository.class)
                .addClass(Book.class)
                .addClass(Language.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml");
    }

}
