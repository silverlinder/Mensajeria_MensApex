package es.ja.csaludad.sas.mensapex.rest.factory;

import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Meta;
import org.hl7.fhir.r4.model.OperationOutcome;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public final class OperationOutcomeFactory {

    private static final String DEFAULT_PROFILE = "profile";//Esto es temporal, me tengo que informar pd: Fernando

    private OperationOutcomeFactory() {
    }

    public static OperationOutcome fromHttpStatus(
            int httpStatus,
            String diagnostics,
            List<String> expressions,
            String text
    ) {
        return create(
                OperationOutcomeType.fromHttpStatus(httpStatus),
                diagnostics,
                expressions,
                text
        );
    }


    private static OperationOutcome create(
            OperationOutcomeType type,
            String diagnostics,
            List<String> expressions,
            String text
    ) {
        OperationOutcome outcome = new OperationOutcome();

        outcome.setMeta(buildMeta());

        OperationOutcome.OperationOutcomeIssueComponent issue = getOperationOutcomeIssueComponent(type, diagnostics, text);

        if (expressions != null) {
            expressions.stream()
                    .filter(expression -> expression != null && !expression.isBlank())
                    .forEach(issue::addExpression);
        }

        outcome.addIssue(issue);
        return outcome;
    }

    private static OperationOutcome.OperationOutcomeIssueComponent getOperationOutcomeIssueComponent(OperationOutcomeType type, String diagnostics, String text) {
        OperationOutcome.OperationOutcomeIssueComponent issue =
                new OperationOutcome.OperationOutcomeIssueComponent();

        issue.setCode(type.getIssueType());
        issue.setSeverity(type.getSeverity());

        if (diagnostics != null && !diagnostics.isBlank()) {
            issue.setDiagnostics(diagnostics);
        }

        if (text != null && !text.isBlank()) {
            CodeableConcept details = new CodeableConcept();
            details.setText(text);
            issue.setDetails(details);
        }
        return issue;
    }

    private static Meta buildMeta() {
        Meta meta = new Meta();
        meta.setLastUpdated(Date.from(Instant.now()));
        meta.addProfile(DEFAULT_PROFILE);
        return meta;
    }
}