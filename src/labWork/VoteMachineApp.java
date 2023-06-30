package labWork;

import com.sun.net.httpserver.HttpExchange;
import dataModel.CandidateDataModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import server.BasicServer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class VoteMachineApp extends BasicServer {


    public VoteMachineApp(String host, int port) throws IOException {
        super(host, port);
        registerGet("/", this::candidatesHandler);
    }

    private void candidatesHandler(HttpExchange exchange) {
        renderTemplate(exchange, "candidates.ftlh", getCandidatesDataModel());
    }

    private CandidateDataModel getCandidatesDataModel() {
        return new CandidateDataModel();
    }
}
