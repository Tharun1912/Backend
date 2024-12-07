package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/artworks")
@CrossOrigin(origins = "http://localhost:3000")
public class ArtworkController {

    @Autowired
    private ArtworkService artworkService;

    // Upload Artwork
    @PostMapping("/upload")
    public ResponseEntity<?> uploadArtwork(
            @RequestParam("title") String title,
            @RequestParam("artist") String artist,
            @RequestParam("price") BigDecimal price,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("image") MultipartFile image) {
        try {
            Artwork savedArtwork = artworkService.saveArtwork(title, artist, price, description, category, image);
            return ResponseEntity.ok("Artwork uploaded successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); // 409 Conflict for duplicate entry
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading artwork: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }

    // Get Artwork Image by ID
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getArtworkImage(@PathVariable Long id) {
        Optional<Artwork> artwork = artworkService.findById(id);

        if (artwork.isPresent() && artwork.get().getImage() != null) {
            return ResponseEntity.ok(artwork.get().getImage());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Get Artworks by Category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Artwork>> getArtworksByCategory(@PathVariable String category) {
        List<Artwork> artworks = artworkService.findByCategory(category);
        if (!artworks.isEmpty()) {
            return ResponseEntity.ok(artworks);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
    }

    // Get Artwork Details by ID
    @GetMapping("/{id}")
    public ResponseEntity<Artwork> getArtworkDetails(@PathVariable Long id) {
        Optional<Artwork> artwork = artworkService.findById(id);
        return artwork.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Get All Artworks
    @GetMapping
    public ResponseEntity<List<Artwork>> getAllArtworks() {
        List<Artwork> artworks = artworkService.findAll();
        if (!artworks.isEmpty()) {
            return ResponseEntity.ok(artworks);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
    }

    // Delete Artwork by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArtwork(@PathVariable Long id) {
        try {
            artworkService.deleteArtwork(id);
            return ResponseEntity.ok("Artwork deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
