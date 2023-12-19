package org.example.springbatchdbtoxml.processor;

import lombok.NonNull;
import org.example.springbatchdbtoxml.entity.Book;
import org.example.springbatchdbtoxml.mapper.DbToXmlMapper;
import org.example.springbatchdbtoxml.xmlentity.XmlBook;
import org.springframework.batch.item.ItemProcessor;

public class Processor implements ItemProcessor<Book, XmlBook> {
    @Override
    public XmlBook process(@NonNull Book item) {
        return DbToXmlMapper.mapToXml(item);
    }
}
