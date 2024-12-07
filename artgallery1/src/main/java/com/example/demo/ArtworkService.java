package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ArtworkService {

    @Autowired
    private ArtworkRepository artworkRepository;

    // Save artwork (with title, artist, price, description, category, and image)
    public Artwork saveArtwork(String title, String artist, BigDecimal price, String description, String category, MultipartFile image) throws IOException {
        // Check if an artwork with the same title and artist already exists
        Optional<Artwork> existingArtwork = artworkRepository.findByTitleAndArtist(title, artist);
        if (existingArtwork.isPresent()) {
            throw new IllegalArgumentException("An artwork with the same title and artist already exists.");
        }

        // If not a duplicate, create a new Artwork object
        Artwork artwork = new Artwork();
        artwork.setTitle(title);
        artwork.setArtist(artist);
        artwork.setPrice(price);
        artwork.setDescription(description);
        artwork.setCategory(category);

        // Save the image bytes if an image is provided
        if (image != null && !image.isEmpty()) {
            artwork.setImage(image.getBytes());
        }

        // Save artwork to the repository
        return artworkRepository.save(artwork);
    }

    // Find artwork by ID
    public Optional<Artwork> findById(Long id) {
        return artworkRepository.findById(id);
    }

    // Find artworks by category
    public List<Artwork> findByCategory(String category) {
        return artworkRepository.findByCategory(category);
    }

    // Fetch all artworks
    public List<Artwork> findAll() {
        return artworkRepository.findAll();
    }

    // Delete an artwork by ID
    public void deleteArtwork(Long id) {
        if (artworkRepository.existsById(id)) {
            artworkRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Artwork with ID " + id + " does not exist.");
        }
    }
}
