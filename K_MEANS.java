import java.util.Vector;



public class K_MEANS {
	private Cluster[] clusters;
    private int miter;//分类的个数
    
    private Vector mDataPoints = new Vector();
    private double mSWCSS;
    //初始化
    public K_MEANS(int k, int iter, Vector dataPoints) {
        clusters = new Cluster[k];
        for (int i = 0; i < k; i++) {
        	//簇的数组
            clusters[i] = new Cluster("类别" + i);
        }
        this.miter = iter;//迭代次数
        this.mDataPoints = dataPoints;//文本向量集
    }

    private void calcSWCSS() {
    	
    	
        double temp = 0;
        for (int i = 0; i < clusters.length; i++) {
            temp = temp + clusters[i].getSumSqr();
        }
        mSWCSS = temp;
    }

    public void startAnalysis() {
    
        setInitialCentroids();
        int n = 0;
     /* 为每个集群添加元素*/
        loop1: while (true) {
            for (int l = 0; l < clusters.length; l++)
            {
            	
                clusters[l].addDataPoint((DataPoint)mDataPoints.elementAt(n));
                
                
                n++;
                if (n >= mDataPoints.size())
                    break loop1;
            }
        }

        //重新计算集群
        calcSWCSS();

        //重新计算聚类质心2
        for (int i = 0; i < clusters.length; i++) {
            clusters[i].getCentroid().calcCentroid();
        }

        //重新计算集群
        calcSWCSS();

        /*迭代
*/        for (int i = 0; i < miter; i++) {
            //进入循环集群1
            for (int j = 0; j < clusters.length; j++) {
                for (int k = 0; k < clusters[j].getNumDataPoints(); k++) {

                    //选择第一个集群里的第一个元素
                    //获取当前的欧氏距离
                    double tempEuDt = clusters[j].getDataPoint(k).getCurrentEuDt();
                    Cluster tempCluster = null;
                    boolean matchFoundFlag = false;

                    //计算所有的距离
                    for (int l = 0; l < clusters.length; l++) {

             
                        if (tempEuDt > clusters[j].getDataPoint(k).testEuclideanDistance(clusters[l].getCentroid())) {
                            tempEuDt = clusters[j].getDataPoint(k).testEuclideanDistance(clusters[l].getCentroid());
                            tempCluster = clusters[l];
                            matchFoundFlag = true;
                        }
                       

                        }

                    /*如果匹配成功*/
       if (matchFoundFlag) {
                tempCluster.addDataPoint(clusters[j].getDataPoint(k));
                clusters[j].removeDataPoint(clusters[j].getDataPoint(k));
                        for (int m = 0; m < clusters.length; m++) {
                            clusters[m].getCentroid().calcCentroid();
                        }


                    /*    再一次计算*/
                        calcSWCSS();
                    }


                }
               
            }
        }
    }
/*获取结果*/
    public Vector[] getClusterOutput() {
        Vector v[] = new Vector[clusters.length];
        for (int i = 0; i < clusters.length; i++) {
            v[i] = clusters[i].getDataPoints();
        }
        return v;
    }

	/*
	 * 计算各组数据的方差
	 * */
    private void setInitialCentroids() {
      
    	/*计算质心*/
        double [] mP=new double[DataPoint.numOfData];
    
        for (int n = 1; n <= clusters.length; n++) {
        	
        	for(int i=0;i<DataPoint.numOfData;i++)
        	{
         
        		mP[i]=(((getMaxIValue(i) - getMinIValue(i)) / (clusters.length + 1)) * n)+ getMinIValue(i);
        	}
         
        	Centroid c1=new Centroid(mP);
        	//为每一个向量归属一个中心点
            clusters[n - 1].setCentroid(c1);
            c1.setCluster(clusters[n - 1]);
        }
    }
    

   
    private double getMaxIValue(int j)
    {
    	//得到某维度在全部文档文档中的最大值
    	double temp;
        temp = ((DataPoint) mDataPoints.elementAt(0)).getI(j);
        for (int i = 0; i < mDataPoints.size(); i++) {
            DataPoint dp = (DataPoint) mDataPoints.elementAt(i);
            temp = (dp.getI(j) > temp) ? dp.getI(j) : temp;
        }
        return temp;
    	
    }
    private double getMinIValue(int j)
    {	//得到某维度在全部文档文档中的最小值
	    	double temp = 0;
	    temp = ((DataPoint) mDataPoints.elementAt(0)).getI(j);
	    for (int i = 0; i < mDataPoints.size(); i++) {
	        DataPoint dp = (DataPoint) mDataPoints.elementAt(i);
	        temp = (dp.getI(j) < temp) ? dp.getI(j) : temp;
    }
    return temp;
    	
    }

    


  

    //获取k值
    public int getKValue() {
        return clusters.length;
    }

    //获取迭代次数
    public int getIterations() {
        return miter;
    }
//获取向量的个数
    public int getTotalDataPoints() {
        return mDataPoints.size();
    }

    public double getSWCSS() {
        return mSWCSS;
    }

    public Cluster getCluster(int pos) {
        return clusters[pos];
    }
}