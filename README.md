# EscapeFromUni

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.


## Links
### PDFs:
- [Requirements]()
- [Architecture]()
- [Method Selection and Planning]()
- [Risk Assessment and Mitigation]()
- [Implementation]()
### Executable JAR:
- [ExcapeFromUniJAR]()
### Version Control Repository:
- [Repository](https://github.com/PossiblyMaybe/eng1team11game)

## Plan
### Week 1 plan

The initial plan allows one week for each phase of the project. 

### Week 2 plan

The website set up took less time than expected so the Method Selection and Planning phase began earlier, giving us more time before the deadline in case of delays in later stages. 

### Week 3-5 plan
The plan remained the same during these weeks. 

### Week 6 plan

Implementation took longer than initially planned, but due to the previous edit of the plan there was enough time to accommodate for this. 



## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

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
