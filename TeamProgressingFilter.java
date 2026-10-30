import java.util.ArrayList;
import java.util.List;

public class TeamProgressingFilter {
    private String stage_name;
    private int type_progressing;

    public TeamProgressingFilter(String stage_name, int type) {
        this.stage_name = stage_name;
        this.type_progressing = type;
    }

    public List<String> apply(Tournament tmt) {
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

    public static TeamProgressingFilter from(String stage_name, int type) {
        return new TeamProgressingFilter(stage_name, type);
    }
}