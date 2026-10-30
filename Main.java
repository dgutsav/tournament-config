public class Main {
        public static void main(String args[]) {
                TournamentConfig cfg4 = new TournamentConfig("Asian Cup Basic", TournamentType.CONTINENTAL_FINALS,
                                new StageBuilds().addStage(new StageBuilder("Group Stage", StageType.GROUP).seeded()
                                                .teamsJoining(new TeamFilter().byContinent(Continents.ASIA).best(24))
                                                .groups(6).rounds(1)
                                                .primaryProgressing(12).secondaryProgressing(4))
                                                .addStage(new StageBuilder("Round of 16", StageType.KNOCKOUT)
                                                                .teamsProgressing(new TeamProgressingFilter(
                                                                                "Group Stage", 1))
                                                                .teamsProgressing(new TeamProgressingFilter(
                                                                                "Group Stage", 2)))
                                                .addStage(new StageBuilder("Quarter Final", StageType.KNOCKOUT)
                                                                .teamsAdvancing())
                                                .addStage(new StageBuilder("Semi Final", StageType.KNOCKOUT)
                                                                .teamsAdvancing())
                                                .addStage(new StageBuilder("Final", StageType.KNOCKOUT).isFinal()
                                                                .teamsAdvancing()));
                Tournament tmt4 = cfg4.configureTournament();
                tmt4.simulate();

                TournamentConfig cf5 = new TournamentConfig("Asian WC Qualifiers",
                                TournamentType.CONTINENTAL_QUALIFIERS,
                                new StageBuilds()
                                                .addStage(new StageBuilder("First Round", StageType.KNOCKOUT).seeded()
                                                                .teamsJoining(new TeamFilter()
                                                                                .byContinent(Continents.ASIA)
                                                                                .worst(20)))
                                                .addStage(new StageBuilder("Second Round", StageType.GROUP).groups(9)
                                                                .rounds(2).primaryProgressing(18).teamsAdvancing()
                                                                .teamsJoining(new TeamFilter()
                                                                                .byContinent(Continents.ASIA).best(26))
                                                                .seeded())
                                                .addStage(new StageBuilder("Third Round", StageType.GROUP).groups(3)
                                                                .rounds(2).teamsAdvancing().seeded()
                                                                .primaryProgressing(6).secondaryProgressing(6))
                                                .addStage(new StageBuilder("Fourth Round", StageType.GROUP).groups(2)
                                                                .rounds(1).seeded().primaryProgressing(2)
                                                                .secondaryProgressing(2).teamsProgressing(
                                                                                new TeamProgressingFilter("Third Round",
                                                                                                2)))
                                                .addStage(new StageBuilder("Fifth Round", StageType.KNOCKOUT)
                                                                .teamsAdvancing(2)));
                Tournament t5 = cf5.configureTournament();
                t5.simulate();

                TournamentConfig c7 = new TournamentConfig("Sudamericana WC Qualifiers",
                                TournamentType.CONTINENTAL_QUALIFIERS,
                                new StageBuilds().addStage(new StageBuilder("League Phase", StageType.LEAGUE)
                                                .teamsJoining(new TeamFilter().byContinent(Continents.SOUTH_AMERICA))
                                                .rounds(2).progressing(6, 1)));
                Tournament t7 = c7.configureTournament();
                t7.simulate();

                TournamentConfig c8 = new TournamentConfig("African WC Qualifiers",
                                TournamentType.CONTINENTAL_QUALIFIERS,
                                new StageBuilds().addStage(new StageBuilder("First Round", StageType.GROUP).groups(9)
                                                .teamsJoining(new TeamFilter().byContinent(Continents.AFRICA)).rounds(2)
                                                .progressing(9, 4, 0).seeded())
                                                .addStage(new StageBuilder("Second Round", StageType.KNOCKOUT)
                                                                .teamsAdvancing(2))
                                                .addStage(new StageBuilder("Second Round Final", StageType.KNOCKOUT)
                                                                .teamsAdvancing()));
                Tournament t8 = c8.configureTournament();
                t8.simulate();

                TournamentConfig euq = new TournamentConfig("Europe WCQ", TournamentType.CONTINENTAL_QUALIFIERS,
                                new StageBuilds().addStage(new StageBuilder("First Round", StageType.GROUP)
                                                .teamsJoining(new TeamFilter().byContinent(Continents.EUROPE))
                                                .groups(12).rounds(2).progressing(12, 12, 4)
                                                .seeded())
                                                .addStage(new StageBuilder("Second Round", StageType.KNOCKOUT)
                                                                .teamsProgressing(new TeamProgressingFilter(
                                                                                "First Round", 2))
                                                                .teamsProgressing("First Round", 3))
                                                .addStage(new StageBuilder("Third Round", StageType.KNOCKOUT)
                                                                .teamsAdvancing()));
                Tournament eutmt = euq.configureTournament();
                eutmt.simulate();

                TournamentConfig naq = new TournamentConfig("NA WCQ", TournamentType.CONTINENTAL_QUALIFIERS,
                                new StageBuilds().addStage(new StageBuilder("First Round", StageType.KNOCKOUT)
                                                .teamsJoining(new TeamFilter().byContinent(Continents.NORTH_AMERICA)
                                                                .worst(10))
                                                .seeded())
                                                .addStage(new StageBuilder("Second Round", StageType.GROUP).groups(6)
                                                                .rounds(2).progressing(12).seeded().teamsAdvancing()
                                                                .teamsJoining(new TeamFilter()
                                                                                .byContinent(Continents.NORTH_AMERICA)
                                                                                .best(25)))
                                                .addStage(new StageBuilder("Third Round", StageType.GROUP)
                                                                .teamsAdvancing().groups(3).rounds(2)
                                                                .progressing(6, 2).seeded()));
                Tournament naqt = naq.configureTournament();
                naqt.simulate();

                TournamentConfig oq = new TournamentConfig("OFC WCQ", TournamentType.CONTINENTAL_QUALIFIERS,
                                new StageBuilds().addStage(new StageBuilder("First Round", StageType.KNOCKOUT)
                                                .teamsJoining(new TeamFilter().byContinent(Continents.OCEANIA).worst(4))
                                                .seeded())
                                                .addStage(new StageBuilder("First Round Final", StageType.KNOCKOUT)
                                                                .teamsAdvancing())
                                                .addStage(new StageBuilder("Second Round", StageType.GROUP)
                                                                .teamsAdvancing().teamsJoining(new TeamFilter()
                                                                                .byContinent(Continents.OCEANIA)
                                                                                .best(7))
                                                                .groups(2).rounds(1).progressing(4))
                                                .addKnockoutStages("Third Round", "Third Round Final"));
                Tournament oqt = oq.configureTournament();
                oqt.simulate();

                TournamentConfig wcpl = new TournamentConfig("WC Playoff", TournamentType.CONTINENTAL_QUALIFIERS,
                                new StageBuilds().addStage(new StageBuilder("First Round", StageType.KNOCKOUT)
                                                .teamsQualifying(new TeamQualifierFilter("Sudamericana WC Qualifiers",
                                                                "League Phase", 2))
                                                .teamsQualifying(new TeamQualifierFilter("OFC WCQ", "Third Round Final",
                                                                2))
                                                .teamsQualifying(new TeamQualifierFilter("NA WCQ", "Third Round", 2)))
                                                .addStage(new StageBuilder("Second Round", StageType.KNOCKOUT)
                                                                .teamsAdvancing()
                                                                .teamsQualifying(new TeamQualifierFilter(
                                                                                "Asian WC Qualifiers", "Fifth Round",
                                                                                1))
                                                                .teamsQualifying(new TeamQualifierFilter(
                                                                                "African WC Qualifiers",
                                                                                "Second Round Final", 1))));
                Tournament wcplt = wcpl.configureTournament();
                wcplt.simulate();

                TournamentConfig c6 = new TournamentConfig("World Cup", TournamentType.WORLD_CUP,
                                new StageBuilds().addStage(new StageBuilder("Group Stage", StageType.GROUP)
                                                .teamsQualifying(new TeamQualifierFilter("Asian WC Qualifiers",
                                                                "Third Round", 1))
                                                .teamsQualifying(new TeamQualifierFilter("Asian WC Qualifiers",
                                                                "Fourth Round", 1))
                                                .teamsQualifying(new TeamQualifierFilter("Sudamericana WC Qualifiers",
                                                                "League Phase", 1))
                                                .teamsQualifying(new TeamQualifierFilter("Europe WCQ",
                                                                "First Round", 1))
                                                .teamsQualifying(new TeamQualifierFilter("African WC Qualifiers",
                                                                "First Round", 1))
                                                .teamsQualifying(new TeamQualifierFilter("OFC WCQ", "Third Round Final",
                                                                1))
                                                .teamsQualifying(
                                                                new TeamQualifierFilter("Europe WCQ", "Third Round", 1))
                                                .teamsQualifying(new TeamQualifierFilter("NA WCQ", "Third Round", 1))
                                                .teamsQualifying(new TeamQualifierFilter("WC Playoff", "Second Round",
                                                                1))
                                                .groups(12).rounds(1).progressing(24, 8))
                                                .addStage(new StageBuilder("Round of 32", StageType.KNOCKOUT)
                                                                .teamsAdvancing().teamsProgressing("Group Stage", 2))
                                                .addKnockoutStages("Round of 16", "Quarter Final", "Semi Final",
                                                                "Final"));
                Tournament t6 = c6.configureTournament();
                t6.simulate();

                TournamentConfig cfg7 = new TournamentConfig("World Cup", TournamentType.WORLD_CUP,
                                new StageBuilds().addStage(new StageBuilder("Group Stage", StageType.GROUP)
                                                .teamsQualifying(TeamQualifierFilter.from("Asian WC Qualifiers",
                                                                "Third Round"),
                                                                TeamQualifierFilter.from("Asian WC Qualifiers",
                                                                                "Fourth Round"),
                                                                TeamQualifierFilter.from("Sudamericana WC Qualifiers",
                                                                                "League Phase"),
                                                                TeamQualifierFilter.from("Europe WCQ",
                                                                                "First Round"),
                                                                TeamQualifierFilter.from("African WC Qualifiers",
                                                                                "First Round"),
                                                                TeamQualifierFilter.from("OFC WCQ",
                                                                                "Third Round Final"),
                                                                TeamQualifierFilter.from("Europe WCQ", "Third Round"),
                                                                TeamQualifierFilter.from("NA WCQ", "Third Round"),
                                                                TeamQualifierFilter.from("WC Playoff", "Second Round"))
                                                .groups(12).rounds(1).progressing(24, 8))
                                                .addStage(new StageBuilder("Round of 32", StageType.KNOCKOUT)
                                                                .teamsAdvancing().teamsProgressing("Group Stage", 2))
                                                .addKnockoutStages("Round of 16", "Quarter Final", "Semi Final",
                                                                "Final"));
                Tournament tmt7 = cfg7.configureTournament();
                tmt7.simulate();
        }
}
