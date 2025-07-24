package com.worldcup.repository.impl;

import com.worldcup.exception.MatchNotFoundException;
import com.worldcup.model.Match;
import com.worldcup.repository.MatchRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatchRepositoryImpl implements MatchRepository {
    private final List<Match> inMemoryMatches = new ArrayList<>();

    @Override
    public void save(Match match) {
        match.validate();
        inMemoryMatches.add(match);
    }

    @Override
    public void save(List<Match> matches) {
        matches.forEach(Match::validate);
        inMemoryMatches.addAll(matches);
    }

    @Override
    public void delete(Match match) {
        if (!inMemoryMatches.remove(match) || match == null) {
            throw new MatchNotFoundException();
        }
    }

    @Override
    public Optional<Match> findByTeams(String homeTeam, String awayTeam) {
        Match match = new Match(homeTeam, awayTeam);
        match.validate();
        return inMemoryMatches.stream()
                .filter(g -> g.getHomeTeam().equals(homeTeam) && g.getAwayTeam().equals(awayTeam))
                .findFirst();
    }

    @Override
    public List<Match> findAll() {
        return new ArrayList<>(inMemoryMatches);
    }
}
