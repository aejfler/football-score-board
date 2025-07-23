package repository;

import com.worldcup.model.Match;
import com.worldcup.repository.MatchRepository;
import com.worldcup.repository.impl.MatchRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;

public class MatchRepositoryTest {
    private MatchRepository matchRepository;
    private static final String HOME_TEAM = "Spain";
    private static final String AWAY_TEAM = "Italy";

    @BeforeEach
    void setUp() {
        matchRepository = new MatchRepositoryImpl();
    }

    @Test
    void save_shouldSaveMatch() {
        Match match = new Match(HOME_TEAM, AWAY_TEAM);
        matchRepository.save(match);

        Optional<Match> savedMatch = matchRepository.findByTeams(HOME_TEAM, AWAY_TEAM);

        assertThat(savedMatch).isPresent();
    }

    @Test
    void save_shouldThrowException_whenTeamIsEmpty() {
        Match match = new Match(HOME_TEAM, "");
        assertThatThrownBy(() -> matchRepository.save(match))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("awayTeam cannot be null or blank");
    }

    @Test
    void save_shouldThrowException_whenTeamIsPlayingAgainstItself() {
        Match match = new Match(HOME_TEAM, HOME_TEAM);

        assertThatThrownBy(() -> matchRepository.save(match))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("A team cannot play against itself");
    }

    @Test
    void delete_shouldDeleteMatch_whenMatchIsPresent() {
        Match match = new Match(HOME_TEAM, AWAY_TEAM);
        matchRepository.save(match);

        matchRepository.delete(match);

        Optional<Match> savedMatch = matchRepository.findByTeams(HOME_TEAM, AWAY_TEAM);
        assertThat(savedMatch).isNotPresent();
    }

    @Test
    void delete_shouldThrowException_whenMatchIsNotFound() {
        Match match = new Match("non existing team", "ghost team");

        assertThatThrownBy(() -> matchRepository.delete(match))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Match not found");
    }

    @Test
    void delete_shouldThrowException_whenMatchIsNull() {
        assertThatThrownBy(() -> matchRepository.delete(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Match cannot be null");
    }

    @Test
    void findAll_shouldReturnAllPresentMatches() {
        Match firstMatch = new Match(HOME_TEAM, AWAY_TEAM);
        Match secondMatch = new Match("Netherlands", "Germany");
        List<Match> matches = List.of(firstMatch, secondMatch);
        matchRepository.save(matches);

        List<Match> foundMatches = matchRepository.findAll();

        assertThat(foundMatches).hasSize(matches.size());
    }

    @Test
    void findByTeams_shouldReturnMatch_whenMatchExists() {
        Match match = new Match(HOME_TEAM, AWAY_TEAM);
        matchRepository.save(match);

        Optional<Match> foundMatch = matchRepository.findByTeams(HOME_TEAM, AWAY_TEAM);

        assertThat(foundMatch).isPresent();
        assertThat(foundMatch.get()).isEqualTo(match);
    }

    @Test
    void findByTeams_shouldReturnEmpty_whenMatchDoesNotExist() {
        Optional<Match> foundMatch = matchRepository.findByTeams("NonExistingTeam", "AnotherNonExistingTeam");

        assertThat(foundMatch).isNotPresent();
    }

    @Test
    void findByTeams_shouldThrowException_whenTeamIsNull() {
        assertThatThrownBy(() -> matchRepository.findByTeams(null, AWAY_TEAM))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("homeTeam");
    }

    @Test
    void findByTeams_shouldThrowException_whenTeamsAreTheSame() {
        assertThatThrownBy(() -> matchRepository.findByTeams(HOME_TEAM, HOME_TEAM))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("A team cannot play against itself");
    }
}
