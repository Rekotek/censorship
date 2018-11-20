package com.scriptorium.censorship.parser.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookInfo {
    private String tradeCompany;
    private String publisher;
    private String ruTitle;
    private String isbn;
    private String quantity;
    private String author;
    private String uaTitle;
    private String yearOfPublish;
    private String documentNum;
    private String documentStartDate;
}
