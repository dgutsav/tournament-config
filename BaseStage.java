import java.util.List;
import java.util.Map;

public abstract class BaseStage {
    private String name;
    private List<String> teams;
    private boolean isFinal;
    protected Map<Integer, List<Match>> matches;
    protected boolean isComplete;
    private StageType type;

    public BaseStage(String name, StageType type, boolean isFinal, List<String> teams) {
        this.name = name;
        this.type = type;
        this.isFinal = isFinal;
        this.teams = teams;
        this.isComplete = false;
    }

    public abstract void scheduleFixtures();

    public boolean isComplete() {
        return this.isComplete;
    }

    public boolean isKnockout() {
        return this.type == StageType.KNOCKOUT;
    }

    public boolean isFinalStage() {
        return this.isFinal;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getTeams() {
        return this.teams;
    }

    public Map<Integer, List<Match>> getMatches() {
        return this.matches;
    }

    public abstract void simulate();

    public abstract List<String> getPrimaryProgressingTeams();

    public abstract List<String> getSecondaryProgressingTeams();

    public abstract List<String> getTertiaryProgressingTeams();
}
