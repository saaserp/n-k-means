import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;


public class ReverseMatching {
	//逆向文本匹配算法
	String str;
	

	Map<String,Integer> map=new HashMap<String,Integer>();
	public ReverseMatching(String str)
	{
		this.str=str;
	
		String []s=getProperty(str);
		//List<Map<String,Integer>> list=new ArrayList<Map<String,Integer>>();
		//定义键值对来存储数据
		
		
		
		//存储属性，以及对应的个数
		for(int i=0;i<s.length;i++)
		{
			int num=0;
			num=getCount(s[i]);
			if(num>1)//把出现大于1的放进去
			map.put(s[i], num);
			
		}
		
		
		
	}
	
	public Map<String,Integer>getResult()
	{
		
		
		return map;
		
	}
	private int getCount(String s) {
		// TODO Auto-generated method stub
		//查找字符串出现的个数
		   int count=0;
	        int index=0;
	        while(true){
	             index=str.indexOf(s,index+1);
	             if(index>0){
	                 count++;
	             }else{
	                 break;
	             }
	        }
		return count;
	}

	public static String[] getProperty(String str0)
	{
		//str0表示原始文本
		//读取分隔词
		File file =new File("C:/Documents and Settings/JSJ/桌面/VideoRecorder-master25/K-MEANS/src/pause.txt");//这是分割词
		Hashtable<String,String> pause=new Hashtable<String,String>();
		String[] temps;
		ArrayList<String> list=new ArrayList<String>();
		ArrayList<String> list1=new ArrayList<String>();
		String tempStr;
		//如果长度为0就直接返回空
		if(str0.length()==0)
		return null;
		else
		{
			try
			{
				BufferedReader bw=new BufferedReader(new FileReader(file));
				String line=null;
				while((line=bw.readLine())!=null)
				{
					if(!line.equals(""))
					pause.put(line,line);
				}
				bw.close();
			}catch(IOException e)
			{
				e.printStackTrace();
			}
			
			Set<String> keys=pause.keySet();
			for(String key: keys)
			{
				int i=-1;
				if((i=str0.indexOf(key))!=-1)
					str0=str0.replace(key, ",");//把所有的出现了分割符的换成逗号
			}
			temps=str0.split(",");
			for(int i =0;i<temps.length;i++)
			{
				if(temps[i].equals("")!=true)
					list.add(temps[i]);
				
			}
			for(int i=0;i<list.size();i++)
			{
				tempStr=list.get(i);
				if(tempStr.length()<4)
				{
					if(tempStr.length()>=2)
					list1.add(tempStr);
				}
				else
				{
					tempStr=list.get(i).substring(list.get(i).length()-4, list.get(i).length());
					
					list1.add(tempStr);
				}
			}
			
		
			
			return list1.toArray(new String[list1.size()]);
			
			
			
			
		}
	}




}
