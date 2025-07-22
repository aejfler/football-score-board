package com.worldcup.service.impl;

import com.worldcup.exception.MatchNotFoundException;
import com.worldcup.model.Match;
import com.worldcup.repository.MatchRepository;
import com.worldcup.service.ScoreBoardService;

import java.util.List;

public class ScoreBoardServiceImpl implements ScoreBoardService {
    private final MatchRepository matchRepository;

    public ScoreBoardServiceImpl(MatchRepository repository) {
        this.matchRepository = repository;
    }

    @Override
    public void startMatch(String homeTeam, String awayTeam) {
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
        return List.of();
    }
}
