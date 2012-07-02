package com.eliasnogueira.alm.bugzilla;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;


/**
 * Reescrita das funcoes da API do Bugzilla
 * 
 * @author Elias Nogueira <elias.nogueira@gmail.com>
 *
 */
public class BugzillaUtils {

    /**
     * Metodo para reportar um bug no Bugzilla
     * 
     * @param produto nome do projeto
     * @param componente nome do componente
     * @param sumario sumario do bug
     * @param descricao descricao do bug
     * @param versao versao do projeto/produto
     * @param sistemaOperacional sistema operaciona utilizado para o teste
     * @param plataforma plataforma utilizada para o teste
     * @param prioridade prioridade do defeito
     * @param severidade severidade do defeito
     * @param status status do defeito
     * @return id do bug cadastrado
     * @throws XmlRpcException erro na transmissao dos dados
     */
	public static int reportarIssue(String produto, String componente, String sumario, String descricao,
			String versao, String sistemaOperacional, String plataforma, String prioridade,
			String severidade, String status) {
		
		XmlRpcClient client = BugzillaInstance.getBugzillaInstance();
		int bugId = 0;
		Map<String, String> bugMap = new HashMap<String, String>();
		 
		bugMap.put("product", produto);
		bugMap.put("component", componente);
		bugMap.put("summary", sumario);
		bugMap.put("description", descricao);
		bugMap.put("version", versao);
		bugMap.put("op_sys", sistemaOperacional);
		bugMap.put("platform", plataforma);
		bugMap.put("priority", prioridade);
		bugMap.put("severity", severidade);
		bugMap.put("status", status);
		
		Object createResult;
        try {
            createResult = client.execute("Bug.create", new Object[]{bugMap});
            
            /*
             * Sequencia de codigo para pegar o retorno da api e retirar somente o id
             * Exemplo de retorno do Bubzilla: {id=4}
             */
            int tempIni = 4;
            int tempFinal = createResult.toString().length() -1;
            bugId = Integer.parseInt(createResult.toString().substring(tempIni, tempFinal));
            
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
		
		return bugId;
	}
	
	/**
	 * Metodo para submeter um anexo em um bug ja existente
	 * 
	 * @param bugID id de algum bug ja cadastrado
	 * @param arquivo caminho do arquivo
	 * @param nomeArquivo nome do arquivo que sera exibido no Bugzilla
	 * @param sumario descricao do anexo que sera exibido na ferramenta
	 * @throws XmlRpcException erro na transmissao dos dados
	 * @throws IOException erro ao fechar arquivo
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void adicionarAnexo(int bugID, String arquivo, String nomeArquivo, String sumario)  {
		XmlRpcClient client = BugzillaInstance.getBugzillaInstance();
        Map attach = new HashMap();
		
        try {
            
            attach.put("ids", bugID);
            attach.put("data", Base64.decodeBase64(arquivo));
            attach.put("file_name", nomeArquivo);
            attach.put("summary", sumario);
            attach.put("content_type", "image/png");
            
            client.execute("Bug.add_attachment", new Object[]{attach});

        } catch (XmlRpcException e) {
            e.printStackTrace();
        } 
	}
}
