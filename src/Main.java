import labWork.VoteMachineApp;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new VoteMachineApp("localhost", 9878).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}