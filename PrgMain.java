import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/*多维k-MEANS算法的实现
 * 刘文才,本多维k-means程序可以计算多维文档向量，从而自动归类文本
 * 本程序实现方法类似于二维k-means
 * */
public class PrgMain {
	
	static class Node
	{
		String str;
		int value;
		
		public static void sort(Node []node)
		{
			
                for(int i=0;i<node.length-1;i++){
                        for(int j=i+1;j<node.length;j++){
                                if (node[i].value<node[j].value){
                                        Node temp=node[i];
                                        node[i]=node[j];
                                        node[j]=temp;
                                }
                        }
                }
             
        }
	}
	private static String[] strs;
	public static void main(String args[]) {
		
		//读取文本
		final String [] resources={
				"在这一个星期的日子里，可以说是苦多于甜，但是我可以学到很多的东西，这不仅巩固了以前所学过的知识，而且学到了很多在书本上所没有学到过的知识。一周的课程设计结束了，在这次的课程设计中不仅检验了我所学习的知识，也培养了我如何去努力做好一件事。",
				"在这次课程设计过程中，与同学分工设计，和同学们相互探讨，相互学习，相互监督。我学会了合作，学会了运筹帷幄，也学会了宽容与理解。在课程设计的过程中遇到的问题，可以说是困难重重，这与我平时没有做过相关的练习有关，毕竟做的不多，难免会遇到各种各样的问题",
				"同时也发现了自己的不足之处。在这个过程中，我深刻感觉到自己对以前所学过的知识理解得不够深刻，掌握得不够牢固，没有多少经验。比如说使用了第三方控件界面布局无法较好调整，不懂得调试出现错误时较好的进行改错",
				"它的可玩性究其他MOD如隐风之龙、神龙天舞、兵临城下来说十分差，（几个新单位如雌鹿直升机其实原游戏中都有，只不过未启用。利用冷晓辉的那个INI编辑器就能启用出来）但因为它年代较久远，又只有一个人改造，这个也不是不能体谅。但它将很多中国单位造成了IMBA，让很多愤青玩家一玩就爱不释手，也导致了红色警戒长期遭受其他RTS玩家唾骂，蒙受了一个糟糕的名誉",
				"中国这个“独特”阵营的出现，严重破坏了平衡性，很多人只知道利用解放军海之类的IMBA虐待电脑，上面说的中国对中国、盟军对联军似乎没什么人注意，这也是共和国之辉在大部分红色警戒玩家那里蒙受骂名的重要原因。此外，由于共辉是基于红警II最初的版本1.000修改的，保留了空投建筑、间谍自偷等等BUG，这也是共辉的缺憾之一。",
				"共和国之辉的最大缺憾就是没有升级版，放眼中国的其它MOD，如早期的狂狮怒吼、中华崛起，后来的兵临城下、反恐联盟、隐风之龙、神龙天舞等等MOD，都在原有的基础上进行过多次完善，甚至全面的更新，唯独共和国之辉的作者Lord Hero单单就发布了这一个最初的版本而没有对其进行升级。当然，即使如此，在玩家数量与全国网吧电脑安装各种MOD数量上，其他一再升级的MOD仍然远不如共和国之辉受欢迎，二者间完全没有可比性，甚至全国各大对战平台根本就不接受共和国之辉以外的MOD，这是个很大的遗憾",
				"共和国之辉虽然是个极其粗糙的MOD，并且导致了红色警戒的名声败坏，但它毕竟起到了一定的宣传推动作用（尽管发展来的玩家基本上玩了一个星期就离别了红警）；也算为MOD事业开了一个头。成了中国的红色警戒MOD始祖之一。我们既不能过分强调它的不足，刻意抹杀它的功绩，也不能为之一味吹捧。对待共和国之辉，我们要辩证地看待",
				"期待共和国之辉进一步完善，目前共和国之辉吧正在讨论进行共和国之辉2的制造，希望作者们能够保证游戏的平衡，不要做得中国独大。（如果你对日本之类的国家看不顺眼，给它个邪恶但不弱的科技，再给中国比较强大正义的科技）"
				
	};
		
			
			
	

				Node [][]node=new Node[resources.length][];
				
				for(int i=0;i<resources.length;i++)
				{
					//System.out.print("\n第+"+i+"行\n");
					Map<String, Integer> map;
					map=new ReverseMatching(resources[i]).getResult();
					Set<String>key=map.keySet();
					int j;
					Iterator<String> it;
					node[i]=new Node[map.size()];
					
					for( j=0,it=key.iterator();it.hasNext();j++)
					{
						
						node[i][j]=new Node();
						String s=(String)it.next();
						int p=(Integer)map.get(s);
						//System.out.print(""+s+"__");
						node[i][j].str=s;//提取键值对
						node[i][j].value=p;
						
					}
					
				}
				//按照权值排序
				for(int i=0;i<node.length;i++)
					Node.sort(node[i]);
				
				//找到含关键字对多的那个长度
				int all=0,c=0,count=0;
				for(int i=0;i<node.length;i++)
				{
					for(int j=0;j<node[i].length;j++)
					{
							c++;
							count++;
					}
					if(all<c)
						all=c;
					c=0;
					
				}
				//存储所有的权值，没有的用0取代
				double[][]allvalues=new double[node.length][];
				
				strs=new String[count];//存储所有的关键字（含重复）
				c=0;
				for(int i=0;i<node.length;i++)
				{
					//values[i]=new int[node[i].length];
					for(int j=0;j<node[i].length;j++)
					{
						strs[c++]=node[i][j].str;//所有的
						
					}
					
				
				}
				
				for(int i=0;i<node.length;i++)
				{
					//values[i]=new int[node[i].length];
					
						allvalues[i]=new double[count];
						for(int k=0;k<strs.length;k++)
						{
							for(int j=0;j<node[i].length;j++)
							if(node[i][j].str.equals(strs[k])==true)
							{
								//如果当前关键词在所有的中出现过
								allvalues[i][k]+=node[i][j].value;
								break;
							}
							else
								allvalues[i][k]=0;
						}
									
					
					
					
				}
				
		
		
		
		for(int i=0;i<allvalues.length;i++)
		{
			for(int j=0;j<allvalues[i].length;j++)
			{
				System.out.print(allvalues[i][j]+" ");
			}
			System.out.println();
		}
		
		
        //定义向量集
		Vector<DataPoint> dataPoints = new Vector<DataPoint>();
        //输入向量数据
		for(int i=0;i<node.length;i++)
		{
			try
			{
			 dataPoints.add(new DataPoint(allvalues[i],node[i][0].str));
			}
			catch(Exception e)
			{
				
			}
		}

        
        
        
        //输入k，以及定义迭代次数，输入数据向量集
        K_MEANS jca = new K_MEANS(4, 10000, dataPoints);
        //开始分析
        jca.startAnalysis();

        	//获取输出向量
        Vector[] v = jca.getClusterOutput();
        for (int i = 0; i < v.length; i++)
        {
            Vector<?> tempV = v[i];
            System.out.println("-----------类别" + i + "---------");
            Iterator<?> iter = tempV.iterator();
            while (iter.hasNext()) {
                DataPoint dpTemp = (DataPoint) iter.next();
                //已一定格式输出
                System.out.print(dpTemp.getObjName()+"[");
                for(int j=0;j<DataPoint.numOfData;j++)
                	
                System.out.print(dpTemp.getI(j)+"  ");
                System.out.println("]");
            }
        }
    }
}
