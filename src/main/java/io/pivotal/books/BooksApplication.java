package io.pivotal.books;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
import java.util.stream.Stream;

@Log
@SpringBootApplication
public class BooksApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooksApplication.class, args);
    }

    @Bean
    CommandLineRunner LoadData(BookRepository repository) {
        return commandLineRunner -> {
            Stream.of("To Kill a Mocking Bird", "The Dome", "Harry Potter").forEach(book -> {
                repository.save(new Book(null, book));
            });
            repository.findAll().forEach(book -> log.info(book.getTitle()));
        };
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
class Book {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

}

@RepositoryRestResource
interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByTitle(@Param("text") String text);
}

