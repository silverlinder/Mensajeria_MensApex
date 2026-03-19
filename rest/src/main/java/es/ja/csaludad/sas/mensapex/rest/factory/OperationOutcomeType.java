package es.ja.csaludad.sas.mensapex.rest.factory;

import org.hl7.fhir.r4.model.OperationOutcome;

import java.util.Arrays;

public enum OperationOutcomeType {

    BAD_REQUEST(
            400,
            OperationOutcome.IssueType.INVALID,
            OperationOutcome.IssueSeverity.ERROR
    ),
    UNAUTHORIZED(
            401,
            OperationOutcome.IssueType.SECURITY,
            OperationOutcome.IssueSeverity.ERROR
    ),
    FORBIDDEN(
            403,
            OperationOutcome.IssueType.FORBIDDEN,
            OperationOutcome.IssueSeverity.ERROR
    ),
    NOT_FOUND(
            404,
            OperationOutcome.IssueType.NOTFOUND,
            OperationOutcome.IssueSeverity.ERROR
    ),
    METHOD_NOT_ALLOWED(
            405,
            OperationOutcome.IssueType.NOTSUPPORTED,
            OperationOutcome.IssueSeverity.ERROR
    ),
    CONFLICT(
            409,
            OperationOutcome.IssueType.CONFLICT,
            OperationOutcome.IssueSeverity.ERROR
    ),
    UNPROCESSABLE_ENTITY(
            422,
            OperationOutcome.IssueType.PROCESSING,
            OperationOutcome.IssueSeverity.ERROR
    ),
    INTERNAL_SERVER_ERROR(
            500,
            OperationOutcome.IssueType.EXCEPTION,
            OperationOutcome.IssueSeverity.FATAL
    ),
    BAD_GATEWAY(
            502,
            OperationOutcome.IssueType.EXCEPTION,
            OperationOutcome.IssueSeverity.ERROR
    ),
    SERVICE_UNAVAILABLE(
            503,
            OperationOutcome.IssueType.THROTTLED,
            OperationOutcome.IssueSeverity.ERROR
    ),
    GATEWAY_TIMEOUT(
            504,
            OperationOutcome.IssueType.TIMEOUT,
            OperationOutcome.IssueSeverity.ERROR
    );

    private final int httpStatus;
    private final OperationOutcome.IssueType issueType;
    private final OperationOutcome.IssueSeverity severity;

    OperationOutcomeType(
            int httpStatus,
            OperationOutcome.IssueType issueType,
            OperationOutcome.IssueSeverity severity
    ) {
        this.httpStatus = httpStatus;
        this.issueType = issueType;
        this.severity = severity;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public OperationOutcome.IssueType getIssueType() {
        return issueType;
    }

    public OperationOutcome.IssueSeverity getSeverity() {
        return severity;
    }

    public static OperationOutcomeType fromHttpStatus(int httpStatus) {
        return Arrays.stream(values())
                .filter(value -> value.httpStatus == httpStatus)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("HTTP status no soportado: " + httpStatus));
    }
}