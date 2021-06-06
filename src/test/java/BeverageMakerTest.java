import com.dunzo.beveragemachine.components.BeverageMakerWorkersManager;
import com.dunzo.beveragemachine.components.Machine;
import com.dunzo.beveragemachine.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class BeverageMakerTest {

    static ObjectMapper objectMapper = new ObjectMapper();
    Machine machine;

    @Before
    public void setup() throws IOException {
        machine = objectMapper.readValue(Utils.fileContentToString("src/main/resources/input.txt"), Machine.class);
    }


    @Test
    public void willMakeBeverages() {

        BeverageMakerWorkersManager workers = machine.getBeverageMakerThreadPool();
        workers.makeBeverage("hot_tea", machine.getContentManager());
        workers.makeBeverage("hot_coffee", machine.getContentManager());
        workers.makeBeverage("black_tea", machine.getContentManager());
        workers.makeBeverage("green_tea", machine.getContentManager());


    }

}
