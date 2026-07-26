# 🚀 Space Shooter - Java

Um jogo do gênero *Shoot 'em Up* (Navinha) desenvolvido inteiramente em Java. Este projeto foi construído para aplicar conceitos avançados de Programação Orientada a Objetos (POO), substituindo o uso de vetores fixos por coleções dinâmicas e implementando uma arquitetura robusta baseada em Gerenciadores (Managers) e Entidades.

---

## 🎮 Funcionalidades

* **Sistema de Níveis Dinâmico:** As fases são lidas a partir de arquivos de texto (`.txt`) localizados na pasta `Levels/`, permitindo criar novos desafios sem precisar alterar o código-fonte.
* **Mecânicas do Jogador:**
  * Movimentação fluida e disparo de projéteis.
  * Sistema de invulnerabilidade temporária (efeito visual de "piscar") ao receber dano.
* **Power-Ups:** 
  * 🛡️ **Escudo:** Protege a nave contra danos.
  * 🔫 **Tiro Triplo:** Aumenta a cadência e o espalhamento do dano.
* **Inimigos Variados:** Diferentes tipos de inimigos (`CircleEnemy`, `DiamondEnemy`) e Chefes (`Boss`, `MovingBoss`, `StaticBoss`), cada um com comportamentos de movimento independentes.
* **Gerenciamento de Telas:** Transições entre Tela Inicial, Jogo, Game Over e Vitória.

---

## 🏗️ Arquitetura do Projeto

O projeto utiliza uma estrutura modular, dividida nos seguintes pacotes principais:

* 📂 **`Behaviors`**: Classes responsáveis por definir os padrões de movimento (Circular, Diamante, Oscilante, etc.) utilizando o padrão de projeto *Strategy*.
* 📂 **`Entities`**: Contém todas as classes que representam objetos no jogo (Player, Inimigos, Projéteis e Power-Ups).
* 📂 **`Managers`**: Os "motores" do jogo. Controlam a lógica de colisão (`CollisionManager`), o spawn de entidades (`LevelManager`, `EnemyManager`), e os elementos em tela (`ScreenManager`).
* 📂 **`Screens`**: Interface gráfica e estados do jogo (Start, Playing, Victory, GameOver).
* 📂 **`Utils`**: Classes utilitárias, bibliotecas do jogo e a classe principal (`Main`).
* 📂 **`Levels`**: Arquivos de configuração externos contendo o mapeamento de entidades, tempo de spawn e coordenadas de cada fase.

---

## 🛠️ Como Compilar e Executar

Para garantir a execução perfeita em qualquer computador ou sistema operacional, independente da IDE utilizada (VS Code, Eclipse, IntelliJ), siga as instruções abaixo utilizando o terminal.

### Pré-requisitos
* **Java Development Kit (JDK)** instalado na máquina.
* Terminal de sua preferência (CMD, PowerShell, Bash).

### Passo a Passo

**1. Clone ou baixe o projeto e navegue até a pasta raiz:**
Abra o terminal de sua preferência e navegue até a pasta principal do projeto (onde as pastas src e bin estão localizadas).

**2. Compile o código-fonte:**
Este comando lerá todos os arquivos `.java` da pasta `src` e criará os binários compilados de forma organizada dentro da pasta `bin`.
```bash
javac -d bin -sourcepath src src/Main.java
```

**3. Execute o Jogo:**
Inicie o programa a partir da pasta raiz. Isso garante que o jogo consiga localizar e ler corretamente os arquivos da pasta `Levels/`.
```bash
java -cp bin Main
```

---

## ⌨️ Controles do Jogo

* **Setas Direcionais**: Movimentam a nave.
* **CTRL**: Dispara projéteis.
* **ESC**: Sai do jogo.
* **Espaço**: Inicia o jogo, e recomeça caso perca ou ganhe o jogo.

---

## 👨‍💻 Desenvolvido por
* **Giovane Giroldo da Silva**
* **Pedro Botelho Gondim Dantas**
* **Raul Brasil de Sousa**
*Projeto Acadêmico (EP-COO)*