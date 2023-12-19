package org.example.springbatchdbtoxml.mapper;

import org.example.springbatchdbtoxml.entity.Book;
import org.example.springbatchdbtoxml.xmlentity.XmlAuthor;
import org.example.springbatchdbtoxml.xmlentity.XmlBook;

import java.util.stream.Collectors;

public class DbToXmlMapper {

    public static XmlBook mapToXml(Book book) {
        XmlBook xmlBook = new XmlBook();
        xmlBook.setId(book.getId());
        xmlBook.setTitle(book.getTitle());
        xmlBook.setAuthors(book.getAuthors().stream().map(author -> {
            XmlAuthor xmlAuthor = new XmlAuthor();
            xmlAuthor.setId(author.getId());
            xmlAuthor.setFirstname(author.getFirstname());
            xmlAuthor.setLastname(author.getLastname());
            return xmlAuthor;
        }).collect(Collectors.toList()));
        return xmlBook;
    }
}
