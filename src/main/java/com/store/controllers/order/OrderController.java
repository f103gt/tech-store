package com.store.controllers.order;

import com.store.model.product.CartItem;
import com.store.repository.cart.CartItemRepository;
import com.store.config.security.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    private final HttpSession session;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    @Autowired
    public OrderController(HttpSession session,
                           UserRepository userRepository,
                           CartItemRepository cartItemRepository) {
        this.session = session;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @GetMapping("/order")
    public String order() {
        String role = (String) session.getAttribute("role");
        if (role.equals("guest")) {
            Boolean storeToDatabase = false;
            session.setAttribute("storeToDatabase", storeToDatabase);
            return "redirect:/login";
        }
        Boolean storeToDatabase = (Boolean) session.getAttribute("storeToDatabase");
        if (storeToDatabase != null && storeToDatabase) {
            List<?> productsWild = (List<?>) session.getAttribute("cartItems");
            List<CartItem> cartItems = new ArrayList<>(productsWild.stream().map(object -> (CartItem) object).toList());
            BigInteger userId = (BigInteger) session.getAttribute("id");
            cartItems.forEach(cartItem -> cartItem.setUser(userRepository.getUserById(userId)));
            cartItemRepository.saveAll(cartItems);
        }
        return null;
    }

}
