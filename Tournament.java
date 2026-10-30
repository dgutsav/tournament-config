import java.util.ArrayList;
import java.util.List;

public class Tournament {
    private String name;
    private TournamentType type;
    private StageBuilds stage_def;
    private List<String> teams;
    private List<BaseStage> stages;
    private int stages_simulated;

    public Tournament(String name, TournamentType type, StageBuilds builds) {
        this.name = name;
        this.type = type;
        this.stage_def = builds;
        this.teams = new ArrayList<>();
        this.stages = new ArrayList<>();
        this.stages_simulated = 0;
    }

    public boolean isQualifier() {
        return this.type == TournamentType.CONTINENTAL_QUALIFIERS;
    }

    public void setupNextStage() {
        StageBuilder build = this.stage_def.getStage(stages_simulated);
        BaseStage stage = build.build(this);
        stage.scheduleFixtures();
        log("Setting up stage: " + stage.getName() + " " + stage.getTeams().size());
        for (String team : stage.getTeams()) {
            if (!this.teams.contains(team))
                this.teams.add(team);
        }
        this.stages.add(stage);
    }

    public void simulateNextStage() {
        int len = this.stages.size();
        log(" Simulating " + len);
        this.stages.get(len - 1).simulate();
        this.stages_simulated++;
    }

    public void simulate() {
        for (int i = 0; i < stage_def.stage_builds.size(); i++) {
            setupNextStage();
            simulateNextStage();
        }
    }

    public String getName() {
        return this.name;
    }

    private void log(String x) {
        System.out.println(x);
    }

    public List<BaseStage> getStages() {
        return this.stages;
    }
}