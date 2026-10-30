import java.util.List;
import java.util.Map;

public class LeagueStage extends BaseStage {

    private FixtureHelper _fixHelper;
    private Simulator _sim;
    private int rounds;

    private int primary_progressing;
    private int secondary_progressing;
    private int tertiary_progressing;

    private LeagueTable table;

    public LeagueStage(String name, List<String> teams, int rounds, int pr1, int pr2, int pr3) {
        super(name, StageType.LEAGUE, false, teams);
        this.rounds = rounds;
        _fixHelper = FixtureHelper.getInstance();
        _sim = Simulator.getInstance();
        this.primary_progressing = pr1;
        this.secondary_progressing = pr2;
        this.tertiary_progressing = pr3;
        this.table = new LeagueTable(teams);
    }

    @Override
    public void scheduleFixtures() {
        if (rounds == 1)
            this.matches = _fixHelper.getSingleRoundRobinFixtures(this.getTeams());
        else if (rounds == 2)
            this.matches = _fixHelper.getDoubleRoundRobinFixtures(this.getTeams());
    }

    private int getNumMatchDays() {
        int num_matchdays = this.getTeams().size() % 2 == 0 ? this.getTeams().size() - 1 : this.getTeams().size();
        return num_matchdays * this.rounds;
    }

    @Override
    public void simulate() {
        System.out.println(this.getName());
        int mds = this.getNumMatchDays();
        for (int i = 1; i <= mds; i++) {
            System.out.println("Matchday " + i);
            List<Match> matchList = this.matches.get(i);
            for (int j = 0; j < matchList.size(); j++) {
                _sim.simulateMatch(this.matches.get(i).get(j));
                this.table.update(this.matches.get(i).get(j));
            }
        }
        this.showTable();
    }

    public void showTable() {
        this.table.show();
    }

    public Map<String, int[]> getTable() {
        return this.table.getTableMap();
    }

    public List<String> getTeamsRanked() {
        return this.table.getSortedTeams();
    }

    public List<String> getPrimaryProgressingTeams() {
        return this.getTeamsRanked().subList(0, this.primary_progressing);
    }

    public List<String> getSecondaryProgressingTeams() {
        return this.getTeamsRanked().subList(this.primary_progressing,
                this.primary_progressing + this.secondary_progressing);
    }

    public List<String> getTertiaryProgressingTeams() {
        return this.getTeamsRanked().subList(this.primary_progressing + this.secondary_progressing,
                this.primary_progressing + this.secondary_progressing + this.tertiary_progressing);
    }
}
