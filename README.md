#  **Run or Croak** - Java Game  🐸 

![Game Screenshot](/src/resources/sprites/sc-001.png)
> *"Can you run fast enough to survive? Or will you croak?"*

Welcome to **Run or Croak**, a fast-paced 2D action game where you control a daring character trying to avoid obstacles and collect points. Dodge bees, jump over traps, and see how far you can go as the game gets faster and harder!




## 🌟 **Table of Contents**
- [🎮 Game Overview](#-game-overview)
- [⚙️ Principles and Design Patterns](#️-principles-and-design-patterns)
- [📜 Game Instructions](#-game-instructions)
- [💻 Technologies Used](#-technologies-used)
- [🖼️ Screenshots & GIFs](#️-screenshots--gifs)
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

