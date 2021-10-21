/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csd301;

import java.util.Scanner;

/**
 *
 * @author Kiet Ngo
 */
public class input {
    
    Scanner sc = new Scanner(System.in);
    
    public int int_check(String msg, int min, int max){
        System.out.print(msg + ": ");
        int output;
        while(true){
            try {
                String input = sc.nextLine();
                output = Integer.parseInt(input);
                if(output < min || output > max){
                    System.out.println("Re-enter: ");
                } else {
                    return output;
                }
            } catch (NumberFormatException x){
                System.out.print("Re-enter: ");
            }
        }
    }
    
    public double double_check(String msg){
        System.out.print(msg + ": ");
        double output;
        while(true){
            try {
                String input = sc.nextLine();
                output = Double.parseDouble(input);
                return output;
            } catch (NumberFormatException x){
                System.out.print("Re-enter: ");
            }
        }
    }
    
    public String str_check(String msg){
        System.out.print(msg + ": ");
        String output = sc.nextLine();
        return output;
    }
}
