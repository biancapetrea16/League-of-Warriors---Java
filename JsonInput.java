import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class JsonInput {
    // method to deserialize accounts from a JSON file
    public static ArrayList<Account> deserializeAccounts() {
        String accountPath = "C:\\Users\\bianca\\IdeaProjects\\tema1\\src\\accounts.json";
        ArrayList<Account> accounts = new ArrayList<>();

        try {
            // parse the JSON file
            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(new FileReader(accountPath));
            JSONArray accountsArray = (JSONArray) root.get("accounts");

            // process each account in the JSON array
            for (Object accountObj : accountsArray) {
                JSONObject accountJson = (JSONObject) accountObj;

                // retrieve general information about the account
                String name = (String) accountJson.getOrDefault("name", "Unknown");
                String country = (String) accountJson.getOrDefault("country", "Unknown");
                long gamesNumber = 0;
                Object mapsCompleted = accountJson.get("maps_completed");
                if (mapsCompleted instanceof String) {
                    gamesNumber = Long.parseLong((String) mapsCompleted);
                } else if (mapsCompleted instanceof Long) {
                    gamesNumber = (Long) mapsCompleted;
                }

                // retrieve credentials for the account
                Credentials credentials = null;
                JSONObject credentialsJson = (JSONObject) accountJson.get("credentials");
                if (credentialsJson != null) {
                    String email = (String) credentialsJson.getOrDefault("email", "");
                    String password = (String) credentialsJson.getOrDefault("password", "");
                    credentials = new Credentials(email, password);
                }

                // retrieve the favorite games list
                ArrayList<String> favoriteGames = new ArrayList<>();
                JSONArray gamesArray = (JSONArray) accountJson.get("favorite_games");
                if (gamesArray != null) {
                    for (Object gameObj : gamesArray) {
                        favoriteGames.add((String) gameObj);
                    }
                    // sort the favorite games list alphabetically
                    Collections.sort(favoriteGames);
                }

                // retrieve the characters for the account
                ArrayList<Character> characters = new ArrayList<>();
                JSONArray charactersArray = (JSONArray) accountJson.get("characters");
                if (charactersArray != null) {
                    for (Object charObj : charactersArray) {
                        JSONObject charJson = (JSONObject) charObj;
                        String cname = (String) charJson.getOrDefault("name", "Unnamed");
                        String profession = (String) charJson.getOrDefault("profession", "Unknown");
                        long level = 0;
                        Object levelObj = charJson.get("level");
                        if (levelObj instanceof String) {
                            level = Long.parseLong((String) levelObj);
                        } else if (levelObj instanceof Long) {
                            level = (Long) levelObj;
                        }
                        long experience = 0;
                        Object experienceObj = charJson.get("experience");
                        if (experienceObj instanceof String) {
                            experience = Long.parseLong((String) experienceObj);
                        } else if (experienceObj instanceof Long) {
                            experience = (Long) experienceObj;
                        }

                        // creates a new character using factory
                        Character newCharacter = CharacterFactory.createCharacter(profession, cname, (int) experience, (int) level);

                        if (newCharacter != null) {
                            characters.add(newCharacter);
                        }
                    }
                }

                // use builder
                Account.Information information = new Account.Information.Builder()
                        .setFavoriteGame(favoriteGames)
                        .setCredentials(credentials)
                        .setName(name)
                        .setCountry(country)
                        .build();
                Account account = new Account(information);
                account.setGamesPlayed((int) gamesNumber);
                for (Character character : characters) {
                    account.addCharacter(character);
                }
                // add the account to the list
                accounts.add(account);
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error reading or parsing the JSON file: " + e.getMessage());
        }
        // return the list of deserialized accounts
        return accounts;
    }
}
