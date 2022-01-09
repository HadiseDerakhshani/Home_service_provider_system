package service;

import data.dao.SuggestionDao;
import data.model.order.Suggestion;
import data.model.user.Expert;
import exception.IsNullObjectException;
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
        if (!suggestionDao.exists(suggestion)) {
            suggestionDao.save(suggestion);
            return suggestion;
        } else
            throw new IsNullObjectException("---this suggestion exited---");
    }
}
