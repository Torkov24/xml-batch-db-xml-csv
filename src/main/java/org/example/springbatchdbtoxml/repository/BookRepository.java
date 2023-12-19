package org.example.springbatchdbtoxml.repository;

import org.example.springbatchdbtoxml.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{
}
