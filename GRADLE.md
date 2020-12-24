# The Gradle project of The Midnight

Our lead programmer [Shadew](https://github.com/ShadewEnder) decided to make a unique gradle project structure for the Midnight rewrite. Below, you can see how to work with this new project structure, along with how to do specific tasks on your own.

So far, we've only tested this new gradle project structure in IntelliJ IDEA, so we have no guarantee it'll work in other IDEs like Eclipse or VSCode, but give it a try anyway!

## The Structure

The Midnight is divided into four projects:

- **`:api` - the API code.** This project contains the API classes which are publicly exposed for compatibility support.
- **`:` - the root project.** This project contains the core code along with the implementation of the API and the core classes and makes them 'the Midnight' The majority of the code is located here.

## Building

To build the Midnight, simply run `./gradlew build`. It will build `buildSrc` and then all subprojects, and you can find the jar files in the `build/libs` folders of all subprojects after it has built successfully.

## Testing The Midnight

To test The Midnight, simply generate the run files of whatever IDE you're using (`./gradlew genIntellijRuns` or `./gradlew genEclipseRuns`) and run the client/server as normal. If The Midnight does not initialize with Minecraft, simply generate the run files again. Please note that because of the complex Gradle structure, your IDE might take a little bit of time before it loads the game.

- If for some reason your game crashes or does not load on startup, try regernating the run configurations again. Chances are, we updated some dependencies and your run configs are outdated.

You may have noticed that there's a `runTestServer` run configuration. **Do not run it!** It is meant for our GitHub Actions CI and you will just crash on world load if you run it, since that is what it's meant to do.

## Generating Data

This project will automatically run data generation every time you build the project or run either the client or server. Please *do not* run the `runData` task as it will delete everything in the generated folder and you'll just have to regenerate all of it on the next build anyway.

## Updating the Changelog

The Midnight's changelog can be edited in `changelog.json`. To update the changelogs in the `versioninfo/` folder, simply run `./gradlew makeVersionInfo`.

## Information on GitHub Actions

We use GitHub Actions to build and test The Midnight on every commit, and at 12:00 PM (whatever time zone GH Actions uses, probably UTC) every Monday and Friday. These builds are also signed and there is a check in place to make sure that they are signed correctly. To ensure that The Midnight is able to run on the vast majority of platforms, the server test is conducted on Windows Server, macOS, and Ubuntu. If you are making a pull request, please **do not change** the YAML files located under `.github/workflows/`. We will ask that you revert them in your PR if you happen to do so.
