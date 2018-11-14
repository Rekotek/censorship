package com.scriptorium.censorship.frontend.repository;

import com.scriptorium.censorship.frontend.entity.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by taras on 2018-11-13.
 */
@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    @Query("select b from Book b where upper(b.publisher) like '%СОФИЯ%' or " +
            "upper(b.publisher) like '%СОФІЯ%' order by b.ruTitle, b.author")
    List<Book> getInitialBookList();
}
