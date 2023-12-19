package org.example.springbatchdbtoxml.processor;

import lombok.NonNull;
import org.example.springbatchdbtoxml.entity.BookDto;
import org.example.springbatchdbtoxml.mapper.XmlToCssMapper;
import org.example.springbatchdbtoxml.xmlentity.XmlBook;
import org.springframework.batch.item.ItemProcessor;

public class CsvProcessor implements ItemProcessor<XmlBook, BookDto> {
    @Override
    public BookDto process(@NonNull XmlBook item) {
        if (item.getTitle().equals("title2")) {
            return XmlToCssMapper.map(item);
        } else {
            return null;
        }

    }
}
