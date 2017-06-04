import java.util.Scanner;

public class SocialNetworkConnectivity {
    private int[] parent;
    private int[] size;
    private int counts;

    public SocialNetworkConnectivity(int n) {
        this.parent = new int[n]; 
        this.size = new int[n];
        this.counts = n;
        for(int i=0;i<n;i++){
            parent[i]=i;
            size[i]=1;
        }
    }
    
    public int root(int i){
        
        while(i!=parent[i]){
            i=parent[i];
        }
        
        return i;
    }
    
    public void union(int i,int j){                
        int iroot = root(i);
        int jroot = root(j);
                
        if(size[i]>size[j]){
            parent[jroot]=iroot;
            size[iroot]+=size[jroot];
        }else{
            parent[iroot]=jroot;
            size[jroot]+=size[iroot];
        }
        counts--;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            SocialNetworkConnectivity snc = new SocialNetworkConnectivity(n);
            int m = sc.nextInt();            
            for(int i=0;i<m;i++){
                int first = sc.nextInt();
                int second = sc.nextInt();
                snc.union(first, second);
                if(snc.counts==1) {
                    System.out.println("Everyone is connected when "+i+1);
                    break;
                }
            }            
        }
        sc.close();
    }
}
