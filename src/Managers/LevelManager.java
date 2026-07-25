package Managers;

import Utils.GameLib;
import Utils.Spawner;
import Entities.ShieldPowerUp;
import Entities.TripleShotPowerUp;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.Color;

public class LevelManager {
    
    private int startHP;
    private int numberOfLevels;
    private List<String> levelFiles;
    private int currentLevel;
    private List<Spawner> spawners;
    private long levelStartTime;
    private boolean victory;

    public LevelManager(String configFile, long currentTime) {
        this.levelFiles = new ArrayList<>();
        this.spawners = new ArrayList<>();
        this.currentLevel = 0; 
        this.victory = false;
        
        readConfigFile(configFile);
        loadLevel(this.currentLevel, currentTime);
    }

    // O código pode ser executado na raiz do projeto ou dentro dos diretórios "src" ou "bin"
    private File filePath(String relativePath) {
        Path path = Path.of(relativePath);
        
        // Se o arquivo não existir no diretório atual, volta uma pasta (../)
        if (!Files.exists(path)) {
            path = Path.of("../", relativePath);
        }
        
        return path.toFile();
    }

    private void readConfigFile(String path) {
        try {
            File file = filePath(path);
            Scanner scanner = new Scanner(file);
            
            this.startHP = scanner.nextInt(); 
            this.numberOfLevels = scanner.nextInt();      
            scanner.nextLine(); 
            
            for (int i = 0; i < this.numberOfLevels; i++) {
                this.levelFiles.add(scanner.nextLine().trim());
            }
            
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erro: Arquivo de configuração não encontrado: " + path);
            System.exit(1);
        }
    }

    // Carrega a fase escolhida ao traduzir o arquivo .txt em entidades, levando em consideração o tempo de spawn de cada entidade
    private void loadLevel(int levelNumber, long currentTime) {
        this.spawners.clear(); 
        this.levelStartTime = currentTime; 

        String levelPath = "Levels/" + this.levelFiles.get(levelNumber);

        try { 
            File file = filePath(levelPath);
            Scanner scanner = new Scanner(file);
            

            while (scanner.hasNext()) {
                String entity = scanner.next(); 
                
                if (entity.equals("CHEFE")) {
                    int type = scanner.nextInt();
                    int hp = scanner.nextInt();
                    long when = scanner.nextLong();
                    double x = scanner.nextDouble();
                    double y = scanner.nextDouble();
                    
                    this.spawners.add(new Spawner(entity, type, hp, when, x, y));
                } else { 
                    int type = scanner.nextInt();
                    long when = scanner.nextLong();
                    double x = scanner.nextDouble();
                    double y = scanner.nextDouble();
                    
                    this.spawners.add(new Spawner(entity, type, when, x, y));
                }
            }
            
            scanner.close();

            // Ordena os surgimentos do mais cedo ao mais tarde 
            this.spawners.sort((s1, s2) -> Long.compare(s1.getSpawnTime(), s2.getSpawnTime()));
            
        } catch (FileNotFoundException e) {
            System.out.println("Erro: Arquivo da fase não encontrado: " + levelPath);
            System.exit(1);
        }
    }

    public void update(long currentTime, EnemyManager enemyManager, PowerUpManager powerUpManager) {
        long timeOnLevel = currentTime - this.levelStartTime;
        
        while (!this.spawners.isEmpty()) {
            Spawner spawn = this.spawners.get(0);
            
            if (timeOnLevel >= spawn.getSpawnTime()) {
                
                if (spawn.getEntity().equals("POWERUP")) {
                    if (spawn.getType() == 1) {
                        powerUpManager.addPowerUp(new ShieldPowerUp(spawn.getX(), spawn.getY()));
                    } else if (spawn.getType() == 2) {
                        powerUpManager.addPowerUp(new TripleShotPowerUp(spawn.getX(), spawn.getY()));
                    }
                } else {
                    enemyManager.spawnEntity(spawn);
                }
                
                this.spawners.remove(0); 
            } else {
                break;
            }
        }

        // Avança de fase ou dá game vencido após morte do chefe da fase atual
        if (enemyManager.isBossDefeated()) {
            this.currentLevel++; 
            
            if (this.currentLevel < this.numberOfLevels) {
                enemyManager.resetPhase(); 
                loadLevel(this.currentLevel, currentTime); 
            } else {
                this.victory = true;
            }
        }
    }

    // Mostra o nome da fase na tela desaparecendo aos poucos (fading) durante os 3 primeiros segundos
    public void drawLevelText(long currentTime) {
        long elapsed = currentTime - this.levelStartTime;
        
        if (elapsed < 3000) { 
            double alpha = 1.0 - (elapsed / 3000.0);
            if (alpha < 0) {
                alpha = 0;
            }
            
            int a = (int) (alpha * 255);
            GameLib.setColor(new Color(255, 255, 0, a)); 
            GameLib.drawTextCentered("LEVEL " + (this.currentLevel + 1), GameLib.WIDTH / 2.0, GameLib.HEIGHT / 2.0, 36);
        }
    }

    public boolean isVictory() { return victory; }
    public int getStartHP() { return startHP; }
}