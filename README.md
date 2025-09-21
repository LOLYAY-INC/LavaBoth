# LavaBoth

[![Maven Central](https://img.shields.io/maven-central/v/dev.lolyay/lavaboth?style=for-the-badge&label=Maven%20Central)](https://search.maven.org/artifact/dev.lolyay/lavaboth)
[![GitHub Issues](https://img.shields.io/github/issues/LOLYAY-INC/LavaBoth?style=for-the-badge)](https://github.com/LOLYAY-INC/LavaBoth/issues)
[![License](https://img.shields.io/github/license/LOLYAY-INC/LavaBoth?style=for-the-badge)](https://github.com/LOLYAY-INC/LavaBoth/blob/main/LICENSE)

A unified library providing a single API for both **LavaPlayer** and **Lavalink**, making it seamless to switch between local and server-based audio playback for your Discord bot.

## The Problem
When building a music-focused Discord bot with Java, developers typically have to choose between two excellent libraries: LavaPlayer (for local processing) and Lavalink (for a standalone audio server). The APIs are different, and migrating from one to the other can be a significant undertaking.

## The Solution
**LavaBoth** abstracts the differences between these two systems, providing a single, consistent interface for all your audio needs. You can write your music logic once and switch the backend implementation with only a few lines of code. It's designed primarily for **JDA** but is flexible enough to be adapted for other Discord libraries.

## Features
*   **Unified API:** A single set of methods for both Lavalink and LavaPlayer.
*   **Easy to Switch:** Change your entire audio backend by swapping out a single class.
*   **Built-in Search:** A powerful search manager to find tracks and playlists.
*   **Extensible:** Easily add custom audio sources and search providers.

## Installation

### Maven
Add the dependency to your `pom.xml`:
```xml
<dependencies>
    <dependency>
        <groupId>dev.lolyay</groupId>
        <artifactId>lavaboth</artifactId>
        <version>5.6.7/version>
    </dependency>
</dependencies>
```

### Gradle (Kotlin DSL)
Add this dependency to your `build.gradle.kts`:
```kotlin

dependencies {
    implementation("dev.lolyay:lavaboth:5.6.7")
}
```

## Usage
This guide focuses on using LavaBoth with JDA.

### Step 1: Create a PlayerManager
This is the only part of your code that needs to know whether you're using Lavalink or LavaPlayer. Choose **one** of the following options.

#### Option A: Lavalink
This setup connects to a running Lavalink server.
```java
// This must be configured before calling jdaBuilder.build()
AbstractPlayerManager playerManager = new LavaLinkPlayerManager(
    /* Your JDABuilder */       jdaBuilder,
    /* Your bot's user ID */    botUserId,
    /* Connection info */       new ConnectionInfo(
        /* Server name (unused) */  "LavaBoth",
        /* Host (use wss:// for secure) */ "ws://lavalink.example.com:2333",
        /* Is secure? */            false,
        /* Password */              "youshallnotpass"
    )
);
```

#### Option B: LavaPlayer
This setup runs the audio processing locally within your bot's process.
```java
// This can be run at any time.
AbstractPlayerManager playerManager = LavaPlayerPlayerManager.getBuilder()
    // Optional configuration
    .setOpusEncodingQuality(10)
    .setResamplingQuality(ResamplingQuality.HIGH)
    .setTrackStuckTimeout(10000)
    .build();

// Configure and register audio sources
new SourcesBuilder(playerManager)
    .addDefault() // YouTube, SoundCloud, etc.
    // .addYoutubeDlp("path/to/yt-dlp.exe") // For better YouTube support
    .buildAndRegister();

// Register search providers for the search manager
playerManager.getSearchManager().registerDefaultSearchers();
```

### Step 2: Searching for a Track
Use the unified `SearchManager` to find audio.

```java
// RequestorData can store who requested a track.
// Use RequestorData.ofMember(member), RequestorData.system(), or RequestorData.anonymous().
RequestorData requestor = RequestorData.ofMember(event.getMember());

playerManager.getSearchManager().search(query, search -> {
    switch (search.result().getStatus()) {
        case SUCCESS:
            // A single track was found
            AudioTrack track = search.track().get();
            System.out.println("Found track: " + track.getInfo().getTitle());
            // Now you can play the track (see Step 3)
            playTrack(guild, track);
            break;

        case PLAYLIST:
            // A playlist was found
            AudioPlaylist playlist = search.playlistData().playlist();
            System.out.println("Found playlist: " + playlist.getName());
            // You can add the whole playlist to your queue
            queue.addAll(playlist.getTracks());
            break;

        case NOT_FOUND:
            System.out.println("Nothing found for your query.");
            break;

        case ERROR:
            System.out.println("Error searching: " + search.result().getMessage());
            break;
    }
}, requestor);
```

### Step 3: Controlling the Player
Once you have an `AudioTrack`, you can control the player for a specific guild.

```java
// Get the player for a specific guild (it will be created if it doesn't exist)
GuildPlayer guildPlayer = playerManager.getPlayerFactory().getOrCreatePlayer(guild.getIdLong());

// Connect to a voice channel
guildPlayer.connect(voiceChannel);

// Play a track
guildPlayer.play(track);

// Pause or resume playback
guildPlayer.pause();
guildPlayer.resume();

// Stop the player and clear the current track
guildPlayer.stop();

// Set the volume (0-150, 100 is default)
guildPlayer.setVolume(80);

// Get player status
AudioTrack currentTrack = guildPlayer.getCurrentTrack();
boolean isPaused = guildPlayer.isPaused();
int volume = guildPlayer.getVolume();

// Disconnect from the voice channel
guildPlayer.disconnect(guild);
```

## Bug Reports & Feature Requests
This project is in active development. If you find a bug or have an idea for a new feature, please [open an issue on GitHub](https://github.com/LOLYAY-INC/LavaBoth/issues). We appreciate your feedback