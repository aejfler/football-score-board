package com.worldcup.repository.impl;

import com.worldcup.exception.MatchNotFoundException;
import com.worldcup.model.Match;
import com.worldcup.repository.MatchRepository;
import com.worldcup.util.Validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatchRepositoryImpl implements MatchRepository {
    private final List<Match> inMemoryMatches = new ArrayList<>();

    @Override
    public void save(Match match) {
        match.validate();
        if (!inMemoryMatches.contains(match)) {
            inMemoryMatches.add(match);
        }
    }

    @Override
    public void save(List<Match> matches) {
        matches.forEach(Match::validate);
        for (Match match : matches) {
            if (!inMemoryMatches.contains(match)) {
                inMemoryMatches.add(match);
            }
        }
    }

    @Override
    public void delete(Match match) {
        if (!inMemoryMatches.remove(match) || match == null) {
            throw new MatchNotFoundException();
        }
    }

    @Override
    public Optional<Match> findByTeams(String homeTeam, String awayTeam) {
        Validators.validateTeamName(homeTeam);
        Validators.validateTeamName(awayTeam);
        Validators.validateTeamsAreDifferent(homeTeam, awayTeam);
        return inMemoryMatches.stream()
                .filter(g -> g.getHomeTeam().equals(homeTeam) && g.getAwayTeam().equals(awayTeam))
                .findFirst();
    }

    @Override
    public List<Match> findAll() {
        return new ArrayList<>(inMemoryMatches);
    }
}
