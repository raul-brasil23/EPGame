package Managers;

import Utils.GameLib;
import Utils.Spawner;
import Entities.ShieldPowerUp;
import Entities.TripleShotPowerUp;
import java.io.File;
import java.io.FileNotFoundException;
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

    private boolean victory = false;

    public LevelManager(String configFile, long currentTime) {
        levelFiles = new ArrayList<>();
        spawners = new ArrayList<>();
        currentLevel = 0; 
        
        readConfigFile(configFile);
        loadLevel(currentLevel, currentTime);
    }

    private void readConfigFile(String path) {
        try {
            Scanner scanner = new Scanner(new File(path));
            
            this.startHP = scanner.nextInt(); 
            this.numberOfLevels = scanner.nextInt();      
            scanner.nextLine(); 
            
            for (int i = 0; i < numberOfLevels; i++) {
                levelFiles.add(scanner.nextLine().trim());
            }
            scanner.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("Erro: Arquivo de configuração não encontrado: " + path);
            System.exit(1);
        }
    }

    private void loadLevel(int levelNumber, long currentTime) {
        spawners.clear(); 
        this.levelStartTime = currentTime; 

        String levelPath = "Levels/" + levelFiles.get(levelNumber);

        try (Scanner scanner = new Scanner(new File(levelPath))) { 
            
            while (scanner.hasNext()) {
                String entity = scanner.next(); 
                
                if (entity.equals("CHEFE")) {
                    int type = scanner.nextInt();
                    int hp = scanner.nextInt();
                    long when = scanner.nextLong();
                    double x = scanner.nextDouble();
                    double y = scanner.nextDouble();
                    spawners.add(new Spawner(entity, type, hp, when, x, y));
                } 
                else { 
                    int type = scanner.nextInt();
                    long when = scanner.nextLong();
                    double x = scanner.nextDouble();
                    double y = scanner.nextDouble();
                    spawners.add(new Spawner(entity, type, when, x, y));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro: Arquivo da fase não encontrado: " + levelPath);
            System.exit(1);
        }
    }

    // Atualizado para receber também o PowerUpManager
    public void update(long currentTime, EnemyManager enemyManager, PowerUpManager powerUpManager) {
        long timeOnLevel = currentTime - levelStartTime;
        
        // Spawn de entidades baseado no tempo cronológico
        while (!spawners.isEmpty()) {
            Spawner spawn = spawners.get(0);
            
            if (timeOnLevel >= spawn.getSpawnTime()) {
                
                // VERIFICA SE A ENTIDADE É UM POWER-UP
                // Nota: Substitua "getEntity()" pelo getter correto da sua classe Spawner (ex: getName(), getTypeString())
                if (spawn.getEntity().equals("POWERUP")) {
                    
                    // Tipo 1 = Escudo | Tipo 2 = Tiro Triplo
                    if (spawn.getType() == 1) {
                        powerUpManager.addPowerUp(new ShieldPowerUp(spawn.getX(), spawn.getY()));
                    } else if (spawn.getType() == 2) {
                        powerUpManager.addPowerUp(new TripleShotPowerUp(spawn.getX(), spawn.getY()));
                    }
                    
                } else {
                    // Se não for Power-Up, manda para o EnemyManager normalmente
                    enemyManager.spawnEntity(spawn);
                }
                
                spawners.remove(0); 
            } else {
                break;
            }
        }

        // A fase só é finalizada quando o chefe morre
        if (enemyManager.isBossDefeated()) {
            
            currentLevel++; 
            
            if (currentLevel < numberOfLevels) {
                enemyManager.resetPhase(); // Limpa os inimigos da fase velha
                loadLevel(currentLevel, currentTime); // Carrega e reseta o cronômetro
            } else {
                this.victory = true;
            }
        }
    }

    public boolean isVictory() { return victory; }
    public int getStartHP() { return startHP; }

    // Efeito de Fade Out por 3 segundos
    public void drawLevelText(long currentTime) {
        long elapsed = currentTime - levelStartTime;
        if (elapsed < 3000) { 
            double alpha = 1.0 - (elapsed / 3000.0);
            if (alpha < 0) alpha = 0;
            int a = (int) (alpha * 255);
            GameLib.setColor(new Color(255, 255, 0, a)); 
            GameLib.drawTextCentered("LEVEL " + (currentLevel + 1), GameLib.WIDTH / 2.0, GameLib.HEIGHT / 2.0, 36);
        }
    }
}