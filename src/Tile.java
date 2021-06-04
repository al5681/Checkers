public class Tile {

    private boolean usable;

    public Tile(boolean usable)
    {
        this.usable = usable;
    }
    public Tile()
    {

    }

    public boolean isUsable() {
        return usable;
    }

    public void setUsable(boolean usable) {
        this.usable = usable;
    }

    @Override
    public String toString() {
        return " | " + usable + " | ";
    }
}
