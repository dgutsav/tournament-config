class Nation {
    public String name;
    public String code;
    public int points;
    public Continents continent;

    public Nation(String name, String code, int points, Continents continent) {
        this.name = name;
        this.code = code;
        this.points = points;
        this.continent = continent;
    }
}