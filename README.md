# EMCAPIClient

# Using EMCAPIClient
Create an instance of EMCAPIClient like this
```java
EMCAPIClient api = new EMCAPIClient();
```

Every method you should be using is within this class, there are Javadocs that you can read to learn how to use the library, the method names are self-explanatory

BASIC EXAMPLE: get the data of all currently registered players and print their username
```java
public static void main(String[] args) {
    EMCAPIClient api = new EMCAPIClient();

    List<PlayerIdentifier> playerIdentifiers = api.getAllPlayerIdentifiers();
    List<PlayerData> playerData = api.getPlayerDataByIdentifiers(playerIdentifiers);

    int n = 1;
    for (PlayerData player : playerData) {
        System.out.println(n + ": " + player.getName());
        n++;
    }
}
```

# Adding EMCAPIClient to your project
Add EMCAPIClient to your Java project at https://jitpack.io/#Fruitloopins/EMCAPIClient

Replace "Tag" in the below examples with the latest Git commit hash (https://github.com/Fruitloopins/EMCAPIClient/commits/master/)
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
        <groupId>com.github.Fruitloopins</groupId>
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
        implementation "com.github.Fruitloopins:EMCAPIClient:Tag"
}
```