package com.worldcup.service;

import com.worldcup.model.Match;

import java.util.List;

public interface ScoreBoardService {
    void startMatch(String homeTeam, String awayTeam);
    void finishMatch(String homeTeam, String awayTeam);
    void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore);
    List<Match> getSummary();
}
