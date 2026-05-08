package com.testing.bookspringapp.bookRating;

import com.testing.bookspringapp.PageResponse;
import com.testing.bookspringapp.book.Book;
import com.testing.bookspringapp.book.BookRepository;
import com.testing.bookspringapp.user.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class BookRatingService {
    private final BookRatingRepository bookRatingRepository;
    private final BookRatingDTOMapper bookRatingDTOMapper;
    private final BookRepository bookRepository;

    private void updateBookStats(Integer bookId) {
        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Double newAvg = bookRatingRepository.getAverageRatingByBookId(bookId);
        Integer newCount = bookRatingRepository.getCountByBookId(bookId);

        book.setAverageRate(newAvg);
        book.setRatingCount(newCount);

        bookRepository.save(book);
    }

    public BookRatingResponse getRate(Integer id) {
        return bookRatingRepository
                .findById(id)
                .map(bookRatingDTOMapper)
                .orElseThrow(() -> new RuntimeException("Can't find this rate"));
    }

    public void addRate(BookRatingRequest request, Authentication auth) {
        User user = (User) auth.getPrincipal();
        if (bookRatingRepository.existsByUserIdAndBookId(user.getId(), request.book().getId())) {
            throw new RuntimeException("You have already rated this book!");
        }

        BookRating rating = bookRatingDTOMapper.toEntity(request);
        rating.setUser(user);
        bookRatingRepository.save(rating);
        updateBookStats(request.book().getId());
    }

    public PageResponse<BookRatingResponse> getRateByUser(Authentication auth, Pageable pageable) {
        User user = (User) auth.getPrincipal();
        Page<BookRatingResponse> page = bookRatingRepository
                .findByUserId(user.getId(), pageable)
                .map(bookRatingDTOMapper);

        return toPageResponse(page);
    }

    public PageResponse<BookRatingResponse> getRateByBook(Integer bookId, Pageable pageable) {
        Page<BookRatingResponse> page = bookRatingRepository
                .findByBookId(bookId, pageable)
                .map(bookRatingDTOMapper);

        return toPageResponse(page);
    }

    public void deleteRate(Integer id, Authentication auth) {

        User user = (User) auth.getPrincipal();
        BookRating rating = bookRatingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rate not found"));
        Integer bookId = rating.getBook().getId();

        if(!user.canManageRating(rating)){
            throw new RuntimeException("You are not authorized to delete this rate");
        }
        bookRatingRepository.delete(rating);
        updateBookStats(bookId);
    }


    public void modifyRate(Integer id, BookRatingRequest request, Authentication auth) {
        User user = (User) auth.getPrincipal();
        BookRating bookRating = bookRatingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rate doesn't exist"));

        if(!user.canManageRating(bookRating)){
            throw new RuntimeException("You can only modify your own ratings");
        }

        bookRating.setRate(request.rate());
        bookRatingRepository.save(bookRating);
        updateBookStats(bookRating.getBook().getId());
    }

    private PageResponse<BookRatingResponse> toPageResponse(Page<BookRatingResponse> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}