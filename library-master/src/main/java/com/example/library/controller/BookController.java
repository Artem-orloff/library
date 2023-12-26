package com.example.library.controller;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.model.enums.Role;
import com.example.library.service.AuthorService;
import com.example.library.service.BookService;
import com.example.library.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/library")
public class BookController {
    private static final ThreadLocal<SimpleDateFormat> dateFormat =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("dd.MM.yyyy"));
    @Autowired
    BookService bookService;
    @Autowired
    AuthorService authorService;
    @Autowired
    UsersService usersService;

    @GetMapping("/book")
    public String getAllBooks(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books",books);
        return "book-main";
    }

    @GetMapping("/book/{id}")
    public String getABookById(@PathVariable(value = "id") Long bookId, Model model) {
        Optional<Book> book = bookService.findById(bookId);
        ArrayList<Book> res = new ArrayList<>();
        book.ifPresent(res::add);
        model.addAttribute("book", res);
        return "book-details";
    }

    @GetMapping("/book/add")
    public String bookAdd(Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "book-add";
    }
    @PostMapping("/book/add")
    public String bookPostAdd(@RequestParam String name, @RequestParam String publication, @RequestParam String genre,
                              @RequestParam String author, Model model) throws ParseException {
        Date publicationDate = dateFormat.get().parse(publication);
        Optional<Author> authorId = authorService.findById(Long.parseLong(author));
        if(authorId.isPresent()) {
            Book book = new Book(name, publicationDate, genre);
            book.setAuthor(authorId.get());
            bookService.create(book);
        }
        return "redirect:/library/book";
    }

    @GetMapping("/book/{id}/edit")
    public String getBookEdit(@PathVariable(value = "id") Long bookId, Model model) {
        Optional<Book> book = bookService.findById(bookId);
        ArrayList<Book> res = new ArrayList<>();
        book.ifPresent(res::add);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("book", res);
        return "book-edit";
    }
    @PostMapping("/book/{id}/edit")
    public String bookPostUpdate(@PathVariable(value = "id") Long bookId, @RequestParam String name,
                                 @RequestParam String publication, @RequestParam String genre,
                                 @RequestParam String author, Model model)
            throws ParseException {
        Book book = bookService.findById(bookId).orElseThrow();
        Date publicationDate = dateFormat.get().parse(publication);
        Optional<Author> authorId = authorService.findById(Long.parseLong(author));
        if(authorId.isPresent())
        {
            book.setName(name);
            book.setPublication(publicationDate);
            book.setGenre(genre);
            book.setAuthor(authorId.get());
            bookService.create(book);
        }
        model.addAttribute("user", usersService.findAll());
        model.addAttribute("book", book);
        model.addAttribute("result", "Success update");
        return "book-details";
    }

    @PostMapping("/book/{id}/remove")
    public String bookPostDelete(@PathVariable(value = "id") Long bookId, Model model) {
        Book book = bookService.findById(bookId).orElseThrow();
        bookService.delete(book.getBookId());
        return "redirect:/library/book";
    }

    @GetMapping("/take/{book_id}")
    public ResponseEntity<?> takeBook(@PathVariable(value = "book_id") Long bookId) {
        User user = getUser();
        Book book = bookService.getById(bookId);
        if(user.getType() == Role.READER) {
            System.out.println("Correct");
        }
        else { System.out.println("Incorrect");}
        return ResponseEntity.ok().build();
    }
    public static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }
    //    @GetMapping("/{user_id}/put")
//    public ResponseEntity<?> putBook(PathVariable(value = "user_id") Long userId){
//        User user = usersService.getById(userId);
//        return ;
//    }
}