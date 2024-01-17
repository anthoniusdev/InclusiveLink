package util;

import org.mindrot.jbcrypt.BCrypt;

public class ServicoAutenticacao {
    // Gera um hash BCrypt para a senha indicada
    public static String hashSenha(String senha){
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    // Verifica se a senha fornecida corresponde à senha indicada
    public static boolean autentica(String senhaIndicada, String hashSenhaArmazenada){
        return BCrypt.checkpw(senhaIndicada, hashSenhaArmazenada);
    }
}
