#  **Run or Croak** - Java Game  🐸 

![Game Screenshot](/src/resources/sprites/sc-001.png)
> *"Can you run fast enough to survive? Or will you croak?"*

Welcome to **Run or Croak**, a fast-paced 2D action game where you control a daring character trying to avoid obstacles and collect points. Dodge bees, jump over traps, and see how far you can go as the game gets faster and harder!




## 🌟 **Table of Contents**
- [🎮 Game Overview](#-game-overview)
- [🚀 How to Run the Game](#-run-the-game)
- [⚙️ Principles and Design Patterns](#️-principles-and-design-patterns)
- [📜 Game Instructions](#-game-instructions)
- [💻 Technologies Used](#-technologies-used)
- [✨ Credits](#-credits)



## 🎮 Game Overview

### 🎯 Objective

Your goal is simple: **survive as long as possible!** Control your character as they run through a dangerous path, dodging obstacles and collecting items to maximize your score. 

### 🐸 Features

- **Dynamic Difficulty**: The game gets harder as you play. Obstacles spawn faster, and the speed increases!
- **Smooth Animations**: Enjoy fluid character movements and polished obstacle animations.
- **Lives System**: You start with 3 lives. Once you lose them all... it's *Game Over!*
- **Collision Detection**: Careful! Touch an obstacle and you'll lose a life.
- **Charming Graphics**: Featuring pixel art visuals and a retro vibe.
- **Sound Effects**: Immersive background music and sound effects add to the fun.

### 🎮 Game States

- **Intro Screen**: A welcoming start to set the mood.
- **Title Screen**: Start the game, see your best score, or adjust settings.
- **Playing Mode**: The main action takes place here — run or croak!
- **Game Over Screen**: Shows your final score and lets you restart.


## 🚀 How to Run the Game

### 🔗 **Option 1: Run the `.jar` File**
1. **Download the game**:
   - [⬇️ Download `run-or-croak.jar`](https://github.com/Sebaspallero/run-or-croak/raw/run-or-croak.jar)
2. **Install Java**: Ensure you have **Java JRE 8 or higher** installed on your system. [Get Java here](https://www.oracle.com/java/technologies/javase-downloads.html).
3. **Run the game**:
   Open a terminal and use the following command:

   ```bash
   java -jar RunOrCroak.jar
   ```
### 🛠️ Option 2: Run the Code in your IDE
If you'd like to explore or modify the code, follow these steps:

1. **Install Java JDK**: 
    - [⬇️ JDK Development Kit](https://www.oracle.com/java/technologies/downloads/?er=221886)

2. **Install "Java Extension Pack"**: If you are using Visual Studio Code, go to the Extensions view (`Ctrl+Shift+X` or `Cmd+Shift+X` on macOS) and search for "Java Extension Pack." 

2. **Clone the repository**:

   ```bash
   git clone https://github.com/your-username/RunOrCroak.git
   ```

3. **Compile and run**:
   ```bash
   javac -d bin -sourcepath src src/game/main/Game.java
   java -cp bin game.main.Game
   ```
## ⚙️ Principles and Design Patterns

The game was built with clean coding principles and utilizes multiple software design patterns for flexibility and scalability:

### 🔁 **Game Loop**
The game continuously updates and renders in a loop:
- **Update**: Game state, player movements, and obstacle positions.
- **Render**: Draws all visual elements to the screen.

### 🖼️ **MVC Pattern**
The game adheres to the **Model-View-Controller (MVC)** pattern:
- **Model**: Handles the game logic (score, obstacles, etc.).
- **View**: Renders the game graphics (character, UI, etc.).
- **Controller**: Manages player inputs like key presses.

### 🏭 **Factory Pattern**
The **EntityFactory** class dynamically generates obstacles and items, ensuring modular and scalable code.

### 🎵 **Singleton Pattern**
The **SoundPlayer** class ensures that only one instance of sound effects and background music is active at any time.

### 📜 **State Pattern**
Manages the game's transitions between states (Intro, Title, Playing, Game Over) smoothly and efficiently.

## 📜 **Game Instructions**

1. **Start the Game**: 
   - Launch the game and press `Spacebar` on the **Title Screen** to begin!
2. **Controls**:
   - **⬆️ Spacebar**: To jump over the obstacles!
   - **⬇️ Down Arrow**: Slide right under those pesky enemies.
3. **Objective**: Avoid obstacles, collect items, and survive as long as you can.
4. **Game Over**: When you lose all your lives, your final score will be displayed.
5. **Restart**: Press `Enter` after the game ends to start over.

>**Tip**: Watch out for those pesky bees—they’ll follow you around!



## 💻Technologies Used

This project uses a combination of Java tools and libraries:

- **Java**: Core language for game logic and rendering.
- **Swing**: For the graphical user interface (GUI).
- **ImageIO**: To load sprites and animations.
- **Animator**: Custom class to handle smooth frame-based animations.
- **SoundPlayer**: Plays background music and sound effects.

### 🛠️ Development Tools
- **IDE**: Visual Studio Code
- **JDK**: Version 8 or higher
- **External Libraries**: Custom fonts and assets

## ✨ **Credits**

- **Development**: Sebastián Pallero Oría
- **Graphics**: Pixel art from [Pixelfrog](https://pixelfrog-assets.itch.io/) & custom assets by Sebastián Pallero Oría.
- **Sound**: Background music and sound effects sourced from royalty-free libraries.
- **Font**: `AvenuePixel` by [Jdjimenez](https://jdjimenez.itch.io/).

