package com.eliasnogueira.alm.util;

/**
 * Classe de suporte para o envio de resultados de teste e abertura de bugs.
 * Toda vez que um erro ocorre dentro de um bloco 'try' esse classe Ž invocada e recebe os parametros sobre o erro no seu construtor
 * ou atraves dos setters
 * 
 * @author Elias Nogueir <elias.nogueira@gmail.com>
 */
public class ReportaErro {

    /**
     * Recebe a imagem de evidencia. Procure passar uma string Base64
     */
    private String evidencia;
    
    /**
     * Recebe a descricao da exception lancada
     */
    private String stackTrace;
    
    public ReportaErro(String evidencia, String stackTrace) {
        this.evidencia = evidencia;
        this.stackTrace = stackTrace; 
    }

    public String getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(String evidencia) {
        this.evidencia = evidencia;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

}
