package org.example.springbatchdbtoxml.xmlentity;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@XmlRootElement(name="book")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@ToString
@NoArgsConstructor
public class XmlBook {

    @XmlElement(name = "id")
    private int id;

    @XmlElement(name = "title")
    private String title;

    @XmlElementWrapper(name = "authors")
    @XmlElement(name = "author")
    private List<XmlAuthor> authors;

}
