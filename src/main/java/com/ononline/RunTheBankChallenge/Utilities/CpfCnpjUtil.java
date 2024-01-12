package com.ononline.RunTheBankChallenge.Utilities;

import com.ononline.RunTheBankChallenge.Exceptions.InvalidCpfCnpjException;

import java.util.InputMismatchException;

/**
 * Classe utilitária para validação de CPF e CNPJ.
 */
public class CpfCnpjUtil {
    
    /**
     * Valida se um CPF ou CNPJ é válido.
     * @param cpfCnpj O CPF ou CNPJ a ser validado.
     * @throws InvalidCpfCnpjException Lançada se o CPF ou CNPJ for inválido.
     */
    public static void valida(String cpfCnpj){
        if (!isCPF(cpfCnpj) && !isCNPJ(cpfCnpj))
            throw new InvalidCpfCnpjException();
    }
    
    /**
     * Verifica se um CPF é válido.
     * @param cpfCnpj O CPF a ser verificado.
     * @return true se o CPF é válido, false caso contrário.
     */
    private static boolean isCPF(String cpfCnpj) {
        if (cpfCnpj.equals("00000000000") ||
                cpfCnpj.equals("11111111111") ||
                cpfCnpj.equals("22222222222") || cpfCnpj.equals("33333333333") ||
                cpfCnpj.equals("44444444444") || cpfCnpj.equals("55555555555") ||
                cpfCnpj.equals("66666666666") || cpfCnpj.equals("77777777777") ||
                cpfCnpj.equals("88888888888") || cpfCnpj.equals("99999999999") ||
                (cpfCnpj.length() != 11))
            return(false);
        
        char dig10, dig11;
        int sm, i, r, num, peso;
        
        try {
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                num = cpfCnpj.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48);
            
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = cpfCnpj.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);
            
            return ((dig10 == cpfCnpj.charAt(9)) && (dig11 == cpfCnpj.charAt(10)));
        } catch (InputMismatchException erro) {
            return(false);
        }
    }
    
    /**
     * Verifica se um CNPJ é válido.
     * @param cpfCnpj O CNPJ a ser verificado.
     * @return true se o CNPJ é válido, false caso contrário.
     */
    private static boolean isCNPJ(String cpfCnpj) {
        if (cpfCnpj.equals("00000000000000") || cpfCnpj.equals("11111111111111") ||
                cpfCnpj.equals("22222222222222") || cpfCnpj.equals("33333333333333") ||
                cpfCnpj.equals("44444444444444") || cpfCnpj.equals("55555555555555") ||
                cpfCnpj.equals("66666666666666") || cpfCnpj.equals("77777777777777") ||
                cpfCnpj.equals("88888888888888") || cpfCnpj.equals("99999999999999") ||
                (cpfCnpj.length() != 14))
            return(false);
        
        char dig13, dig14;
        int sm, i, r, num, peso;
        
        try {
            sm = 0;
            peso = 2;
            for (i=11; i>=0; i--) {
                num = cpfCnpj.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }
            
            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else dig13 = (char)((11-r) + 48);
            
            sm = 0;
            peso = 2;
            for (i=12; i>=0; i--) {
                num = cpfCnpj.charAt(i)- 48;
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }
            
            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else dig14 = (char)((11-r) + 48);
            
            return ((dig13 == cpfCnpj.charAt(12)) && (dig14 == cpfCnpj.charAt(13)));
        } catch (InputMismatchException erro) {
            return(false);
        }
    }
}
