package com.scriptorium.censorship.frontend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by taras on 2018-11-13.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book {
    public static final int MAX_VARCHAR = 4096;
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = MAX_VARCHAR)
    private String tradeCompany;

    @Column(length = MAX_VARCHAR)
    private String publisher;

    @Column(length = MAX_VARCHAR)
    private String publisherUpper;

    @Column(length = MAX_VARCHAR)
    private String ruTitle;

    @Column(length = MAX_VARCHAR)
    private String ruTitleUpper;

    private String isbn;

    private String isbnShort;

    private int quantity;

    @Column(length = MAX_VARCHAR)
    private String author;

    @Column(length = MAX_VARCHAR)
    private String authorUpper;

    @Column(length = MAX_VARCHAR)
    private String uaTitle;

    private int yearOfPublish;

    @Column(length = MAX_VARCHAR)
    private String documentNum;

    private Date documentDate;
}
