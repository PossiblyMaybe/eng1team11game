# EscapeFromUni


### Tutorial
- Movement is done via the up, down, left, and right arrows found to the rightward end of the keyboard.
- Dashing can be performed by pressing the space bar.
- Some parts of the environment can be broken by dashing into them.
- Items can be collected by moving over them.
- If the end of the maze is not reached within 5 minutes, you will lose the game.
- If you reach the end of the maze within 5 minutes, you win.
- Be careful, if you move over a trap and don't dash out of it you will be returned to the start of the room.

## Links
### PDFs:
- [Requirements](https://drive.google.com/open?id=1YRm9ibuXdxPoXoQ9FpuN6CCZU18TGqme&usp=drive_copy)
- [Architecture]()
- [Method Selection and Planning](https://drive.google.com/open?id=1-ZwqyeLFDMuxPolpbPpPfbenKGEcH6Q7&usp=drive_copy)
- [Risk Assessment and Mitigation](https://drive.google.com/open?id=1w7KW20eSDxNYc-xmj-bfqXltp_giHH6S&usp=drive_copy)
- [Implementation]()
### Executable JAR:
- [ExcapeFromUniJAR]()
### Version Control Repository:
- [Repository](https://github.com/PossiblyMaybe/eng1team11game)

## Plan
### Week 1 plan
![Week 1 Project Plan](/eng1team11game/WebsiteImages/Week1Plan.jpg)

The initial plan allows one week for each phase of the project. 

### Week 2 plan
![Week2Plan.jpg](/eng1team11game/WebsiteImages/Week2Plan.jpg)

The website set up took less time than expected so the Method Selection and Planning phase began earlier, giving us more time before the deadline in case of delays in later stages. 

### Week 3-5 plan
The plan remained the same during these weeks. 

### Week 6 plan
![Week 6 Project Plan](/eng1team11game/WebsiteImages/Week6Plan.jpg)

Implementation took longer than initially planned, but due to the previous edit of the plan there was enough time to accommodate for this. 


## Risk Assessment
Risk Register:

![Risk Register](/eng1team11game/WebsiteImages/RiskRegister.png)


## Architecture Diagrams
The first diagram to contain the entire state of the project at that time.
![classDiagramV1](/eng1team11game/WebsiteImages/classDiagramV1.png)




The current state of the system

## Additional Content

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.


### Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

### Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
