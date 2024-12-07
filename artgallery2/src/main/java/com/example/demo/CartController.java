package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from the frontend
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    // Save cart along with its items
    @PostMapping
    public ResponseEntity<Cart> saveCart(@RequestBody Cart cart) {
        cart.getItems().forEach(item -> item.setCart(cart)); // Link items to the cart
        Cart savedCart = cartRepository.save(cart);
        return ResponseEntity.ok(savedCart);
    }

    // Get cart by ID
    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCart(@PathVariable Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        return cart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // List all carts
    @GetMapping
    public ResponseEntity<List<Cart>> listAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        if (carts.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 if no carts exist
        }
        return ResponseEntity.ok(carts);
    }

 // Calculate total cart cost
    @GetMapping("/total_cost")
    public ResponseEntity<Double> getTotalCartCost() {
        List<Cart> cartItems = cartRepository.findAll();
        double totalCost = cartItems.stream()
                                    .flatMap(cart -> cart.getItems().stream()) // Get all cart items
                                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                                    .sum();
        return ResponseEntity.ok(totalCost); // Return total cost as plain double
    }

    // Delete a cart by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Long id) {
        if (cartRepository.existsById(id)) {
            cartRepository.deleteById(id);
            return ResponseEntity.ok("Cart deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Cart not found");
        }
    }
    
    

}
