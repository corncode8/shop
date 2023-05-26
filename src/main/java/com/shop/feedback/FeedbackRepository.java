package com.shop.feedback;

import com.shop.feedback.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    Feedback findById(int id);
    Feedback findByContent(String content);
    Feedback findByForm(String form);
    Feedback findByUser (int id);
    Feedback findFeedbackById(int id);
    Feedback findFeedbackByForm(String form);
    @Query("SELECT f.content FROM Feedback f")
    List<String> findAllTexts();

}
