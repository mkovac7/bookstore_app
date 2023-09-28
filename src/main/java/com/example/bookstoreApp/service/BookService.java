package com.example.bookstoreApp.service;

import com.example.bookstoreApp.model.Book;
import com.example.bookstoreApp.model.BookDto;
import com.example.bookstoreApp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService { //Service klasa za Book model

    private final BookRepository bookRepository;

    @Autowired
    BookService(BookRepository repository) {
        this.bookRepository = repository;
    }

    public List<Book> getAll(String keyword) { //Metoda za vracanje svih postojecih Book objekata
        if (keyword != null) {
            return bookRepository.search(keyword.toLowerCase());
        }
        return bookRepository.findAll();
    }

    public Book findById(Long id) { //Metoda za vracanje Book po ID-u
        return bookRepository.getById(id);
    }

    public Book findByTitle(String title) { //Metoda za vracanje Book po naslovu
        return bookRepository.findByTitle(title);
    }

    public void saveBook(BookDto bookDto) { //Metoda za snimanje novih Book objekata
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPublisher(bookDto.getPublisher());
        book.setPrice(bookDto.getPrice());
        bookRepository.save(book);
        System.out.println("Saved new book: " + book.toString());
    }

    public void editBook(Long id, BookDto bookDto) { //Metoda za izmenu postojecih Book objekata
        Book book = bookRepository.getById(id);
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPublisher(bookDto.getPublisher());
        book.setPrice(bookDto.getPrice());
        bookRepository.save(book);
        System.out.println("Edited book: " + book.toString());
    }

    public void deleteBook(Long id) { //Metoda za brisanje Book objekata
        bookRepository.deleteById(id);
    }

}
