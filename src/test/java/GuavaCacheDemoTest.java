import com.google.common.collect.ImmutableList;
import com.nickgroenke.DemoCallerWithCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.inject.Named;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author nickgroenke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/application-config.xml", "/application-config-test.xml" })
public class GuavaCacheDemoTest {

    @Resource
    @Named("demoCallerWithCacheFromSpec")
    DemoCallerWithCache demoCallerWithCacheFromSpec;

    @Resource
    @Named("demoCallerWithCacheFromFactoryBean")
    DemoCallerWithCache demoCallerWithCacheFromFactoryBean;

    @Resource
    @Named("demoCallerSpEL")
    DemoCallerWithCache demoCallerSpEL;

    List<String> input = ImmutableList.of("a","b","c");
    List<String> expectedOutput = ImmutableList.of("A","B","C");

    @Test
    public void testDemoCallerWithCacheFromSpec() {
        testHelper(demoCallerWithCacheFromSpec);
    }

    @Test
    public void testDemoCallerWithCacheFromFactoryBean() {
        testHelper(demoCallerWithCacheFromFactoryBean);
    }

    @Test
    public void testDemoCallerSpEL() {
        testHelper(demoCallerSpEL);
    }

    private void testHelper(DemoCallerWithCache instance) {
        List<String> output = null;

        output = instance.capitalizeStrings(input);

        assertEquals(expectedOutput.size(),output.size());

        for(int i = 0; i < expectedOutput.size(); i++) {
            assertEquals(expectedOutput.get(i),output.get(i));
        }
    }

}
