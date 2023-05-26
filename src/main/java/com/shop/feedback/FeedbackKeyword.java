package com.shop.feedback;

import java.util.*;

public class FeedbackKeyword {

    private WordAnalysisService wordAnalysisService;

    public FeedbackKeyword(WordAnalysisService wordAnalysisService) {
        this.wordAnalysisService = wordAnalysisService;
    }

    public Map<String, Integer> getTopKeywords(List<String> texts, int topCount) throws Exception{
        Map<String, Integer> aggregateMap = new HashMap<>();

        // 모든 게시글에 대해 키워드와 빈도수를 집계
        for (String text : texts) {
            Map<String, Integer> keywordMap = wordAnalysisService.doWordAnalysis(text);
            for (Map.Entry<String, Integer> entry : keywordMap.entrySet()) {
                String keyword = entry.getKey();
                int frequency = entry.getValue();

                // 기존에 해당 키워드가 집계 맵에 존재하는 경우, 기존 빈도에 현재 빈도를 더해 업데이트
                if (aggregateMap.containsKey(keyword)) {
                    int existingFrequency = aggregateMap.get(keyword);
                    aggregateMap.put(keyword, existingFrequency + frequency);
                } else {
                    // 새로운 키워드인 경우, 집계 맵에 추가
                    aggregateMap.put(keyword, frequency);
                }
            }
        }

        // 집계된 키워드 맵을 빈도 내림차순으로 정렬
        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(aggregateMap.entrySet());
        sortedList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // 상위 N개 키워드 추출하여 Keywords 맵에 담기
        Map<String, Integer> Keywords = new HashMap<>();
        int count = Math.min(topCount, sortedList.size());
        for (int i = 0; i < count; i++) {
            Map.Entry<String, Integer> entry = sortedList.get(i);
            String keyword = entry.getKey();
            int frequency = entry.getValue();
            Keywords.put(keyword, frequency);
        }

        return Keywords;
    }

    public void processFeedback(int num) throws Exception{
        WordAnalysisService wordAnalysisService = new WordAnalysisService();
        FeedbackKeyword feedbackKeyword = new FeedbackKeyword(wordAnalysisService);

        List<String> texts = new ArrayList<>();
        // 여러 개의 게시글 내용을 texts에 추가

        // 상위 5개 키워드와 빈도 가져오기
        Map<String, Integer> Keywords = feedbackKeyword.getTopKeywords(texts, num);

        // 결과 출력
        for (Map.Entry<String, Integer> entry : Keywords.entrySet()) {
            String keyword = entry.getKey();
            int frequency = entry.getValue();
            System.out.println("Keyword: " + keyword + ", Frequency: " + frequency);
        }
    }
}

