package com.worldcup.repository;

import com.worldcup.model.Match;

import java.util.List;
import java.util.Optional;

public interface MatchRepository {
    void save(Match match);

    void save(List<Match> matches);

    void delete(Match match);

    Optional<Match> findByTeams(String homeTeam, String awayTeam);

    List<Match> findAll();
}
