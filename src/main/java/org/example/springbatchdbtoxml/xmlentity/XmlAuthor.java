package org.example.springbatchdbtoxml.xmlentity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@ToString
@NoArgsConstructor
public class XmlAuthor {

    @XmlElement(name = "id")
    private int id;

    @XmlElement(name = "firstname")
    private String firstname;

    @XmlElement(name = "lastname")
    private String lastname;
}