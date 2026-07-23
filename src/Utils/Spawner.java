package Utils;
    
public class Spawner {
    private String entity; // Inimigo, Chefe ou Power-Up?
    private int type; // Qual tipo?
    private int hp; // Quantos pontos de vida tem o chefe?
    private long when; // Em que momento ele deve aparecer?
    private double x; // posicao x inicial
    private double y; // posicao y inicial

    public Spawner (String entity, int type, int hp, long when, double x, double y) {
        this.entity = entity;
        this.type = type;
        this.hp = hp;
        this.when = when;
        this.x = x;
        this.y = y;
    }

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
    public double getY() { return y; } // Pode chamar de getY()
}