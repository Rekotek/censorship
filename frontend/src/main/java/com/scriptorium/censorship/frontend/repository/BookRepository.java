package com.scriptorium.censorship.frontend.repository;

import com.scriptorium.censorship.frontend.entity.Book;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by taras on 2018-11-13.
 */
@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
}
