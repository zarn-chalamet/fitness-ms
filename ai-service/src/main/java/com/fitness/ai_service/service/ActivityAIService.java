package com.fitness.ai_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.ai_service.model.Activity;
import com.fitness.ai_service.model.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAIService {

    private final GeminiService geminiService;

    public Recommendation generateRecommendation(Activity activity) {

        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getAnswer(prompt);
//        log.info("RESPONSE FROM AI: {}", aiResponse);



        return processAIResponse(activity,aiResponse);
    }

    private Recommendation processAIResponse(Activity activity, String aiResponse) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(aiResponse);

            JsonNode textNode = rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text");

            String jsonContent = textNode.asText()
                    .replaceAll("```json\\\n","")
                    .replaceAll("\\n```","")
                    .trim();

            log.info("PARSED FROM JSON : {} ", jsonContent);
            JsonNode analysisJson = mapper.readTree(jsonContent);
            JsonNode analysisNode = analysisJson.path("analysis");

            StringBuilder fullAnalysis = new StringBuilder();
            addAnalysisSection(fullAnalysis,analysisNode,"overall","Overall:");
            addAnalysisSection(fullAnalysis,analysisNode,"pace","Pace:");
            addAnalysisSection(fullAnalysis,analysisNode,"heartRate","Heart Rate:");
            addAnalysisSection(fullAnalysis,analysisNode,"caloriesBurned","Calories:");

            //improvement
            List<String> improvements = extractImprovements(analysisJson.path("improvements"));

            //suggestions
            List<String> suggestions = extractSuggestions(analysisJson.path("suggestions"));
            
            //safety
            List<String> safety = extractSafetyGuidelines(analysisJson.path("safety"));

            //create recommendation
            Recommendation recommendation = new Recommendation();
            recommendation.setActivityId(activity.getId());
            recommendation.setUserId(activity.getUserId());
            recommendation.setActivityType(activity.getType());
            recommendation.setRecommendation(fullAnalysis.toString().trim());
            recommendation.setImprovements(improvements);
            recommendation.setSuggestions(suggestions);
            recommendation.setSafety(safety);
            recommendation.setCreatedAt(LocalDateTime.now());

            return recommendation;

        } catch (Exception e) {
            e.printStackTrace();
            return createDefaultRecommendation(activity);
        }
    }

    private Recommendation createDefaultRecommendation(Activity activity) {
        Recommendation recommendation = new Recommendation();
        recommendation.setActivityId(activity.getId());
        recommendation.setUserId(activity.getUserId());
        recommendation.setActivityType(activity.getType());
        recommendation.setRecommendation("Unable to generate detailed analysis");
        recommendation.setImprovements(Collections.singletonList("Continue with your current plan."));
        recommendation.setSuggestions(Collections.singletonList("Consider consulting with a specialist."));
        recommendation.setSafety(Arrays.asList(
                "Always warm up before exercise",
                "Stay hydrated",
                "Listen to your body"
        ));
        recommendation.setCreatedAt(LocalDateTime.now());

        return recommendation;
    }

    private List<String> extractSafetyGuidelines(JsonNode safetyNode) {
        List<String> safety = new ArrayList<>();
        if(safetyNode.isArray()) {
            safetyNode.forEach( item -> safety.add(item.asText()));
        }

        return safety.isEmpty() ?
                Collections.singletonList("Follow general safety guidelines.") :
                safety;
    }

    private List<String> extractSuggestions(JsonNode suggestionsNode) {

        List<String> suggestions = new ArrayList<>();
        if(suggestionsNode.isArray()) {
            suggestionsNode.forEach(suggestion -> {
                String workout = suggestion.path("workout").asText();
                String description = suggestion.path("description").asText();
                suggestions.add(String.format("%s: %s", workout, description));
            });
        }

        return suggestions.isEmpty() ?
                Collections.singletonList("No specific suggestions provided.") :
                suggestions;
    }

    private List<String> extractImprovements(JsonNode improvementsNode) {

        List<String> improvements = new ArrayList<>();
        if(improvementsNode.isArray()) {
            improvementsNode.forEach(improvement -> {
                String area = improvement.path("area").asText();
                String detail = improvement.path("recommendation").asText();
                improvements.add(String.format("%s: %s", area, detail));
            });
        }
        return improvements.isEmpty() ?
                Collections.singletonList("No specific improvements provided.") :
                improvements;
    }

    private void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix) {
        if(!analysisNode.path(key).isMissingNode()) {
            fullAnalysis.append(prefix)
                    .append(prefix)
                    .append(analysisNode.path(key).asText())
                    .append("\n\n");
        }
    }

    private String createPromptForActivity(Activity activity) {

        return String.format(""" 
                  Analyze this fitness activity and provide detailed recommendations in the following format
                  {
                      "analysis" : {
                          "overall": "Overall analysis here",
                          "pace": "Pace analysis here",
                          "heartRate": "Heart rate analysis here",
                          "CaloriesBurned": "Calories Burned here"
                      },
                      "improvements": [
                          {
                              "area": "Area name",
                              "recommendation": "Detailed Recommendation"
                          }
                      ],
                      "suggestions" : [
                          {
                              "workout": "Workout name",
                              "description": "Detailed workout description"
                          }
                      ],
                      "safety": [
                          "Safety point 1",
                          "Safety point 2"
                      ]
                  }
                
                  Analyze this activity:
                  Activity Type: %s
                  Duration: %d minutes
                  calories Burned: %d
                  Additional Metrics: %s
                
                  provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines
                  Ensure the response follows the EXACT JSON format shown above.
                """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalMetrics()
        );

    }
}
