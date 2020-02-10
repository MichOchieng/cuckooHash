import java.util.ArrayList;
import java.util.Arrays;

public class cuckoo{
    
    private static int size;
    private static int sizeMultiplier = 1;
    private static int maxLoop = 10;
    private static int loopCount = 0;
    // Creates hashtables T0 and T1
    private static int table[][];   
    // Stores all given values
    private static ArrayList<Integer> values = new ArrayList<Integer>();  

    public cuckoo(int size){
        // You can chose how large the tables are
        this.size = size;
        table = new int[2][size];
        inializeTable();        
    }   

    // Allows outside classes to use insert method
    public static void add(int x){
        // Checks to see if there are too many values
        if (values.size() < (size*0.5)) {
            insert(x);
        }else{
            rehash();
            insert(x);
        }
    }

    // Initializes hashtables with placeholder values
    private static void inializeTable(){
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < size*sizeMultiplier; j++) {
                table[i][j] = -1;
            }
        }        
    }

    private static void insert(int x){        
        // Detects an infinite loop and rehashes
        if(loopCount == maxLoop){
            rehash();    
            insert(x);                  
            return;
        }else{
            if (table[0][hash0(x)] == -1) {
                // If the address in the first table is empty            
                table[0][hash0(x)] = x;                   
            }else if(table[0][hash0(x)] != -1){
                // If the address is filled
                int temp = table[0][hash0(x)];
                System.out.println("Replacing " + temp);
                table[0][hash0(x)] = x;                
                if (table[1][hash1(temp)] == -1){ 
                    // If the address in the next table is empty               
                    table[1][hash1(temp)] = temp;                    
                }else if(table[1][hash1(temp)] != -1){
                    // If its full save the value there, replace and restart with the temp variable
                    int temp2= table[1][hash1(temp)];
                    table[1][hash1(temp)] = temp;                    
                    loopCount++;                
                    insert(temp2);                
                }
    
            }
            printTable();
            System.out.println();
        }        
        
    }

    // This stops values from being added to the value list twice
    private static void rehashInsert(int x){
        if (table[0][hash0(x)] == -1) {
            // If the address in the first table is empty            
            table[0][hash0(x)] = x;            
        } else if(table[0][hash0(x)] != -1){
            // If the address is filled
            int temp = table[0][hash0(x)];
            System.out.println("Replacing " + temp);
            table[0][hash0(x)] = x;            
            if (table[1][hash1(temp)] == -1){ 
                // If the address in the next table is empty               
                table[1][hash1(temp)] = temp;                
            }else if(table[1][hash1(temp)] != -1){
                // If its full save the value there, replace and restart with the temp variable
                int temp2= table[1][hash1(temp)];
                table[1][hash1(temp)] = temp;                
                             
            }
        }
    }

    private static void rehash(){
        System.out.println("Rehashing...");
        // When using insert dont add values twice
        loopCount = 0;
        sizeMultiplier++;        
        // Saves previous tables values except for last value stuck in insert loop 
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < size*(sizeMultiplier-1); j++) {
                if(table[i][j] != -1){
                    values.add(table[i][j]);
                }
            }            
        }
        table = new int[2][size*sizeMultiplier];
        inializeTable();        
        for (int i = 0; i < values.size(); i++) {                     
            rehashInsert(values.get(i));           
        }  
        
    }   

    // Hash for first table
    private static int hash0(int x){        
        return x%(size*sizeMultiplier);
    }
    // Hash for second table
    private static int hash1(int x){        
        return (x/size*sizeMultiplier)%(size*sizeMultiplier);
    }

    public static void printTable(){
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < size*sizeMultiplier; j++) {
                if(table[i][j] == -1){
                    System.out.print(" x ");
                }else{
                    System.out.print(" " + table[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}