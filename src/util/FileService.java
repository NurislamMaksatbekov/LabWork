package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entity.Candidate;
import entity.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class FileService {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATHC = Paths.get("data/json/candidates.json");
    private static final Path PATHU = Paths.get("data/json/user.json");


    public static void writeUsers(List<User> users){
        String str = GSON.toJson(users);
        try {
            byte[] strToBytes = str.getBytes();
            Files.write(PATHU, strToBytes);
        }catch (IOException e){
            e.printStackTrace();        }
    }

    public static void writeCandidates(List<Candidate> candidates){
        String str = GSON.toJson(candidates);
        try {
            byte[] strToBytes = str.getBytes();
            Files.write(PATHC, strToBytes);
        }catch (IOException e){
            e.printStackTrace();        }
    }

public static List<Candidate> readCandidatesFile(){
    String json = "";
    try {
        json = Files.readString(PATHC);
    }catch (IOException e){
        e.printStackTrace();
    }        return GSON.fromJson(json, new TypeToken<List<Candidate>>() {}.getType());
}
    public static List<User> readUser(){
        String json = "";
        try {
            json = Files.readString(PATHU);
        }catch (IOException e){
            e.printStackTrace();
        }        return GSON.fromJson(json, new TypeToken<List<User>>() {}.getType());
    }

}
