package com.example.bookstoreApp.controller;

import com.example.bookstoreApp.model.Book;
import com.example.bookstoreApp.model.BookDto;
import com.example.bookstoreApp.model.User;
import com.example.bookstoreApp.service.BookService;
import com.example.bookstoreApp.service.OrderService;
import com.example.bookstoreApp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class BookController { //kontroler klasa za Book model

    private final BookService bookService;
    private final UserService userService;
    private final OrderService orderService;

    BookController(BookService bookService, UserService userService, OrderService orderService){
        this.bookService = bookService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping //Get mapping metoda za index stranicu
    ModelAndView getAllIndex(@Param("keyword") String keyword) {
        ModelAndView model = new ModelAndView("index");
        model.addObject("books", bookService.getAll(keyword));
        model.addObject("keyword", keyword);
        return model;
    }

    @GetMapping("/adminPage") //Get mapping metoda za admin stranicu
    ModelAndView getAllAdmin(@Param("keyword") String keyword) {
        ModelAndView model = new ModelAndView("adminPage");
        model.addObject("books", bookService.getAll(keyword));
        model.addObject("orders", orderService.getAll());
        model.addObject("keyword", keyword);
        return model;
    }

    @GetMapping("/adminPage/addNewBookListing") //Get mapping metoda za stranicu za dodavanje Book objekata
    public String showNewBookListingForm(Model model) {
        BookDto book = new BookDto();
        model.addAttribute("book", book);
        return "addNewBookListing";
    }

    @PostMapping("/adminPage/addNewBookListing/save") //Post mapping metoda za dodavanje novih Book objekata
    public String addNewBook(@Validated @ModelAttribute("book") BookDto bookDto,
                               BindingResult result,
                               Model model) {
        Book existingBook = bookService.findByTitle(bookDto.getTitle());

        if(existingBook != null && existingBook.getTitle() != null && !existingBook.getTitle().isEmpty()){
            result.rejectValue("title", null,
                    "There is already a listing with this title");
        }

        if(result.hasErrors()) {
            model.addAttribute("book", bookDto);
            return "/adminPage/addNewBookListing";
        }
        bookService.saveBook(bookDto);
        return "redirect:/adminPage/addNewBookListing?success";
    }

    @GetMapping("/adminPage/editBookListing/{id}") //Get mapping metoda za stranicu za izmenu postojecih Book objekata
    public ModelAndView editBookListingPage(@PathVariable Long id) {
        ModelAndView model = new ModelAndView("editBookListing");
        model.addObject("book", bookService.findById(id));
        return model;
    }

    @PostMapping("/adminPage/editBookListing/{id}/save") //Post mapping metoda za izmenu Book objekata
    public String editBookListing(@PathVariable Long id,
                                  @Validated @ModelAttribute("book") BookDto bookDto,
                             BindingResult result,
                             Model model) {

        if(result.hasErrors()) {
            model.addAttribute("book", bookDto);
            return "/adminPage/editBookListing";
        }
        bookService.editBook(id, bookDto);
        return "redirect:/adminPage/editBookListing/{id}?success";
    }

    @GetMapping("/adminPage/delete/{id}") //Get mapping metoda za brisanje Book objekata
    public String deleteBookListing(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/adminPage";
    }

    @GetMapping("/cartPage") //Get mapping metoda za cartPage stranicu
    public ModelAndView cartPage(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("cartPage");
        User currentUser = userService.getCurrentUser(request);
        List<Book> currentUserCart = currentUser.getCart();
        double totalPrice = userService.totalCartPrice(currentUserCart);
        model.addObject("cart", currentUserCart);
        model.addObject("totalPrice", totalPrice);
        return model;
    }

    @GetMapping("/addToCart/{id}") //Get mapping metoda za dodavanje objekta u cart korisnika
    public String addItemToCart(HttpServletRequest request, @PathVariable Long id) {
        User currentUser = userService.getCurrentUser(request);
        List<Book> currentUserCart = currentUser.getCart();
        currentUserCart.add(bookService.findById(id));
        userService.updateUserCart(currentUser.getEmail(),currentUserCart);
        return "redirect:/?success";
    }

    @GetMapping("cartPage/remove/{id}") //Get mapping metoda za brisanje objekta iz cart-a korisnika
    public String removeCartItem(HttpServletRequest request, @PathVariable Long id) {
        User currentUser = userService.getCurrentUser(request);
        List<Book> currentUserCart = currentUser.getCart();
        currentUserCart.remove(bookService.findById(id));
        userService.updateUserCart(currentUser.getEmail(),currentUserCart);
        return "redirect:/cartPage";
    }

}