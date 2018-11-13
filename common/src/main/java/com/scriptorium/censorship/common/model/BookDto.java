package com.scriptorium.censorship.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by taras on 2018-11-12.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private String tradeCompany;
    private String publisher;
    private String ruTitle;
    private String isbn;
    private int quantity;
    private String author;
    private String uaTitle;
    private int yearOfPublish;
}
