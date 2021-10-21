/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csd301;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Kiet Ngo
 */
public class simplex {
    
    int n, m;
    input in = new input();
    double[][] a;
    
    double[][] b;
    
    public double[][] simplexmethod(double[][] matrix_in, int var){
        double[][] matrix = matrix_in;
        for (int time = 0; time < var; time++){
            if (check_botrow(matrix[matrix.length-1])){
                return matrix;
            } else {
                int col_id = time, row_id = 0, temp_row_id=row_id;
                double pivot_number = Double.MAX_VALUE, pivot_number_row;              
                for (double[] row : matrix){
                    pivot_number_row = row[row.length-1] / row[col_id] * 1.0;
                    if (pivot_number_row > 0 && pivot_number_row < pivot_number){
                        pivot_number = pivot_number_row;
                        row_id = temp_row_id;
                    }                  
                    temp_row_id += 1;
                }        
                pivot_number = matrix[row_id][matrix[row_id].length-1] / pivot_number;
                double [] row_temp = new double[matrix[row_id].length];
 
                for (int j = 0; j < row_temp.length; j++){
                    matrix[row_id][j] = matrix[row_id][j] / pivot_number;
                }
                matrix = update_col(matrix, matrix[row_id], col_id, row_id);
            }
        }
        
        return matrix;
    }
    
    private double[][] update_col(double[][] matrix, double[] prow, int col_id, int id){
        double number_multi;
        double[] temp_row = new double[prow.length], temp;
        
        for (int hu = 0; hu < temp_row.length; hu++){
            temp_row[hu] = prow[hu];
        } 
        
        for (int row = 0; row < matrix.length; row++){
            if(row != id){
                temp = temp_row;
                
                number_multi = matrix[row][col_id] / temp[col_id];
                
                for (int j = 0; j < temp.length; j++){
                    temp[j] = temp[j] * number_multi;
                    matrix[row][j] = matrix[row][j] - temp[j];
                } 

            }
        }
        
        return matrix;
    }
    
    private boolean check_botrow(double[] arr){
        for (double i : arr){
            if (i < 0.0){
                return false;
            }
        }
        return true;
    }
    
    public void show(double[][] matrix){
        for (int i = 0; i < matrix.length; i++){
            for (int o = 0; o < matrix[0].length; o++){
                System.out.print(matrix[i][o] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    void loadData(String fname) throws IOException {
        try{
            FileReader fr = new FileReader(fname);
            BufferedReader br = new BufferedReader(fr);
            String x, y;
            int i, j;
            String[] s;
            br.readLine(); // ignore the first line
            x = br.readLine().trim();
            n = Integer.parseInt(x);
            y = br.readLine().trim();
            m = Integer.parseInt(y);
            br.readLine(); // ignore the third line
            br.readLine();
            a = new double[n][m];
            for (i = 0; i < n; i++) {
                x = br.readLine().trim();
                s = x.split("[ ]+");
                for (j = 0; j < m; j++) {
                    a[i][j] = Integer.parseInt(s[j].trim());
                }
            }
            createSimplexMatrix(a);
            System.out.println("Your matrix data: ");
            show(b);

            br.close();
            fr.close();
        } catch (IOException e){
            System.out.println("Wrong file!");
        }
    }
    
    public void loadData_manual(){
        int para;
        para = in.int_check("Enter_parameters: ", 2, 4);
        n = in.int_check("Enter_functions: ", 2, 5);
        m = para + 1;
        a = new double[n][m];
        
        System.out.println("Enter constraint function:");
        for (int z = 0; z < n - 1; z++){
            for (int x = 0; x < m; x++){
                a[z][x] = in.double_check("Your value [" + z + "][" + x + "]");
            }
        }
        System.out.println("Enter target function:");
        for (int b = 0; b < m; b++){
            a[n-1][b] = in.double_check("index [" + b + "]"); 
        }
        
        createSimplexMatrix(a);
        
        System.out.println("Your matrix data: ");
        show(b);
    }
    
    private void show_para(double[][] matrix, int para){
        double[] result = new double[para];
        
        for (int row = 0; row < matrix.length - 1; row++){
            for (int col = 0; col < para; col++){
                if(b[row][col] == 1){
                    result[col] = b[row][b[0].length - 1];
                }
            }
        }
        
        for (int i = 0; i < result.length; i++){
            System.out.println("Value " + i + ": " + result[i]);
        }
    } 
    
    private void createSimplexMatrix(double[][] matrix){
        int para = matrix[0].length - 1;
        int column = para + matrix.length + 1;
        b = new double[matrix.length][column];
        
        for (int r = 0; r < matrix.length; r++){
            for (int c = 0; c < matrix[0].length; c++){
                if(c == matrix[0].length - 1){
                    b[r][column-1] = matrix[r][c];
                } else {
                    b[r][c] = matrix[r][c];
                }
            }
        }
        int id = para;
        
        for (int row = 0; row < b.length; row++){
            b[row][id] = 1.0;
            id += 1;
        }
    }
    
    public void menu() throws IOException{
        System.out.println("Welcome to simplex method");
        int choice;
        
        do {
            System.out.println("============ * * * ============");
            System.out.println();
            System.out.println("1: Parse data file");
            System.out.println("2: Parse data manually");
            System.out.println("3: Find the maximum value");
            System.out.println("4: Quit");
            System.out.println();
            choice = in.int_check("Your choice", 1, 4);
            System.out.println();
            switch (choice){
                case 1:
                    String fname = in.str_check("Parse your file name");
                    loadData(fname);
                    System.out.println();
                    break;
                case 2:
                    loadData_manual();
                    System.out.println();
                    break;
                case 3:   
                    System.out.println("Simplex matrix:");
                    System.out.println();
                    show(simplexmethod(b, b[0].length));
                    System.out.println();
                    System.out.println("Target function reach maximum = " + b[b.length-1][b[0].length-1] + " when: ");
                    show_para(b, a[0].length - 1);
                    System.out.println();
                    break;
            }
            System.out.println();
            System.out.println();
        } while (choice > 0 && choice < 4);
    }
}
