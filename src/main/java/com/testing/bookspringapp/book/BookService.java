package com.testing.bookspringapp.book;

import com.testing.bookspringapp.PageResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookDTOMapper bookDTOMapper;

    public BookResponse getBook(Integer id){
        return bookRepository.findById(id).map(bookDTOMapper).orElseThrow(() -> new RuntimeException("Can't find the book with id :"+ id));
    }

    public void addUser(@Valid BookRequest bookRequest) {
        bookRepository.save(bookDTOMapper.toEntity(bookRequest));
    }

    public void updateBook(Integer id, @Valid BookRequest bookRequest) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Can't find the book with id of : "+id));
        book.setName(bookRequest.name());
        book.setYear(bookRequest.year());
        book.setGenre(bookRequest.genre());
        book.setAuthor(bookRequest.author());
        book.setStock(bookRequest.stock());
        book.setPrice(bookRequest.price());
        bookRepository.save(book);
    }

    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    public PageResponse<BookResponse> getAllBooks(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<BookResponse> bookResponses = bookRepository.findAll(pageable).map(bookDTOMapper);
        return new PageResponse<BookResponse>(
            bookResponses.getContent(),
            bookResponses.getNumber(),
            bookResponses.getSize(),
            bookResponses.getTotalElements(),
            bookResponses.getTotalPages(),
            bookResponses.isLast()
        );
    }
}
