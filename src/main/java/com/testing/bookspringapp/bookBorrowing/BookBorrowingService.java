package com.testing.bookspringapp.bookBorrowing;


import com.testing.bookspringapp.PageResponse;
import com.testing.bookspringapp.book.Book;
import com.testing.bookspringapp.book.BookRepository;
import com.testing.bookspringapp.payment.Payment;
import com.testing.bookspringapp.payment.PaymentRepository;
import com.testing.bookspringapp.user.User;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class BookBorrowingService {
    private final BookBorrowingRepository bookBorrowingRepository;
    private final BookBorrowingDTOMapper bookBorrowingDTOMapper;
    private final BookRepository bookRepository;
    private final PaymentRepository paymentRepository;
    @Transactional
    public void borrowingBook(BookBorrowingRequest request, Authentication auth) {
        User user = (User) auth.getPrincipal();
        Book book = bookRepository
                .findById(request.book().getId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        if (book.getStock() < request.quantite()) {
            throw new RuntimeException("Insufficient stock. Only " + book.getStock() + " left.");
        }
        book.setStock(book.getStock() - request.quantite());
        bookRepository.save(book);

        BookBorrowing borrowing = bookBorrowingDTOMapper.toEntity(request);
        borrowing.setUser(user);
        borrowing.setBook(book);

        Payment payment = new Payment();
        payment.setAmount(
                borrowing.getBook().getPrice() * borrowing.getQuantite()
        );
        paymentRepository.save(payment);
        borrowing.setPayment(payment);

        bookBorrowingRepository.save(borrowing);

    }

    @Transactional
    public void cancelBorrowing(Integer id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        BookBorrowing borrowing = bookBorrowingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrowing not found"));
        if(!user.canManageBorrowing(borrowing)){
            throw new RuntimeException("You can only delete your own borrows");
        }
        Book book = borrowing.getBook();
        book.setStock(book.getStock() + borrowing.getQuantite());

        bookBorrowingRepository.delete(borrowing);
    }

    public PageResponse<BookBorrowingResponse> getMyBorrowings(Authentication authentication, Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        Page<BookBorrowingResponse> page = bookBorrowingRepository.findByUserId(user.getId(), pageable).map(bookBorrowingDTOMapper);
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