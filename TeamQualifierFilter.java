import java.util.List;
import java.util.ArrayList;

public class TeamQualifierFilter {
    private String tournament_name;
    private String stage_name;
    private int type_progressing;

    public TeamQualifierFilter(String tmt_name, String stage_name, int type) {
        this.stage_name = stage_name;
        this.type_progressing = type;
        this.tournament_name = tmt_name;
    }

    public List<String> apply(DataHelper _dataHelper) {
        Tournament tmt = _dataHelper.getTournament(this.tournament_name);
        List<String> team_ids = new ArrayList<>();
        for (BaseStage stage : tmt.getStages()) {
            if (stage.getName() == this.stage_name) {
                if (this.type_progressing == 1) {
                    team_ids = stage.getPrimaryProgressingTeams();
                } else if (this.type_progressing == 2) {
                    team_ids = stage.getSecondaryProgressingTeams();
                } else {
                    team_ids = stage.getTertiaryProgressingTeams();
                }
            }
        }
        return team_ids;
    }

    public static TeamQualifierFilter from(String tournament_name, String stage_name, int type) {
        return new TeamQualifierFilter(tournament_name, stage_name, type);
    }

    public static TeamQualifierFilter from(String tournament_name, String stage_name) {
        return from(tournament_name, stage_name, 1);
    }
}
