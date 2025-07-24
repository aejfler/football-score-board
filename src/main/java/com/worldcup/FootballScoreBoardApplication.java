package com.worldcup;

import com.worldcup.repository.impl.MatchRepositoryImpl;
import com.worldcup.service.ScoreBoardService;
import com.worldcup.service.impl.ScoreBoardServiceImpl;


public class FootballScoreBoardApplication {
    public static void main(String[] args) {
        System.out.println("Football Score Board");
        ScoreBoardService scoreBoard = new ScoreBoardServiceImpl(new MatchRepositoryImpl());

        scoreBoard.startMatch("England", "Poland");
        scoreBoard.startMatch("USA", "Australia");

        scoreBoard.updateScore("England", "Poland", 1, 0);
        scoreBoard.updateScore("USA", "Australia", 3, 0);
        scoreBoard.getSummary().forEach(System.out::println);

        scoreBoard.finishMatch("England", "Poland");
        scoreBoard.getSummary().forEach(System.out::println);

    }
}
