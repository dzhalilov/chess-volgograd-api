package com.example.chessvolgograd.util;

import com.example.chessvolgograd.model.Player;
import com.example.chessvolgograd.model.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerSpecificationsBuilder {
    private final List<SearchCriteria> params;

    public PlayerSpecificationsBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public PlayerSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Player> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(PlayerSpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }
}
