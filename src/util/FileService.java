package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entity.Candidate;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class FileService {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = Paths.get("data/json/candidates.json");


//    public static List<User> readUser() {
//        Type type = new TypeToken<Map<String,List<User>>>(){}.getType();
//
//        try(Reader reader = new FileReader(user)) {
//            Map<String, List<User>> emplMap = GSON.fromJson(reader,type);
//            return emplMap.get("user");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return List.of();
//        }
//    }
public static List<Candidate> readCandidatesFile(){
    String json = "";
    try {
        json = Files.readString(PATH);
    }catch (IOException e){
        e.printStackTrace();
    }        return GSON.fromJson(json, new TypeToken<List<Candidate>>() {}.getType());
}

}
