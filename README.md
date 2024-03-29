# EMCAPIClient

Create an instance of EMCAPIClient like this

```java
EMCAPIClient api = new EMCAPIClient();
```

Every method you should be using is within this class, there are Javadocs that you can read to learn how to use the library, the method names are self-explanatory

BASIC EXAMPLE: get the data of all currently registered players
```java
EMCAPIClient api = new EMCAPIClient();

List<PlayerIdentifier> players = api.getAllPlayerIdentifiers();

List<PlayerData> playerData = api.getPlayerData(players);
```