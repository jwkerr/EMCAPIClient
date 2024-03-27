# EMCAPIClient

Create an instance of EMCAPIClient like this

```java
EMCAPIClient api = new EMCAPIClient();
```

EXAMPLE: get the data of all currently registered players
```java
EMCAPIClient api = new EMCAPIClient();

List<PlayerIdentifier> players = api.getAllPlayerIdentifiers();

List<PlayerData> playerData = api.getPlayerData(players);
```