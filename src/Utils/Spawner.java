package Utils;
    
public class Spawner {
    
    private String entity;
    private int type;
    private int hp;
    private long when;
    private double x;
    private double y;

    // Construtor para bosses
    public Spawner (String entity, int type, int hp, long when, double x, double y) {
        this.entity = entity;
        this.type = type;
        this.hp = hp;
        this.when = when;
        this.x = x;
        this.y = y;
    }

    // Construtor alternativo para inimigos e power-ups que não possuem hp
    public Spawner (String entity, int type, long when, double x, double y) {
        this.entity = entity;
        this.type = type;
        this.hp = 0;
        this.when = when;
        this.x = x;
        this.y = y;
    }

    public String getEntity() { return entity; }
    public int getType() { return type; }
    public int getHp() { return hp; }
    public long getSpawnTime() { return when; }
    public double getX() { return x; }
    public double getY() { return y; }
}