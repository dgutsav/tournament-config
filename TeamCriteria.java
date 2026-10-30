import java.util.ArrayList;
import java.util.List;

public class TeamCriteria {
    private List<TeamFilter> teamsJoiningFilters;
    private List<TeamProgressingFilter> teamsProgressingFilters;
    private List<TeamQualifierFilter> teamsQualifierFilters;

    private DataHelper _dataHelper;

    public TeamCriteria() {
        this.teamsJoiningFilters = new ArrayList<>();
        this.teamsProgressingFilters = new ArrayList<>();
        this.teamsQualifierFilters = new ArrayList<>();
        this._dataHelper = DataHelper.getInstance();
    }

    public TeamCriteria teamsJoining(TeamFilter filter) {
        this.teamsJoiningFilters.add(filter);
        return this;
    }

    public TeamCriteria teamsProgressing(TeamProgressingFilter filter) {
        this.teamsProgressingFilters.add(filter);
        return this;
    }

    public TeamCriteria teamsQualifying(TeamQualifierFilter filter) {
        this.teamsQualifierFilters.add(filter);
        return this;
    }

    public List<String> apply(Tournament tmt) {
        List<Team> teamList = new ArrayList<>(_dataHelper.getTeams().values());
        List<String> teams = new ArrayList<>();
        for (TeamFilter tf : teamsJoiningFilters) {
            teams.addAll(tf.applyFilter(teamList));
        }
        for (TeamProgressingFilter tpf : teamsProgressingFilters) {
            teams.addAll(tpf.apply(tmt));
        }
        for (TeamQualifierFilter tqf : teamsQualifierFilters) {
            teams.addAll(tqf.apply(_dataHelper));
        }
        return teams;
    }
}
