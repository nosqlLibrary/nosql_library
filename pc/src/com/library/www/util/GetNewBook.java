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
		        
		        String bname = jsonObject.getString("书名");
		        String bproduction = jsonObject.getString("出版社");
		        String bauthor = jsonObject.getString("作者");
		        String publishdate = jsonObject.getString("出版年");
		        String price = jsonObject.getString("定价");
		        String pagenum = jsonObject.getString("页数");
		        String introduction = jsonObject.getString("简介");
		        
		        //将获取到的信息封装到book实体中
		        
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
	       
		//请求的url 
	    URL url = null;      
	    
	    //建立的http链接  
	    @SuppressWarnings("unused")
		HttpURLConnection httpConn = null;  
	    
	    //请求的输入流
	    BufferedReader in = null;   
	    
	    //输入流的缓冲
	    StringBuffer sb = new StringBuffer(); 
	    
	    try{     
		     url = new URL(urlStr);     
		     
		     in = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8") ); 
		     
		     String str = null;  
		    
		     //一行一行进行读入
		     while((str = in.readLine()) != null) {    
		        sb.append( str );     
	         }     
        } catch (Exception ex) {   
	            
        } finally{    
	         try{             
		          if(in!=null) {  
		           in.close(); //关闭流    
	              }     
            }catch(IOException ex) {      
	        
            }     
        }     
        String result =sb.toString();     
        return result;    
    } 
}
