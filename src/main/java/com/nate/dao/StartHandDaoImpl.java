package com.nate.dao;

import com.nate.model.entities.StartHand;
import com.nate.model.enums.Card;
import com.nate.structure.Pair;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StartHandDaoImpl implements RowMapper<StartHand> {

    private static final String SELECT = "select id, holeOneValue, holeTwoValue, holeOneSuit, holeTwoSuit from start_hand";
    private static final String SELECT_BY_CARDS = "select id, holeOneValue, holeTwoValue, holeOneSuit, holeTwoSuit from start_hand" +
            " where holeOneValue = :firstValue and holeTwoValue = :secondValue and holeOneSuit = :firstSuit" +
            " holeTwoSuit = :secondSuit";
    private static final String INSERT = "insert into start_hand (id, holeOneValue, holeTwoValue, holeOneSuit, holeTwoSuit) " +
            "values (:id, :firstValue, :secondValue, :firstSuit, :secondSuit)";


    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StartHandDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public StartHand getByCards(Pair<Card> hand) {
        return jdbcTemplate.queryForObject(SELECT_BY_CARDS, cardsToSql(hand.getFirst(),hand.getSecond()), this);
    }

    public void addStartHand(Pair<Card> hand) {
        try {
            int update = jdbcTemplate.update(INSERT, cardsToSql(hand.getFirst(),hand.getSecond()));
        } finally {
            System.out.println("Hopefully inserted??");
        }
    }

    @Override
    public StartHand mapRow(ResultSet rs, int i) throws SQLException {
        int value = rs.getInt("holeOneValue");
        int valueTwo = rs.getInt("holeTwoValue");
        String suit = rs.getString("holeOneSuit");
        String suitTwo = rs.getString("holeTwoSuit");
        return new StartHand(
                rs.getInt("id"),
                Card.getCard(value, suit),
                Card.getCard(valueTwo, suitTwo)
        );
    }

    private static SqlParameterSource cardsToSql(Card c, Card c2) {
        return new MapSqlParameterSource()
                .addValue("firstValue", c.value)
                .addValue("secondValue", c2.value)
                .addValue("firstSuit", c.suit.name)
                .addValue("secondSuit", c2.suit.name);
    }
}
