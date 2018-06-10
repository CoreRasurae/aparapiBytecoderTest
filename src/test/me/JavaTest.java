package test.me;

import com.aparapi.Kernel;

public class JavaTest extends Kernel {

    @Local
    int[] arr = new int[32];
    
    @Override
    public void run() {
        for (int i = 0; i < 32; i++) {
            arr[i] = i;
        }  
    }
    
}

