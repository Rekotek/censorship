package com.scriptorium.censorship.frontend.repository;

import com.scriptorium.censorship.frontend.entity.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by taras on 2018-11-13.
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @Query("select b from Book b where " +
            "(( b.ruTitleUpper like ?1) or (b.authorUpper like ?1)) " +
            "order by b.ruTitle, b.author, b.yearOfPublish desc")
    List<Book> searchByTitle(String title);

    @Query("select b from Book b where ((b.publisherUpper like ?2) and " +
            "(( b.ruTitleUpper like ?1) or (b.authorUpper like ?1))) " +
            "order by b.ruTitle, b.author, b.yearOfPublish desc")
    List<Book> searchByTitleAndPublisher(String title, String publisher);

    List<Book> findByIsbnShortLikeOrderByRuTitleAscRuTitleAscYearOfPublishDesc(String isbnShort);

    @Query("select b from Book b where ((b.publisherUpper like ?2) and " +
            "(b.isbnShort like ?1)) " +
            "order by b.ruTitle, b.author, b.yearOfPublish desc")
    List<Book> findByIsbnShortLikeAndPublisherLike(String isbnShort, String publisher);
}
