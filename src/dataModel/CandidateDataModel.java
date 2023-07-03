package dataModel;

import entity.Candidate;

import java.util.Optional;

public class CandidateDataModel {
    private Optional <Candidate> candidate;

    public CandidateDataModel(Optional<Candidate> candidate) {
        this.candidate = candidate;
    }

    public Optional<Candidate> getCandidate() {
        return candidate;
    }

    public void setCandidate(Optional<Candidate> candidate) {
        this.candidate = candidate;
    }
}
