public class main{    
    public static void main(String[] args){        
        cuckoo c = new cuckoo(11);
        // Test for adding values
        c.add(3);
        c.add(5);
        c.add(16);
        c.add(27);
        c.add(38); 
        c.add(33); 
        c.add(49);   
        c.add(44); 
        c.add(133); 
        // Rehash test
        c.add(121);
        // Deletion test  
        c.evict(121); 
        c.evict(3000);
        // Lookup test
        c.lookup(44);
        c.lookup(3000);
        
        
    }

}