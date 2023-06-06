package com.shop.controller;

import com.shop.config.auth.PrincipalDetails;
import com.shop.feedback.Feedback;
import com.shop.feedback.FeedbackKeyword;
import com.shop.model.User;
import com.shop.feedback.FeedbackService;
import com.shop.feedback.WordAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    private FeedbackKeyword feedbackKeyword;


    private Feedback feedback;

    // seller 피드백 등록 페이지 (GET)
    @GetMapping("/seller/mypage/feedback/new")
    public String createFeedback(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        if (principalDetails.getUser().getRole().equals("ROLE_SELLER")) {
            model.addAttribute("seller", principalDetails.getUser());

            return "seller 피드백 등록 페이지";
            //return "/seller/mypage/feedbackForm";
        } else {
            return "redirect:/seller/mypage";
        }
    }

    /** seller 피드백 게시 */
    @PostMapping("seller/feedback/new")
    public String createsellerFeedback (Feedback feedback, @AuthenticationPrincipal PrincipalDetails principalDetails, String from) throws Exception {
        if (principalDetails.getUser().getRole().equals("ROLE_SELLER")) {
            feedback.setUser(principalDetails.getUser());
            feedbackService.createsellerFeedback(feedback, from);
            return "redirect:/seller/feedback";
        } else {
            return "redirect:/main";
        }
    }


    // 피드백 등록 페이지 (GET)
    @GetMapping("/feedback/new")
    public String createFeedbackForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        if(principalDetails.getUser().getRole().equals("ROLE_USER")) {
            model.addAttribute("user", principalDetails.getUser());

            return "피드백 등록 페이지입니다.";
            //return "/user/feedbackFrom";
        } else {
            return "redirect:/main";
        }
    }


    /** 피드백 등록 (user만 가능) */
    @PostMapping("/feedback/new")
    public String createFeedback(Feedback feedback, @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
        if (principalDetails.getUser().getRole().equals("ROLE_USER")) {
            feedback.setUser(principalDetails.getUser());
            feedbackService.createFeedback(feedback);
            return "redirect:/main/feedback";
        }else {
            return "redirect:/main";
        }
    }

    /** 피드백 read */
    @GetMapping("/feedback/view/{id}")
    public @ResponseBody String readFeedback(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("feedback", feedbackService.feedbackView(id));
        return "피드백 view 페이지 입니다";
        // return "feedback/view";
    }

    /** 피드백 수정 페이지 */
    @GetMapping("/mypage/feedback/modify/{id}")
    public String feedbackModify(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails.getUser().equals("ROLE_USER")) {
            User user = feedbackService.feedbackView(id).getUser();
            if(user.getId() == principalDetails.getUser().getId()) {
                model.addAttribute("feedback", feedbackService.feedbackView(id));
                model.addAttribute("user", principalDetails.getUser());

                return "/mypage/feedbackModify";
            }else {
                return "redirect:/main";
            }
        } else {
            return "redirect:/main";
        }
    }

    /** 피드백 수정 */
    @PostMapping("/mypage/feedback/modify/{id}")
    public String feedbackModify(Feedback feedback, @PathVariable("id") Integer id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails.getUser().getRole().equals("ROLE_USER")) {
            User user = feedbackService.feedbackView(id).getUser();

            if(user.getId() == principalDetails.getUser().getId()) {
                feedbackService.feedbackModify(feedback, id);
                return "redirect:/feedback";
            } else {
                return "redirect:/main";
            }
        } else {
            return "redirect:/main";
        }
    }

    /** 피드백 삭제 */
    @GetMapping("/mypage/feedback/delete/{id}")
    public String feedbackDelete(@PathVariable("id") Integer id) {
        feedbackService.feedbackDelete(id);
        return "상품 삭제";
        //return "/mypage/feedback";
    }




    // 자연어 추출 값 반환 test
    @Autowired
    private WordAnalysisService wordAnalysisService;

    @GetMapping("/test/feedback")
    @ResponseBody
    public Map<String, Integer> analysis() throws Exception {

        //분석할 문장
        String text = "아침에 밥을 꼭 먹고 점심엔 점심 밥을 꼭 먹고 저녁엔 저녁 밥을 꼭 먹자!@@";


        Map<String, Integer> rMap = wordAnalysisService.doWordAnalysis(text);


        if(rMap == null) {
            rMap = new HashMap<String, Integer>();
        }

        return rMap;
    }


    /** 피드백들의 자연어 추출 */
    @GetMapping("/main/feedback")
    public Map<String, Integer> getTopKeywords() throws Exception {
        List<String> allFeedbackTexts = feedbackService.getAllFeedbackTexts();
        int topCount = 5; // 상위 N개의 키워드 추출

        return feedbackKeyword.getTopKeywords(allFeedbackTexts, topCount);
    }
}





