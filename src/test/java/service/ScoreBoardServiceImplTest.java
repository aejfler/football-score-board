package service;

import com.worldcup.model.Match;
import com.worldcup.service.ScoreBoardService;
import com.worldcup.service.impl.ScoreBoardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScoreBoardServiceImplTest {

    private ScoreBoardService service;
    private static final String HOME_TEAM = "Spain"; 
    private static final String AWAY_TEAM = "Italy";

    @BeforeEach
    void setUp() {
        service = new ScoreBoardServiceImpl();
    }

    @Test
    void startMatch_shouldAddMatchToScoreBoard_whenTeamsAreProvided() {
        service.startMatch(HOME_TEAM, AWAY_TEAM);

        List<Match> matches = service.getSummary();

        assertThat(matches).hasSize(1);
        assertThat(matches.getFirst()).extracting(Match::getHomeTeam, Match::getAwayTeam).containsExactly(HOME_TEAM, AWAY_TEAM);
    }

    @Test
    void startMatch_shouldInitializeScoresToZero() {
        service.startMatch(HOME_TEAM, AWAY_TEAM);
        List<Match> matches = service.getSummary();

        assertThat(matches.getFirst()).extracting(Match::getHomeScore, Match::getAwayScore).containsExactly(0,0);
    }

    @Test
    void startMatch_shouldThrowException_whenTeamIsAlreadyInMatch() {
        service.startMatch(HOME_TEAM, AWAY_TEAM);

        assertThrows(Exception.class, () -> service.startMatch(HOME_TEAM, "Germany"));
    }

    @Test
    void startMatch_shouldThrowException_whenTeamIsPlayingWithItself() {
        assertThrows(Exception.class, () -> service.startMatch(HOME_TEAM, HOME_TEAM));
    }

    @Test
    void updateScore_shouldModifyTeamScore() {
        service.startMatch(HOME_TEAM, AWAY_TEAM);
        service.updateScore(HOME_TEAM, AWAY_TEAM, 2, 3);

        List<Match> matches = service.getSummary();

        assertThat(matches.getFirst()).extracting(Match::getHomeScore, Match::getAwayScore).containsExactly(2,3);
    }

    @Test
    void finishMatch_shouldRemoveMatchFromScoreBoard() {
        service.startMatch(HOME_TEAM, AWAY_TEAM);
        service.finishMatch(HOME_TEAM, AWAY_TEAM);

        assertEquals(0, service.getSummary().size());
    }

    @Test
    void finishMatch_shouldThrowException_whenNoMatchFound() {
        service.startMatch(HOME_TEAM, AWAY_TEAM);
        service.finishMatch(HOME_TEAM, AWAY_TEAM);

        assertEquals(0, service.getSummary().size());
    }

    @Test
    void updateScore_shouldThrowException_whenMatchNotFound() {
        assertThrows(Exception.class, () ->
                service.updateScore(HOME_TEAM, AWAY_TEAM, 1, 1));
    }

    @Test
    void updateScore_shouldThrowExceptionWhenMatchIsFinished() {
        service.startMatch(HOME_TEAM, AWAY_TEAM);
        service.updateScore(HOME_TEAM, AWAY_TEAM, 2, 3);
        service.finishMatch(HOME_TEAM, AWAY_TEAM);    
        assertThrows(Exception.class, () ->
                service.updateScore(HOME_TEAM, AWAY_TEAM, 1, 1));
    }
}
