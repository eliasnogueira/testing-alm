package com.eliasnogueira.alm.mantis;

import java.net.MalformedURLException;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.mantisbt.connect.IMCSession;
import org.mantisbt.connect.MCException;
import org.mantisbt.connect.model.IProject;
import org.mantisbt.connect.model.Issue;
import org.mantisbt.connect.model.MCAttribute;

/**
 * Reescrita das funcoes da API do Mantis
 * 
 * @author Elias Nogueira <elias.nogueira@gmail.com>
 *
 */
public class MantisUtils {

	/**
	 * Reporta um novo bug
	 * 
	 * @param sumario sumario do bug
	 * @param descricao descricao do Bug
	 * @param categoria categoria do Bug
	 * @param informacaoAdicional qualquer informacao adicional que vai no campo 'Additional Information' no Mantis
	 */
	public static long reportaBug(String projeto, String sumario, String descricao, String categoria, String informacaoAdicional) {
		IMCSession sessao = null;
		long bugID = 0;
		
		try {
			// efetua a conexao com o Mantis atraves do Singleton
			sessao = MantisInstance.getSessao();
			
			// objeto que representa um projeto no Mantis
			IProject projetoMantis = sessao.getProject(projeto);
			
			// objeto que representa uma issue (bug) no Mantis
            Issue issue = new Issue();
            
            issue.setProject(new MCAttribute(projetoMantis.getId(), projetoMantis.getName()));
            issue.setAdditionalInformation(null);
            issue.setOs(System.getProperty("os.name"));
            issue.setOsBuild(System.getProperty("os.version"));
            issue.setPlatform(System.getProperty("os.arch"));
            issue.setSeverity(new MCAttribute(70, "crash"));
            issue.setReproducibility(new MCAttribute(10, "always"));
            
            // abaixo o sumario sera apresentado com a data. Remova o sumario em execução fora de testes
            issue.setSummary(sumario + new Date());
            issue.setDescription(descricao);
            issue.setCategory(categoria);
            issue.setPriority(new MCAttribute(40, "high"));
            issue.setAdditionalInformation(informacaoAdicional);
            
            // submete o bug no Mantis
            bugID = sessao.addIssue(issue);     
            
		} catch (MalformedURLException e) {
			System.err.println("Erro na URL de acesso ao Mantis");
			e.printStackTrace();
		} catch (MCException e) {
			System.err.println("Erro na comunicacao com o Mantis");
			e.printStackTrace();
		}
		
        return bugID;
	}
	
	/**
	 * Adiciona um anexo ao bug
	 * 
	 * @param bugID id do bug que recebera o anexo
	 * @param nomeArquivo nome do arquivo que aparecera no Mantis
	 * @param evidencia anexo que sera anexado
	 */
	public static void adicionaAnexo(long bugID, String nomeArquivo, String evidencia) {
	    IMCSession sessao = null;
	    
	    try {
            sessao = MantisInstance.getSessao();
            sessao.addIssueAttachment(bugID, nomeArquivo, "image/png", Base64.decodeBase64(evidencia));
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (MCException e) {
            e.printStackTrace();
        }
	}
}
