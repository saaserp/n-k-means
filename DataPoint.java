
public class DataPoint {
	
	 private double []mP;//多维
	    private String mObjName;//文档代表的名称
	    private Cluster mCluster;
	    private double mEuDt;
	    public static int numOfData=2;//维度


	    public DataPoint(double data[],String name)
	    {
	    	//多维属性
	    	mP=data;
	    	this.numOfData=data.length;//归类的个数
	    	this.mObjName = name;
	        this.mCluster = null;
	    	
	    }
	    

	    public void setCluster(Cluster cluster) {
	        this.mCluster = cluster;
	        //calcEuclideanDistance();
	        calcDistance();
	    }

	
	    public void calcDistance()
	    {
	    	//获取当前点和其他点的欧几里得距离
	    	double item;
	    	
	    	 for(int i=0;i<this.numOfData;i++)
	    	 {
	    		 item=Math.sqrt(Math.pow((mP[i] - mCluster.getCentroid().getCi(i)),2));
	    		 mEuDt+=item;
	    	 }
	    }
	  
	    public double testEuclideanDistance(Centroid c) {
	      
	        double temp=0;
	        
	        for(int i=0;i<this.numOfData;i++)
	        {
	        	
	        	temp+=Math.sqrt(Math.pow((mP[i] - c.getCi(i)),2));
	        }
	        return temp;
	    }

	    public double getI(int i)
	    {
	    	return mP[i];
	    }

	    
	    public Cluster getCluster() {
	        return mCluster;
	    }

	    public double getCurrentEuDt() {
	        return mEuDt;
	    }

	    public String getObjName() {
	        return mObjName;
	    }

	}