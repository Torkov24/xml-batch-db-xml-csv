package org.example.springbatchdbtoxml.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.example.springbatchdbtoxml.entity.Book;
import org.example.springbatchdbtoxml.entity.BookDto;
import org.example.springbatchdbtoxml.processor.CsvProcessor;
import org.example.springbatchdbtoxml.processor.Processor;
import org.example.springbatchdbtoxml.xmlentity.XmlBook;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;
import org.example.springbatchdbtoxml.repository.BookRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@AllArgsConstructor
public class BatchConfig {

    private EntityManagerFactory entityManagerFactory;

    private final BookRepository bookRepository;

    /*@Bean
    public JpaCursorItemReader<Book> readFromDbManyToMany(){
        var reader = new JpaCursorItemReader<Book>();
        reader.setQueryString("select b from Book b");
        reader.setEntityManagerFactory(entityManagerFactory);
        return reader;
    }*/

    public RepositoryItemReader<Book> readFromDbManyToMany(){
        var reader = new RepositoryItemReader<Book>();
        reader.setRepository(bookRepository);
        reader.setMethodName("findAll");

        Map<String, Sort.Direction> sort = new HashMap<>();
        sort.put("title", Sort.Direction.DESC);
        reader.setSort(sort);
        return reader;
    }

    @Bean
    public StaxEventItemReader<XmlBook> readFromXml(){
        var reader = new StaxEventItemReader<XmlBook>();
        reader.setFragmentRootElementName("book");
        reader.setResource(new FileSystemResource("src/main/resources/output/books-sample.xml"));
        var marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(XmlBook.class);
        reader.setUnmarshaller(marshaller);

        return reader;
    }

    @Bean
    public FlatFileItemWriter<BookDto> writeToCsv(){
        var writer = new FlatFileItemWriter<BookDto>();
        writer.setResource(new FileSystemResource("src/main/resources/output/books-sample.csv"));
        writer.setLineAggregator(item -> {
                String author = item.getAuthors().stream().map(author1 -> author1.getFirstname() + ";" + author1.getLastname())
                        .collect(Collectors.joining(","));
                return item.getId() + "," + item.getTitle() + "," + author;
        });

        return writer;

    }

    @Bean(name = "marshaller7")
    public Marshaller marshaller7(){
        var marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(XmlBook.class);
        return marshaller;
    }


    @Bean(destroyMethod = "")
    public StaxEventItemWriter<XmlBook> writeToXmlManyToMany(Marshaller marshaller){
        var writer = new StaxEventItemWriter<XmlBook>();
        writer.setMarshaller(marshaller);
        writer.setRootTagName("Books");
        writer.setResource(new FileSystemResource("src/main/resources/output/books-sample.xml"));
        return writer;
    }

    @Bean
    public Processor bookToXmlProcessor(){
        return new Processor();
    }

    @Bean
    public CsvProcessor xmlToCsvProcessor(){
        return new CsvProcessor();
    }

    @Bean
    public Step xmlToCsvStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("xmlToCsv", jobRepository).<XmlBook, BookDto>chunk(10, transactionManager)
                .reader(readFromXml())
                .processor(xmlToCsvProcessor())
                .writer(writeToCsv())
                .build();
    }

    @Bean
    public Step xmlNewStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("marshaller7") Marshaller marshaller7){
        return new StepBuilder("dbToXmlM:N", jobRepository).<Book, XmlBook>chunk(10, transactionManager)
                .reader(readFromDbManyToMany())
                .processor(bookToXmlProcessor())
                .writer(writeToXmlManyToMany(marshaller7))
                .build();
    }

    @Bean("manyToManyJob")
    public Job job (@Qualifier("xmlNewStep") Step step, @Qualifier("xmlToCsvStep") Step step2, JobRepository jobRepository){
        return new JobBuilder("DbtoXml", jobRepository)
                .start(step)
                .next(step2)
                .build();
    }
}
