package util;

public class ObterExtensaoArquivo {
    public String get(String nomeArquivo) {
        int pontofinal = nomeArquivo.lastIndexOf(".");
        if (pontofinal != -1) {
            return nomeArquivo.substring(pontofinal + 1);
        }
        return null;
    }
}
