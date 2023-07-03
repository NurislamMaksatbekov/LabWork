package dataModel;

import entity.Candidate;
import util.FileService;

import java.util.List;

public class CandidatesDataModel {
    List<Candidate> candidates;

    public CandidatesDataModel() {
        this.candidates = FileService.readCandidatesFile();
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }
}
