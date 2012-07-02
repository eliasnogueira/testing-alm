package com.eliasnogueira.alm.testlink;

import java.net.MalformedURLException;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionStatus;
import br.eti.kinoshita.testlinkjavaapi.model.Build;
import br.eti.kinoshita.testlinkjavaapi.model.ReportTCResultResponse;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;

import com.eliasnogueira.alm.util.ALMUtils;

/**
 * Reescrita das funcoes da API do Testlink
 * 
 * @author Elias Nogueira <elias.nogueira@gmail.com>
 */
public class TestlinkUtils {
	
    /**
     * Metodo para reportar o resultado do teste automatizado para o Testlink
     * Se um bug nao existir o parametro bugID deve ser null e o resultado do caso de teste ficara 'com sucesso'
     * Se um bug existir insira o codigo do bug e o resultado do caso de teste ficara com 'falha'
     * 
     * Para que seja possivel associar um bug no Testlink e necessario fazer a integracao com uma ferramenta de bugtracker
     * http://teamst.org/_tldoc/1.9/tl-bts-howto.pdf
     * 
     * @see br.eti.kinoshita.testlinkjavaapi.model.ReportTCResultResponse
     * 
     * @param projetoTeste nome do Projeto de Teste
     * @param planoTeste nome do Plano de Teste
     * @param casoTeste nome do Caso de Teste
     * @param nomeBuild nome da Build de Teste
     * @param nota nota que será adicionada
     * @param resultado resultado do teste executado
     * @param bugID Id do bug que sera associado no Testlink
     * 
     * @throws TestLinkAPIException erro de comunicacao na submissao dos resultados
     * @throws MalformedURLException 
     */
    public static int reportaResultadoExecucao(String projeto, String planoTeste, String casoTeste, String nota, String bugID) {
        
        // Pega a conexao com o Testlink
        TestLinkAPI testlink = TestlinkInstance.getInstance();

        TestPlan plan = testlink.getTestPlanByName(planoTeste, projeto);
        Integer testcase = testlink.getTestCaseIDByName(casoTeste, null, null, null);
        Build build = testlink.getLatestBuildForTestPlan(plan.getId());
        ReportTCResultResponse result = null;
        
        if (bugID != null) {
            result = testlink.reportTCResult(testcase, 0, plan.getId(), ExecutionStatus.FAILED, build.getId(), null, nota, false, bugID, 0, null, null, false);
        } else {
        	// submete os dados sem o bug associado
            result = testlink.reportTCResult(testcase, 0, plan.getId(), ExecutionStatus.PASSED, build.getId(), null, nota, false, null, 0, null, null, false);
            
        }
        return result.getExecutionId();
    }
    
    /**
     * Anexa uma evidencia de execucao. E necessario ter executado um Caso de Teste primeiro para submeter este anexo
     * 
     * @param execucao id da execucao do caso de teste
     * @param arquivo arquivo que sera anexado
     * @param titulo titulo que aparecera no anexo no Testlink
     * @param descricao descricao do anexo que aparecera no Testlink
     * @param nomeArquivo nome do arquivo que aparecera no testlink
     * 
     * TO-DO: pensar em outra solucao mais elegante depois sem precisar salvar arquivo no TEMP
     */
    public static void anexaEvidenciaExecucao(Integer execucao, String arquivo, String titulo, String descricao, String nomeArquivo) {
        TestLinkAPI testlink = TestlinkInstance.getInstance();
        
        testlink.uploadExecutionAttachment(execucao, titulo, descricao, nomeArquivo, "image/png", ALMUtils.decodeImageToContent(arquivo));
    }
    
}
