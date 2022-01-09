package service;

import data.dao.SuggestionDao;
import data.model.order.Suggestion;
import data.model.user.Expert;
import org.springframework.stereotype.Service;

@Service
public class SuggestionService {
    private SuggestionDao suggestionDao;

    public SuggestionService(SuggestionDao suggestionDao) {
        this.suggestionDao = suggestionDao;
    }

    public Suggestion createSuggest(double price, int timeSpan, int time, Expert expert) {

        Suggestion suggestion = Suggestion.builder()
                .ProposedPrice(price)
                .durationOfWork(timeSpan)
                .startTime(time)
                .expert(expert)
                .build();
        suggestionDao.save(suggestion);
        return suggestion;
    }
}
