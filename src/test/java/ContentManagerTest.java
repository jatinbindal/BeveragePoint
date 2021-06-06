import com.dunzo.beveragemachine.components.ContentStatus;
import com.dunzo.beveragemachine.components.IngredientAndStatusPair;
import com.dunzo.beveragemachine.components.Machine;
import com.dunzo.beveragemachine.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ContentManagerTest {

    static ObjectMapper objectMapper = new ObjectMapper();
    Machine machine;

    @Before
    public void setup() throws IOException {
        machine = objectMapper.readValue(Utils.fileContentToString("src/main/resources/input.txt"), Machine.class);
    }


    @Test
    public void canFillMachineWIthIngredient() {
        machine.getContentManager().addContent("randomIngredient", 100);
        Assert.assertEquals(machine.getContentManager().getQuantity("randomIngredient"), 100);

    }

    @Test
    public void willReturnUNAVAILABLEIfIngredientNotPresentAtAll() {
        IngredientAndStatusPair result = machine.getContentManager().checkContentPresent("some not present content");
        Assert.assertEquals(result.ingredient, "some not present content");
        Assert.assertEquals(result.status, ContentStatus.UNAVAILABLE);
    }

    @Test
    public void willReturnINSUFFICIENTIfContentISPresentButLess() {

        IngredientAndStatusPair result = machine.getContentManager().checkContentSufficient("hot_water", 600);
        Assert.assertEquals(result.ingredient, "hot_water");
        Assert.assertEquals(result.status, ContentStatus.INSUFFICIENT);
    }

    @Test
    public void willReturnAVAILABLEIfContentIsEnough() {

        IngredientAndStatusPair result = machine.getContentManager().checkContentSufficient("hot_water", 500);
        Assert.assertEquals(result.ingredient, "hot_water");
        Assert.assertEquals(result.status, ContentStatus.AVAILABLE);

    }

    @Test
    public void willNotReturnBeverageEvenIfOneIngredientIsUNAVAILABLE() {

        String result = machine.getContentManager().getBeverageContents("green_tea");
        Assert.assertEquals(result, "green_tea cannot be prepared because green_mixture is UNAVAILABLE");

    }
}

