package com.example.bookstoreApp.repository;

import com.example.bookstoreApp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> { //Repo intefejs za Book model

    Book findByTitle(String title);

    @Query(value = "SELECT b FROM Book b WHERE LOWER(CONCAT(b.title, ' ', b.author, ' ', b.publisher, ' ', b.price)) LIKE %?1%", nativeQuery = false)
    public List<Book> search(String keyword);
}
