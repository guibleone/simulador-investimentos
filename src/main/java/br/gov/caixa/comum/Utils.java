package br.gov.caixa.comum;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static String formatarDataHora(OffsetDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}
