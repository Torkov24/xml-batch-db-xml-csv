package org.example.springbatchdbtoxml.entity;

import lombok.Data;

import java.util.List;

@Data
public class BookDto {

    private int id;

    private String title;

    private List<AuthorsDto> authors;
}
