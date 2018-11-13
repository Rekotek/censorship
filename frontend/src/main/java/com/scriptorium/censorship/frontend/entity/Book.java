package com.scriptorium.censorship.frontend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by taras on 2018-11-13.
 */

@Entity
@Data
@NoArgsConstructor
public class Book {
    public static final int MAX_VARCHAR = 1024;
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = MAX_VARCHAR)
    private String tradeCompany;

    @Column(length = MAX_VARCHAR)
    private String publisher;

    @Column(length = MAX_VARCHAR)
    private String ruTitle;

    private String isbn;

    private int quantity;

    @Column(length = MAX_VARCHAR)
    private String author;

    @Column(length = MAX_VARCHAR)
    private String uaTitle;

    private int yearOfPublish;
}
