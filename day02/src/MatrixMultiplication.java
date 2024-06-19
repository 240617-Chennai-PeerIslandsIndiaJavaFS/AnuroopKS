public class MatrixMultiplication {
    public static void main(String[] args){
        int[][] array1={{1,5,9},{7,5,3},{4,5,6}};
        int[][] array2={{7,8,9},{3,6,9},{1,4,7}};
        int[][] array3=new int[3][3];

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                array3[i][j]=0;
                for(int k=0;k<3;k++) {
                    array3[i][j]+=array1[i][k]*array2[k][j];
                }
                System.out.print(array3[i][j]+" ");
            }System.out.println();
        }
    }
}
