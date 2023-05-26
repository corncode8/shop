package com.shop.feedback;

import com.shop.feedback.Feedback;
import com.shop.feedback.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;


    /** seller의 피드백 등록 */
    public void createsellerFeedback(Feedback feedback, String form) {
        feedbackRepository.findFeedbackByForm(form);
        feedbackRepository.save(feedback);
    }

    /** 피드백 등록 */
    public void createFeedback(Feedback feedback) throws Exception{
        feedbackRepository.save(feedback);
    }
    /** 피드백 read */
    public Feedback feedbackView(Integer id) {
        return feedbackRepository.findById(id).get();
    }

    /** 피드백 수정 */
    @Transactional
    public void feedbackModify(Feedback feedback, Integer id) {
        Feedback update = feedbackRepository.findFeedbackById(id);
        update.setContent(feedback.getContent());
        update.setScore(feedback.getScore());
        feedbackRepository.save(update);
    }

    /** 피드백 삭제 */
    @Transactional
    public void feedbackDelete(Integer id) {
       Feedback num = feedbackRepository.findById(id).get();
       feedbackRepository.deleteById(num.getId());
    }

    /** 피드백들의 내용 추출 */
    public List<String> getAllFeedbackTexts() {
        return feedbackRepository.findAllTexts();
    }

}
