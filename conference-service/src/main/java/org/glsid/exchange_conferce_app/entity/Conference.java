package org.glsid.exchange_conferce_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conference {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String titre;
    
    @Enumerated(EnumType.STRING)
    private ConferenceType type;
    
    private LocalDateTime date;
    
    private Integer duree;
    
    private Integer nombreInscrits;
    
    private Double score;
    
    private Long keynoteId;
    
    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
    
    // Method to calculate and update the average score based on reviews
    public void calculateScore() {
        if (reviews == null || reviews.isEmpty()) {
            this.score = 0.0;
            return;
        }
        
        double sum = reviews.stream()
                .mapToInt(Review::getNote)
                .sum();
        
        this.score = sum / reviews.size();
    }
}