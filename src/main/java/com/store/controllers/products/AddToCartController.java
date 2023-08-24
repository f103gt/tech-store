package com.store.controllers.products;

import com.store.model.product.CartItem;
import com.store.model.product.Category;
import com.store.model.product.Image;
import com.store.model.product.Product;
import com.store.config.security.model.User;
import com.store.repository.cart.CartItemRepository;
import com.store.repository.products.ProductRepository;
import com.store.config.security.repository.UserRepository;
import com.store.validation.PriceUtility;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AddToCartController {
    private final HttpSession session;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final PriceUtility priceUtility;

    @Autowired
    public AddToCartController(HttpSession session,
                               ProductRepository productRepository,
                               CartItemRepository cartItemRepository,
                               UserRepository userRepository,
                               PriceUtility priceUtility) {
        this.session = session;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.priceUtility = priceUtility;
    }

    @GetMapping("/add-to-cart")
    public String addToCart(@RequestParam("productName") String productName) {
        Product product = productRepository.getProductByProductName(productName);
        Category category = productRepository.findCategoryByProductName(productName);
        BigDecimal discountRate = priceUtility.calculateDiscountRate(category);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setFinalPrice(product.getPrice().multiply(discountRate).divide(BigDecimal.valueOf(100),
                RoundingMode.CEILING));
        String role = (String) session.getAttribute("role");
        if (role.equals("guest")) {
            //CartItem sessionCartItem = (CartItem) session.getAttribute(productName);
            List<?> sessionCartItemsWild = (List<?>) session.getAttribute("cartItems");
            if (sessionCartItemsWild != null) {
                List<CartItem> sessionCartItems = new ArrayList<>(sessionCartItemsWild.stream().map(object -> (CartItem) object).toList());
                Optional<CartItem> sessionCartItem = sessionCartItems.stream().filter(
                                item -> item.getProduct().getProductName().equals(productName))
                        .findFirst()
                        .map(item -> {
                            item.setQuantity(item.getQuantity() + 1);
                            session.setAttribute("cartItems", sessionCartItems);
                            return item;
                        })
                        .or(() -> {
                            cartItem.setQuantity(1);
                            BigDecimal price = priceUtility.calculatePrice(category, product);
                            cartItem.setFinalPrice(price);
                            sessionCartItems.add(cartItem);
                            session.setAttribute("cartItems", sessionCartItems);
                            return Optional.of(cartItem);
                        });
            } else {
                List<CartItem> cartItems = new ArrayList<>();
                BigDecimal price = priceUtility.calculatePrice(category, product);
                cartItem.setFinalPrice(price);
                cartItem.setQuantity(1);
                cartItems.add(cartItem);
                session.setAttribute("cartItems", cartItems);
            }
        } else {
            Optional<CartItem> cartItemStored = cartItemRepository.findCartItemByProductId(product.getId());
            cartItemStored.ifPresentOrElse(item -> {
                        Integer quantity = item.getQuantity() + 1;
                        cartItemRepository.updateCartItemQuantityByCartItemId(item.getId(), quantity);
                    },
                    () -> {
                        cartItem.setQuantity(1);
                        /*Optional<User> userOptional = userRepository.findUserById((BigInteger)
                                session.getAttribute("id"));
                        cartItem.setUser(userOptional.ifPresent(user -> {cartItem.setUser(user)}));*/
                        User user = userRepository.getUserById((BigInteger)
                                session.getAttribute("id"));
                        //TODO extract the calculation of final price into price utility
                        BigDecimal price = priceUtility.calculatePrice(category, product);
                        cartItem.setFinalPrice(price);
                        cartItem.setUser(user);
                        cartItemRepository.save(cartItem);
                    });
        }
        /*product.setPrice(BigDecimal.valueOf(Long.parseLong(price)));
        if(session.getAttribute("products")==null){
            session.setAttribute("products",new ArrayList<Product>());
        }
        List<?> productsWild = (List<?>) session.getAttribute("products");
        List<Product> products = new ArrayList<>(productsWild.stream().map(object -> (Product) object).toList());
        products.add(product);*/
        return "redirect:/" + category.getCategoryName();
    }

    @GetMapping("/cart")
    public String showCart(Model model) {
        String role = (String) session.getAttribute("role");
        List<CartItem> cartItems = new ArrayList<>();
        if (role.equals("guest")) {
            List<?> productsWild = (List<?>) session.getAttribute("cartItems");
            if (productsWild != null) {
                cartItems = new ArrayList<>(productsWild.stream().map(object -> (CartItem) object).toList());
            }
        } else {
            BigInteger userId = (BigInteger) session.getAttribute("id");
            cartItems = cartItemRepository.findAllCartItemsByUserId(userId);
        }
        BigDecimal finalPrice = priceUtility.calculateTotalPrice(cartItems);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("finalPrice", finalPrice.add(BigDecimal.valueOf(10)));
        return "general/cart";
    }

    @GetMapping("/primary-image")
    public void showPrimaryImage(@RequestParam("cartItemProductName") String cartItemProductName, HttpServletResponse response) {
        String role = (String) session.getAttribute("role");
        List<Image> images = new ArrayList<>();
        if (role.equals("guest")) {
            List<?> productsWild = (List<?>) session.getAttribute("cartItems");
            if (productsWild != null) {
                List<CartItem> cartItems = new ArrayList<>(productsWild.stream().map(object -> (CartItem) object).toList());
                images = cartItems.stream()
                        .map(cartItem -> cartItem.getProduct().getImages())
                        .flatMap(List::stream)
                        .toList();
            }
        } else {
            images = productRepository.findImagesByProductName(cartItemProductName);
        }
        Image primaryImage = images.stream()
                .filter(image -> image.isPrimaryImage().equals(true))
                .findFirst()
                .get();
        try {
            response.getOutputStream().write(primaryImage.getImageData());
            response.getOutputStream().close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @GetMapping("/add-item")
    public String addAnotherItem(@RequestParam("cartItem") CartItem cartItem,
                                 @RequestParam("action")String action) {
        Integer quantity = cartItem.getQuantity();
        if(action.equals("augment")){
            quantity++;
        }
        else{
            quantity--;
        }
        String role = (String) session.getAttribute("role");
        if (role.equals("guest")) {
            List<?> productsWild = (List<?>) session.getAttribute("cartItems");
            if (productsWild != null) {
                List<CartItem> cartItems = new ArrayList<>(productsWild.stream().map(object -> (CartItem) object).toList());
                cartItem.setQuantity(quantity);
                cartItems.stream()
                        .filter(ci -> ci.getProduct().getProductName().equals(cartItem.getProduct().getProductName()))
                        .map(ci->cartItem);
            }
        } else {
            Optional<CartItem> cartItemStored = cartItemRepository.findCartItemByProductId(cartItem.getProduct().getId());
            Integer finalQuantity = quantity;
            cartItemStored.ifPresent(item -> {
                cartItemRepository.updateCartItemQuantityByCartItemId(item.getId(),finalQuantity);
            });
        }
        return "redirect:/cart";
    }
}
