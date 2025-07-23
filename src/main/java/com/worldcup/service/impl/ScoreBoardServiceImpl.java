package com.worldcup.service.impl;

import com.worldcup.exception.MatchNotFoundException;
import com.worldcup.model.Match;
import com.worldcup.repository.MatchRepository;
import com.worldcup.service.ScoreBoardService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreBoardServiceImpl implements ScoreBoardService {
    private final MatchRepository matchRepository;

    public ScoreBoardServiceImpl(MatchRepository repository) {
        this.matchRepository = repository;
    }

    @Override
    public void startMatch(String homeTeam, String awayTeam) {
        boolean isTeamAlreadyPlaying = matchRepository.findAll().stream()
                .anyMatch(match ->
                        match.getHomeTeam().equalsIgnoreCase(homeTeam) ||
                                match.getAwayTeam().equalsIgnoreCase(homeTeam) ||
                                match.getHomeTeam().equalsIgnoreCase(awayTeam) ||
                                match.getAwayTeam().equalsIgnoreCase(awayTeam)
                );

        if (isTeamAlreadyPlaying) {
            throw new IllegalArgumentException("One or both teams are already playing");
        }
        Match match = new Match(homeTeam, awayTeam);
        matchRepository.save(match);
    }

    @Override
    public void finishMatch(String homeTeam, String awayTeam) {
        Match match = matchRepository.findByTeams(homeTeam, awayTeam)
                .orElseThrow(() -> new MatchNotFoundException("Match not found or already finished"));
        matchRepository.delete(match);
    }

    @Override
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        Match match = matchRepository.findByTeams(homeTeam, awayTeam)
                .orElseThrow(() -> new MatchNotFoundException("Match not found or already finished"));
        match.updateScore(homeScore, awayScore);
    }

    @Override
    public List<Match> getSummary() {
        return matchRepository.findAll().stream()
                .sorted(Comparator
                        .comparingInt(Match::totalScore).reversed()
                        .thenComparing(Match::getLastUpdated).reversed())
                .collect(Collectors.toList());
    }
}
