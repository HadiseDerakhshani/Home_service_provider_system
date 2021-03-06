package service;

import dao.SuggestionDao;
import model.Suggestion;
import model.person.Expert;

public class SuggestionService {
    private SuggestionDao suggestionDao = new SuggestionDao();

    public Suggestion createSuggest(String info, Expert expert) {
        String[] split = info.split(",");
        Suggestion suggestion = Suggestion.builder()
                .ProposedPrice(Double.parseDouble(split[0]))
                .durationOfWork(Integer.parseInt(split[1]))
                .startTime(Integer.parseInt(split[2]))
                .expert(expert)
                .build();
        suggestionDao.save(suggestion);
        return suggestion;
    }
}
