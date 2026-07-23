package Managers;

import Utils.Spawner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LevelManager {
    private int startHP;
    private int numberOfLevels;
    private List<String> levelFiles;

    private int currentLevel;
    private List<Spawner> spawners;
    private long levelStartTime; // Em que momento a fase inicia

    public LevelManager(String configFile, long currentTime) {
        levelFiles = new ArrayList<>();
        spawners = new ArrayList<>();
        currentLevel = 0; // 0 significa a primeira fase na nossa lista
        
        readConfigFile(configFile);
        loadLevel(currentLevel, currentTime);
    }

    // Lê o arquivo principal (que diz HP, Num Fases e Nomes dos Arquivos)
    private void readConfigFile(String path) {
        try {
            Scanner scanner = new Scanner(new File(path));
            
            // Lê conforme o formato do PDF:
            this.startHP = scanner.nextInt(); // <PONTOS DE VIDA DO JOGADOR>
            this.numberOfLevels = scanner.nextInt();      // <NUMERO DE FASES>
            scanner.nextLine(); // Pula para a próxima linha
            
            // Lê o nome dos arquivos de cada fase
            for (int i = 0; i < numberOfLevels; i++) {
                levelFiles.add(scanner.nextLine().trim());
            }
            scanner.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("Erro: Arquivo principal não encontrado: " + path);
            System.exit(1);
        }
    }

    // Lê o arquivo específico da fase (ex: "fase1.txt")
    private void loadLevel(int levelNumber, long currentTime) {
        spawners.clear(); // Limpa os eventos da fase anterior

        this.levelStartTime = currentTime; // Zera o cronômetro da fase

        String levelPath = "Levels/" + levelFiles.get(levelNumber);

        try {
            Scanner scanner = new Scanner(new File(levelPath));
            
            while (scanner.hasNext()) {
                String entity = scanner.next(); // Lê a primeira palavra (INIMIGO, CHEFE, POWERUP)
                
                if (entity.equals("CHEFE")) {
                    int type = scanner.nextInt();
                    int hp = scanner.nextInt();
                    long when = scanner.nextLong();
                    double x = scanner.nextDouble();
                    double y = scanner.nextDouble();
                    spawners.add(new Spawner(entity, type, hp, when, x, y));
                } 
                else { // Se for INIMIGO ou POWERUP (mesma estrutura)
                    int type = scanner.nextInt();
                    long when = scanner.nextLong();
                    double x = scanner.nextDouble();
                    double y = scanner.nextDouble();
                    spawners.add(new Spawner(entity, type, when, x, y));
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erro: Arquivo da fase não encontrado: " + levelPath);
            System.exit(1);
        }
    }

    // Chamado no loop principal para verificar se chegou a hora de spawnar algo
    public void update(long currentTime, EnemyManager enemyManager) {
        long timeOnLevel = currentTime - levelStartTime;
        
        // Faz o spawn dos inimigos no tempo correto
        while (!spawners.isEmpty()) {
            Spawner spawn = spawners.get(0);
            
            if (timeOnLevel >= spawn.getSpawnTime()) {
                enemyManager.spawnEntity(spawn);
                spawners.remove(0); 
            } else {
                break;
            }
        }

        // --- NOVA LÓGICA DE TRANSIÇÃO DE FASE ---
        // Se a fila de spawns está vazia E a lista de inimigos vivos na tela também está vazia
        if (spawners.isEmpty() && enemyManager.getEnemies().isEmpty()) {
            
            currentLevel++; // Avança para o índice da próxima fase na lista
            
            // Verifica se ainda existem fases disponíveis
            if (currentLevel < numberOfLevels) {
                System.out.println("-> Iniciando fase " + (currentLevel + 1));
                loadLevel(currentLevel, currentTime);
            } else {
                // Se o índice for maior ou igual ao total de fases, o jogo acabou
                System.out.println("-> JOGO FINALIZADO! Você venceu todas as fases!");
                
                // Finaliza o processo por enquanto (futuramente você pode colocar uma tela de vitória aqui)
                System.exit(0); 
            }
        }
    }

    public int getStartHP() { return startHP; }
}