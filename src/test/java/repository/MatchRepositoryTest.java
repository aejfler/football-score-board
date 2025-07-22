package repository;

import com.worldcup.model.Match;
import com.worldcup.repository.MatchRepository;
import com.worldcup.repository.impl.MatchRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void save_shouldThrowException_whenTeamsAreNotValid() {
        Match match = new Match(HOME_TEAM, "");

        assertThrows(Exception.class, () -> matchRepository.save(match));
    }

    @Test
    void save_shouldThrowException_whenTeamIsPlayingAgainstItself() {
        Match match = new Match(HOME_TEAM, HOME_TEAM);

        assertThrows(Exception.class, () -> matchRepository.save(match));
    }

    @Test
    void delete_shouldDeleteMatch() {
        Match match = new Match(HOME_TEAM, AWAY_TEAM);
        matchRepository.save(match);

        matchRepository.delete(match);

        Optional<Match> savedMatch = matchRepository.findByTeams(HOME_TEAM, AWAY_TEAM);
        assertThat(savedMatch).isNotPresent();
    }

    @Test
    void delete_shouldThrowException_whenMatchIsNotFound() {
        Match match = new Match("non existing team", "ghost team");
        assertThrows(Exception.class, () -> matchRepository.delete(match));
    }

    @Test
    void findAll_shouldReturnAllPresentMatches() {
        Match firstMatch = new Match(HOME_TEAM, AWAY_TEAM);
        Match secondMatch = new Match("Netherlands", "Germany");
        matchRepository.save(List.of(firstMatch, secondMatch));

        List<Match> matches = matchRepository.findAll();

        assertThat(matches).hasSize(2);
    }
}
