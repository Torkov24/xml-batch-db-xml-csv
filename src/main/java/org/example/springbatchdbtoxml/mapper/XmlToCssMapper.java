package org.example.springbatchdbtoxml.mapper;

import org.example.springbatchdbtoxml.entity.AuthorsDto;
import org.example.springbatchdbtoxml.entity.BookDto;
import org.example.springbatchdbtoxml.xmlentity.XmlBook;

import java.util.stream.Collectors;

public class XmlToCssMapper {

    public static BookDto map(XmlBook xmlBook){
        var xmlBookDto = new BookDto();
        xmlBookDto.setId(xmlBook.getId());
        xmlBookDto.setTitle(xmlBook.getTitle());
        xmlBookDto.setAuthors(xmlBook.getAuthors().stream().map(
                author -> {
                var xmlAuthorDto = new AuthorsDto();
                xmlAuthorDto.setId(author.getId());
                xmlAuthorDto.setFirstname(author.getFirstname());
                xmlAuthorDto.setLastname(author.getLastname());
                return xmlAuthorDto;
            }
        ).collect(Collectors.toList()
        ));
        return xmlBookDto;
    }
}
