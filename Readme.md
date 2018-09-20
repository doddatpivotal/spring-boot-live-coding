# Live Coding

1. start.spring.io, choose web, actuator, jpa, rest repositories, h2, lombok

2. Create entity
```java
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
```

3. Create repository
```java
@RepositoryRestResource
interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByTitle(@Param("text") String text);
}
```

3. Command Line Runner
```java
@Bean
CommandLineRunner LoadData(BookRepository repository) {
    return commandLineRunner -> {
        Stream.of("To Kill a Mocking Bird", "The Dome", "Harry Potter").forEach(book -> {
            repository.save(new Book(null, book));
        });
        repository.findAll().forEach(book -> log.info(book.getTitle()));
    };
}
```

4. Setup Actuator Properties
```properties
management.endpoints.web.exposure.include=*
spring.security.enabled=false
  ```

5. Run and test it out
```bash
http :8080/books
http :8080/book/1
http POST :8080 title=Beowolf
http :8080/books/search/findBooksByTitle?text=Beowolf
http :8080/actuator
http :8080/actuator/health
http :8080/actuator/env
```