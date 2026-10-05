package com.example.books.service;

import com.example.books.entity.Book;
import com.example.books.exception.ResourceNotFoundException;
import com.example.books.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Book findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));
    }

    @Transactional
    public Book create(Book book) {
        book.setId(null); // el id lo genera la base de datos
        return repository.save(book);
    }

    @Transactional
    public Book update(Long id, Book data) {
        Book existing = findById(id);
        existing.setTitle(data.getTitle());
        existing.setAuthor(data.getAuthor());
        existing.setPublicationYear(data.getPublicationYear());
        existing.setPrice(data.getPrice());
        return repository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Book existing = findById(id);
        repository.delete(existing);
    }
}
