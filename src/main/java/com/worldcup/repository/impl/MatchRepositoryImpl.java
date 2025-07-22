package com.worldcup.repository.impl;

import com.worldcup.model.Match;
import com.worldcup.repository.MatchRepository;
import com.worldcup.utils.Validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatchRepositoryImpl implements MatchRepository {
    private final List<Match> inMemoryMatches = new ArrayList<>();

    @Override
    public void save(Match match) {
        validateMatch(match);
        inMemoryMatches.add(match);
    }

    @Override
    public void save(List<Match> matches) {
        for (Match match : matches) {
            validateMatch(match);
        }
        inMemoryMatches.addAll(matches);
    }

    @Override
    public void delete(Match match) {
        boolean removed = inMemoryMatches.remove(match);
        if (!removed) {
            throw new IllegalArgumentException("Match not found");
        }
    }

    @Override
    public Optional<Match> findByTeams(String homeTeam, String awayTeam) {
        return inMemoryMatches.stream()
                .filter(g -> g.getHomeTeam().equals(homeTeam) && g.getAwayTeam().equals(awayTeam))
                .findFirst();
    }

    @Override
    public List<Match> findAll() {
        return new ArrayList<>(inMemoryMatches);
    }

    private void validateMatch(Match match) {
        Validators.validateTeamName(match.getHomeTeam(), "homeTeam");
        Validators.validateTeamName(match.getAwayTeam(), "awayTeam");
        Validators.validateTeamsAreDifferent(match.getHomeTeam(), match.getAwayTeam());
    }
}
