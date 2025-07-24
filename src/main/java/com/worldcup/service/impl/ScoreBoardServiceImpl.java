package com.worldcup.service.impl;

import com.worldcup.exception.MatchNotFoundException;
import com.worldcup.exception.TeamAlreadyPlayingException;
import com.worldcup.model.Match;
import com.worldcup.repository.MatchRepository;
import com.worldcup.service.ScoreBoardService;

import java.util.Comparator;
import java.util.List;

public class ScoreBoardServiceImpl implements ScoreBoardService {
    private final MatchRepository matchRepository;

    public ScoreBoardServiceImpl(MatchRepository repository) {
        this.matchRepository = repository;
    }

    @Override
    public void startMatch(String homeTeam, String awayTeam) {
        if (isTeamAlreadyPlaying(homeTeam, awayTeam)) {
            throw new TeamAlreadyPlayingException();
        }
        Match match = new Match(homeTeam, awayTeam);
        matchRepository.save(match);
    }

    @Override
    public void finishMatch(String homeTeam, String awayTeam) {
        Match match = matchRepository.findByTeams(homeTeam, awayTeam)
                .orElseThrow(MatchNotFoundException::new);
        matchRepository.delete(match);
    }

    @Override
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        Match match = matchRepository.findByTeams(homeTeam, awayTeam)
                .orElseThrow(MatchNotFoundException::new);
        match.updateScore(homeScore, awayScore);
    }

    @Override
    public List<Match> getSummary() {
        return matchRepository.findAll().stream()
                .sorted(Comparator
                        .comparing(Match::getTotalScore, Comparator.reverseOrder())
                        .thenComparing(Match::getLastUpdated, Comparator.reverseOrder()))
                .toList();
    }

    private boolean isTeamAlreadyPlaying(String homeTeam, String awayTeam) {
        return matchRepository.findAll().stream()
                .anyMatch(match ->
                        match.getHomeTeam().equalsIgnoreCase(homeTeam) ||
                                match.getAwayTeam().equalsIgnoreCase(homeTeam) ||
                                match.getHomeTeam().equalsIgnoreCase(awayTeam) ||
                                match.getAwayTeam().equalsIgnoreCase(awayTeam)
                );
    }
}
