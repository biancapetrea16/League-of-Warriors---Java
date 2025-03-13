import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

public class Account {
    private Information information;
    private ArrayList<Character> characters;
    private int nrGamesPlayed;

    public Account(Information information) {
        this.information = information;
        this.characters = new ArrayList<>();
        this.nrGamesPlayed = 0;
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }

    // increment number of games played
    public void incrementGamesPlayed() {
        nrGamesPlayed++;
    }

    public Information getInformation() {
        return information;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public int getGamesPlayed() {
        return nrGamesPlayed;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.nrGamesPlayed = gamesPlayed;
    }

    @Override
    public String toString() {
        return "Account: " + "information = " + information + ", characters = " + characters + ", gamesPlayed = " + nrGamesPlayed;
    }

    public static class Information {
        private Credentials credentials;
        private ArrayList<String> favoriteGames;
        private String name;
        private String country;

        // private constructor
        private Information(Builder b) {
            this.credentials = b.credentials;
            this.favoriteGames = b.favoriteGames;
            this.name = b.name;
            this.country = b.country;
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public ArrayList<String> getFavoriteGames() {
            return favoriteGames;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        @Override
        public String toString() {
            return "Information: " + "name = " + name + ", country = " + country + ", favoriteGames = " + favoriteGames;
        }

        // builder
        public static class Builder {
            private Credentials credentials;
            private ArrayList<String> favoriteGames;
            private String name;
            private String country;

            public Builder(){}

            public Builder(Credentials credentials, String name, String country, ArrayList<String> favoriteGames) {
                this.credentials = credentials;
                this.favoriteGames = favoriteGames;
                this.name = name;
                this.country = country;
            }

            public Builder setCredentials(Credentials credentials) {
                this.credentials = credentials;
                return this;
            }

            public Builder setName(String name) {
                this.name = name;
                return this;
            }

            public Builder setCountry(String country) {
                this.country = country;
                return this;
            }

            public Builder setFavoriteGame(ArrayList<String> games) {
                this.favoriteGames = favoriteGames;
                return this;
            }

            public Information build() {
                return new Information(this);
            }
        }
    }
}
