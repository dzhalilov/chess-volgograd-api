package com.example.chessvolgograd.service;

import com.example.chessvolgograd.model.Player;
import com.example.chessvolgograd.repository.PlayerRepository;
import com.example.chessvolgograd.util.PlayerSpecificationsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PlayerService {

//    @PersistenceContext
//    private EntityManager em;

    @Autowired
    PlayerRepository playerRepository;

    public List<Player> getPlayersWithFilter(Map<String, String> params) {
        PlayerSpecificationsBuilder builder = new PlayerSpecificationsBuilder();
        System.out.println("####################################################");
        for (Map.Entry<String, String> filter : params.entrySet()) {
            System.out.println(filter.getKey() + " = " + filter.getValue());
            if (filter.getKey().equals("sex") && (filter.getValue().equals("f") || filter.getValue().equals("m"))) {
                builder.with(filter.getKey(), "=", filter.getValue().equals("f") ? "Ж" : "М");
            }
            if (filter.getKey().equals("ageStart") &&
                    Integer.parseInt(filter.getValue()) >= 1950 && Integer.parseInt(filter.getValue()) <= 2100) {
                builder.with("age", ">", Integer.parseInt(filter.getValue()));
            }
            if (filter.getKey().equals("ageEnd") &&
                    Integer.parseInt(filter.getValue()) >= 1950 && Integer.parseInt(filter.getValue()) <= 2100) {
                builder.with("age", "<", Integer.parseInt(filter.getValue()));
            }
            if (filter.getKey().equals("name")) {
                builder.with(filter.getKey(), "%", filter.getValue());
            }
        }

        return playerRepository.findAll(builder.build());
    }

//    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//    CriteriaQuery<Player> criteriaQuery = criteriaBuilder.createQuery(Player.class);
//    Root<Player> playerRoot = criteriaQuery.from(Player.class);
//
//    Predicate finalPredicate = criteriaBuilder.conjunction();
//
//    public List<Player> getAllWithFilters(Map<String, String> params) {
//        List<Predicate> predicates = new ArrayList<>();
//        for (Map.Entry<String, String> filter : params.entrySet()) {
//            if (filter.getKey().equals("sex")) {
//                if (filter.getValue().equals("m")) {
//                    Predicate predicate = criteriaBuilder.equal(playerRoot.get("sex"), "М");
//                    predicates.add(predicate);
//                } else if (filter.getValue().equals("f")) {
//                    Predicate predicate = criteriaBuilder.equal(playerRoot.get("sex"), "Ж");
//                    predicates.add(predicate);
//                }
//            }
//        }
//
//
//        for (Predicate predicate : predicates) {
//            finalPredicate = criteriaBuilder.and(finalPredicate, predicate);
//        }
//        criteriaQuery.where(finalPredicate);
//        return em.createQuery(criteriaQuery).getResultList();
//    }
}
