package service;

import com.worldcup.exception.MatchNotFoundException;
import com.worldcup.exception.NegativeScoreException;
import com.worldcup.exception.SelfMatchException;
import com.worldcup.exception.TeamAlreadyPlayingException;
import com.worldcup.model.Match;
import com.worldcup.repository.impl.MatchRepositoryImpl;
import com.worldcup.service.ScoreBoardService;
import com.worldcup.service.impl.ScoreBoardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ScoreBoardServiceImplTest {

    private ScoreBoardService service;
    private static final String HOME_TEAM = "Spain";
    private static final String AWAY_TEAM = "Italy";

    @BeforeEach
    void setUp() {
        service = new ScoreBoardServiceImpl(new MatchRepositoryImpl());
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

        assertThat(matches.getFirst()).extracting(Match::getHomeScore, Match::getAwayScore).containsExactly(0, 0);
    }

    @Test
    void startMatch_shouldThrowException_whenTeamIsAlreadyInMatch() {
        service.startMatch(HOME_TEAM, AWAY_TEAM);

        assertThatThrownBy(() -> service.startMatch(HOME_TEAM, "Germany"))
                .isInstanceOf(TeamAlreadyPlayingException.class)
                .hasMessageContaining("One or both teams are already playing");
    }

    @Test
    void startMatch_shouldThrowException_whenTeamIsPlayingWithItself() {
        assertThatThrownBy(() -> service.startMatch(HOME_TEAM, HOME_TEAM))
                .isInstanceOf(SelfMatchException.class)
                .hasMessageContaining("A team cannot play against itself");
    }

    @Test
    void updateScore_shouldModifyTeamScore_whenScoresAreValid() {
        service.startMatch(HOME_TEAM, AWAY_TEAM);

        service.updateScore(HOME_TEAM, AWAY_TEAM, 2, 3);
        List<Match> matches = service.getSummary();

        assertThat(matches.getFirst()).extracting(Match::getHomeScore, Match::getAwayScore).containsExactly(2, 3);
    }

    @Test
    void finishMatch_shouldRemoveMatchFromScoreBoard() {
        service.startMatch(HOME_TEAM, AWAY_TEAM);

        service.finishMatch(HOME_TEAM, AWAY_TEAM);

        assertThat(service.getSummary().size()).isEqualTo(0);
    }

    @Test
    void finishMatch_shouldThrowException_whenNoMatchFound() {
        service.startMatch(HOME_TEAM, AWAY_TEAM);

        service.finishMatch(HOME_TEAM, AWAY_TEAM);

        assertThat(service.getSummary().size()).isEqualTo(0);
    }

    @Test
    void getSummary_shouldReturnEmptyList_whenNoMatchesArePresent() {
        List<Match> summary = service.getSummary();

        assertThat(summary).isEmpty();
    }

    @Test
    void getSummary_shouldReturnMatchesSortedByTotalScoreAndLastUpdated() {
        service.startMatch(HOME_TEAM, AWAY_TEAM);
        service.updateScore(HOME_TEAM, AWAY_TEAM, 2, 3);

        service.startMatch("USA", "England");
        service.updateScore("USA", "England", 2, 3);

        service.startMatch("Germany", "France");
        service.updateScore("Germany", "France", 1, 1);

        service.startMatch("Brazil", "Argentina");
        service.updateScore("Brazil", "Argentina", 3, 3);

        List<Match> summary = service.getSummary();

        assertThat(summary).hasSize(4);
        assertThat(summary.get(0)).extracting(Match::getHomeTeam, Match::getAwayTeam).containsExactly("Brazil", "Argentina");
        assertThat(summary.get(1)).extracting(Match::getHomeTeam, Match::getAwayTeam).containsExactly("USA", "England");
        assertThat(summary.get(2)).extracting(Match::getHomeTeam, Match::getAwayTeam).containsExactly(HOME_TEAM, AWAY_TEAM);
        assertThat(summary.get(3)).extracting(Match::getHomeTeam, Match::getAwayTeam).containsExactly("Germany", "France");
    }
}
