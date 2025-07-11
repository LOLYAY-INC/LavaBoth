# LavaBoth - LavaPlayer + Lavalink

When making a Discord bot, the most common way to play music is by using Lavalink or LavaPlayer.
Most of the time you need to decide wich one to use, and it's very hard to switch between them.

LavaBoth is a library that makes it easier to switch between Lavalink and LavaPlayer.

Its one library that's easy to set up and allows you to switch between Lavalink and LavaPlayer easily.
It's designed to work with Jda, but can be used with other Discord libraries.

## Installation:
Maven:
````xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/LOLYAY-INC/LavaBoth</url>
    </repository>
</repositories>
````
```xml
<dependency>
    <groupId>io.lolyay</groupId>
    <artifactId>lavaboth</artifactId>
    <version>0.2.3</version>
</dependency>
```

## Usage:
This tutorial focuses on using LavaBoth with Jda.

Firstly, you need to create a PlayerManager:
This is the only thing different between Lavalink and LavaPlayer.

### Lavalink

```java 
AbstractPlayerManager playerManager = new LavaLinkPlayerManager(
        /* Your JDABuilder BEFORE calling .build() */         jdaBuilder,
        /* Your bot's user ID */                              botUserId,
        
        /* Your Lavalink connection info */                   new ConnectionInfo(
        /* Name of your Lavalink server (currently unused) */ "LavaBoth",
        /* Your Lavalink host (change ws to wss if secure) */ "ws://some.lavalink.host:2333",
        /* Secure? */                                         true,
        /* Your Lavalink password */                          "password"
        ) 
);
```

### LavaPlayer
*Hint: This doesn't need to be run before Building Your Jda.*

```java
AbstractPlayerManager playerManager = LavaPlayerPlayerManager.getBuilder()
        /* OPTIONAL: Set decoder format if you need to ( Won't work with JDA ) */.setDecoderFormat(new Pcm16AudioDataFormat(/*...*/))
        /* OPTIONAL: Set opus encoding quality 1 to 10 ( Default is 5 )        */.setOpusEncodingQuality(5)
        /* OPTIONAL: Set resampling quality (Default ResamplingQuality.MEDIUM) */.setResamplingQuality(ResamplingQuality.MEDIUM)
        /* OPTIONAL: Set track stuck timeout                                   */.setTrackStuckTimeout(60)
        /* OPTIONAL: Use ghost seeking                                         */.setUseGhostSeeking(true)
        .build();

new SourcesBuilder(playerPlayerManager)
/* Add Default Sources */                       .addDefault()
/* You can also add custom sources */           .addSource(YourCustomAudioSourceManager source)                 

// For the built in SearchManager to work you need to add one of the following youtube sources:
/* Add Youtube Dlp */                           .addYoutubeDlp("path/to/youtube-dlp.exe")
/* Add Youtube */                               .addYoutube("oauth-token")
/* If you don't have a oauth token, leave the parameter as an empty string, it will ask you to log in, then save the token it gives you in console... */ 
/* Register all sources                       */.buildAndRegister();

```


### Global:

Searching for a track:
```java
playerManager.getSearchManager().search(query, search -> {
        // Returns a Search object
        if (search.isSuccess()) {
            // Do something with the track
        
        }
        if (search.result().status() == SearchResult.Status.PLAYLIST) {
            // Loaded a Playlist
        }

});

```

Playing a track:
```java
// Connect to a voice channel
 playerManager.getPlayerFactory().getOrCreatePlayer(/* Guild ID */)
        .connect(/* Voice Channel */);

// Play a track
playerManager.getPlayerFactory().getOrCreatePlayer(/* Guild ID */)
        .play(/* Track */);

```



# This Project is Still in early development so if you find any bugs, please report them on [Github](https://github.com/LOLYAY-INC/LavaBoth/issues)