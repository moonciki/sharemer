package sharemer.business.api.master.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by 18073 on 2018/7/22.
 */
public class TransferTest {

    @Test
    public void transferTest(){
        List<String> musicIds = new ArrayList<>();
        musicIds.add("1");
        musicIds.add("2");
        String[] s = musicIds.toArray(new String[0]);
        for(String u : s){
            System.out.print(u + ",");
        }
    }

}
