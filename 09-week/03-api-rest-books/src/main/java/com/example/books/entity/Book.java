package com.example.books.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long id;

    @NotBlank(message = "title is required")
    @Size(max = 150, message = "title must have at most 150 characters")
    @Column(nullable = false, length = 150)
    @Schema(example = "Cien años de soledad")
    private String title;

    @NotBlank(message = "author is required")
    @Size(max = 100, message = "author must have at most 100 characters")
    @Column(nullable = false, length = 100)
    @Schema(example = "Gabriel García Márquez")
    private String author;

    @NotNull(message = "publicationYear is required")
    @Min(value = 1450, message = "publicationYear must be >= 1450")
    @Max(value = 2100, message = "publicationYear must be <= 2100")
    @Schema(example = "1967")
    private Integer publicationYear;

    @NotNull(message = "price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "price must be greater than 0")
    @Schema(example = "45000.0")
    private Double price;

    public Book() {
    }

    public Book(String title, String author, Integer publicationYear, Double price) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.price = price;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
