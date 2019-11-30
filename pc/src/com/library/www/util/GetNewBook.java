package com.library.www.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.library.www.po.Book;

public class GetNewBook {
	public static Book getBookFromNet(String isbn) {
		String content = getURLContent("http://39.106.45.30/book/search.jsp?isbn="+isbn);
		System.out.println(content);
		Book findbook = new Book();
		//content="[" + content + "]" ;
		if(!content.equals("")) {
			try {
		        JSONObject jsonObject = JSONObject.parseObject(content);
		        
		        String bname = jsonObject.getString("����");
		        String bproduction = jsonObject.getString("������");
		        String bauthor = jsonObject.getString("����");
		        String publishdate = jsonObject.getString("������");
		        String price = jsonObject.getString("����");
		        String pagenum = jsonObject.getString("ҳ��");
		        String introduction = jsonObject.getString("���");
		        
		        //����ȡ������Ϣ��װ��bookʵ����
		        
		        findbook.setIsbn(isbn);
		        findbook.setBname(bname);
		        if(bproduction != null)
		        	findbook.setBproduction(bproduction);
		        if(bauthor != null)
		        	findbook.setBauthor(bauthor);
		        if(publishdate != null)
		        	findbook.setPublishDate(publishdate);
		        if(price != null)
		        	findbook.setPrice(price);
		        if(pagenum != null) {
		        	int page = Integer.parseInt(pagenum);
		        	findbook.setPageNum(page);
		        }
		        if(introduction != null) 
		        	findbook.setIntroduction(introduction);
		        
		        System.out.println(findbook);
			} catch (JSONException e) {
		        
		        e.printStackTrace();
		    }
			
		}
		return findbook;
		
	}
	
	public static String getURLContent(String urlStr) {               
	       
		//�����url 
	    URL url = null;      
	    
	    //������http����  
	    @SuppressWarnings("unused")
		HttpURLConnection httpConn = null;  
	    
	    //�����������
	    BufferedReader in = null;   
	    
	    //�������Ļ���
	    StringBuffer sb = new StringBuffer(); 
	    
	    try{     
		     url = new URL(urlStr);     
		     
		     in = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8") ); 
		     
		     String str = null;  
		    
		     //һ��һ�н��ж���
		     while((str = in.readLine()) != null) {    
		        sb.append( str );     
	         }     
        } catch (Exception ex) {   
	            
        } finally{    
	         try{             
		          if(in!=null) {  
		           in.close(); //�ر���    
	              }     
            }catch(IOException ex) {      
	        
            }     
        }     
        String result =sb.toString();     
        return result;    
    } 
}
