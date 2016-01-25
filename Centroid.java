
public class Centroid {
	
	 private double []mP;//多维
	    private Cluster mCluster;
	    public static int length;
	  
	    public Centroid(double data[])
	    {
	    	this.mP=data;
	    	 this.length=mP.length;
	    }

	    public void calcCentroid() { 
	        int numDP = mCluster.getNumDataPoints();
	        
	        int i;
	        double []tempmP=new double [mP.length];
	        for( i=0;i<mP.length;i++)
	        {
	        	tempmP[i]=0;
	        }
	       
	        
	        for (i = 0; i < numDP; i++) {
	         
	        	for(int j=0;j<mP.length;j++)
	        	tempmP[j]=tempmP[j]+mCluster.getDataPoint(i).getI(j);
	            
	        }
	    
	        for( i=0;i<mP.length;i++)
	        {
	        	mP[i]=tempmP[i]/ numDP;
	        }
	        
	      
	        for( i=0;i<mP.length;i++)
	        {
	        	tempmP[i]=0;
	        }
	        for (i = 0; i < numDP; i++) {
	          
	        	mCluster.getDataPoint(i).calcDistance();
	        }
	        //再一次计算总的指标
	        mCluster.calcSumOfSquares();
	    }

	    public void setCluster(Cluster c) {
	        this.mCluster = c;
	    }

	   
	    public double getCi(int i)
	    {
	    	return mP[i];
	    }
	    

	  
	  
	    public Cluster getCluster() {
	        return mCluster;
	    }

	}