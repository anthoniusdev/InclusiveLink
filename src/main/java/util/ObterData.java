package util;

import java.time.LocalDate;

public class ObterData {
    private final LocalDate dataAtual = LocalDate.now();
    private final int anoAtual = dataAtual.getYear();
    private final int mesAtual = dataAtual.getMonthValue();
    private final int diaAtual = dataAtual.getDayOfMonth();

    public int getAnoAtual() {
        return anoAtual;
    }

    public int getMesAtual() {
        return mesAtual;
    }

    public int getDiaAtual() {
        return diaAtual;
    }
}
