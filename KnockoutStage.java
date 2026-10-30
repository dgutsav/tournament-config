import java.util.ArrayList;
import java.util.List;

public class KnockoutStage extends BaseStage {

    private FixtureHelper _fixHelper;
    private Simulator _sim;
    private boolean is_seeded;

    private static final int MATCHDAY = 1;

    public KnockoutStage(String name, boolean isFinal, List<String> teams, boolean seeded) {
        super(name, StageType.KNOCKOUT, isFinal, teams);
        _fixHelper = FixtureHelper.getInstance();
        _sim = Simulator.getInstance();
        this.is_seeded = seeded;
    }

    @Override
    public void scheduleFixtures() {
        this.matches = _fixHelper.getKnockoutFixtures(this.getTeams(), this.is_seeded);
    }

    @Override
    public void simulate() {
        System.out.println(this.getName());
        List<Match> matchList = this.matches.get(MATCHDAY);
        for (int i = 0; i < matchList.size(); i++) {
            _sim.simulateMatch(matchList.get(i));
        }
    }

    public List<String> getPrimaryProgressingTeams() {
        List<Match> matchList = this.matches.get(MATCHDAY);
        List<String> progressing = new ArrayList<>();
        for (int i = 0; i < matchList.size(); i++) {
            progressing.add(matchList.get(i).getWinner());
        }
        return progressing;
    }

    public List<String> getSecondaryProgressingTeams() {
        return this.getLosingTeams();
    }

    public List<String> getTertiaryProgressingTeams() {
        return new ArrayList<>();
    }

    public List<String> getLosingTeams() {
        List<Match> matchList = this.matches.get(MATCHDAY);
        List<String> losing = new ArrayList<>();
        for (int i = 0; i < matchList.size(); i++) {
            losing.add(matchList.get(i).getLoser());
        }
        return losing;
    }
}
