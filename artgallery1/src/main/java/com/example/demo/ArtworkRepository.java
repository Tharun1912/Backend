package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    // Find an artwork by title and artist
    Optional<Artwork> findByTitleAndArtist(String title, String artist);

    // Find artworks by category
    List<Artwork> findByCategory(String category);
}
