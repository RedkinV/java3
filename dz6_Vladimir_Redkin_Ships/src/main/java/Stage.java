public abstract class Stage {
    protected String description;
    public String getDescription() {
        return description;
    }
    public abstract void doIt(Ship ship);
}
