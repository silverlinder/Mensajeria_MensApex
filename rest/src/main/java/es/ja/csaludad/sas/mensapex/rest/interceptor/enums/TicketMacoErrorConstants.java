package es.ja.csaludad.sas.mensapex.rest.interceptor.enums;

public class TicketMacoErrorConstants {
    //constantes
    public static final String MALFORMED_TICKET = "Ticket malformed";
    public static final String EXPIRED_TICKET = "Ticket expired";
    public static final String FUTURE_TICKET = "Future ticket received";
    public static final String PERMISSION_TICKET = "Ticket Permission are not enough";
    public static final String SIGNATURE_TICKET = "Ticket Signature invalid";

    private TicketMacoErrorConstants(){}
}
