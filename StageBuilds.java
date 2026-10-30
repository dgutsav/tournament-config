import java.util.List;
import java.util.ArrayList;

public class StageBuilds {
    List<StageBuilder> stage_builds;

    public StageBuilds() {
        this.stage_builds = new ArrayList<>();
    }

    public StageBuilds addStage(StageBuilder builder) {
        this.stage_builds.add(builder);
        return this;
    }

    public StageBuilder getStage(int stage) {
        return this.stage_builds.get(stage);
    }

    public StageBuilds knockoutBracket(TeamFilter filter, String stages[]) {
        for (int i = 0; i < stages.length; i++) {
            if (i == 0) {
                this.addStage(new StageBuilder(stages[i], StageType.KNOCKOUT).teamsJoining(filter));
            } else {
                this.addStage(new StageBuilder(stages[i], StageType.KNOCKOUT).teamsAdvancing());
            }
        }
        return this;
    }

    public StageBuilds addKnockoutStages(String... stages) {
        for (int i = 0; i < stages.length; i++) {
            this.addStage(new StageBuilder(stages[i], StageType.KNOCKOUT).teamsAdvancing());
        }
        return this;
    }
}