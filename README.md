# EMCAPIClient

Fair warning: this library is intended for my own personal use across projects, if I decide something isn't how I like it, I will change it, so do not expect compatibility across versions

# Using EMCAPIClient
Create an instance of EMCAPIClient like this
```java
EMCAPIClient api = new EMCAPIClient();
```

Every method you should be using is within this class, the method names are self-explanatory, let me know if you need any help using the library

BASIC EXAMPLE: get a nation by its name and print the username of every resident in it
```java
public static void main(String[] args) {
    EMCAPIClient api = new EMCAPIClient();

    Nation nation = api.getNationByString("Alaska");
    if (nation == null) return;

    List<Player> players = api.getPlayersByIdentifiers(nation.getResidents());
    for (Player player : players) {
        System.out.println(player.getName());
    }
}
```

# Adding EMCAPIClient to your project
Add EMCAPIClient to your Java project at https://jitpack.io/#jwkerr/EMCAPIClient

Replace "Tag" in the below examples with the latest Git commit hash (https://github.com/jwkerr/EMCAPIClient/commits/master/)
## Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```xml
<dependencies>
    <dependency>
        <groupId>com.github.jwkerr</groupId>
        <artifactId>EMCAPIClient</artifactId>
        <version>Tag</version>
    </dependency>
</dependencies>
```

## Gradle
```groovy
repositories {
    mavenCentral()
    maven { url "https://jitpack.io" }
}
```

```groovy
dependencies {
        implementation "com.github.jwkerr:EMCAPIClient:Tag"
}
```