package com.tjrac.solr;

import java.io.IOException;
import java.util.Iterator;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * ��������ɾ�Ĳ�
 * @author Administrator
 *
 */
public class IndexManager {
	
	
	
	/**
	 * ���� / �޸� ����
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	@Test
	public void insertAndUpdateIndex() throws SolrServerException, IOException{
		//���� httpSolrServer -- �ҵ���������url
		HttpSolrServer server = new HttpSolrServer("http://localhost:8080/solr");
		
		//����document����  --��id������ ����� ��id�Ѿ����� ���޸�
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "c1001");
		doc.addField("name", "solr test");
		server.add(doc);
		
		server.commit();
	}
	
	
	
	/**
	 * ɾ������
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	@Test
	public void deleteIndex() throws SolrServerException, IOException{
		HttpSolrServer server = new HttpSolrServer("http://localhost:8080/solr");
		
		//ͨ��idɾ������
		server.deleteById("c1001");
		
		//����������ɾ��
		server.deleteByQuery("id:c1001");
		
		//ɾ��ȫ��
		server.deleteByQuery("*:*");
		
		server.commit();
	}
	
	

	
	
}
