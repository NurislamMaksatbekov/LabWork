package dataModel;

import entity.Candidate;
import util.FileService;

import java.util.List;

public class CandidateDataModel {
    List<Candidate> candidates;

    public CandidateDataModel() {
        this.candidates = FileService.readCandidatesFile();
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }
}
