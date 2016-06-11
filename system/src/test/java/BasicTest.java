import org.junit.Assert;
import org.junit.Test;

import com.netflix.astyanax.thrift.ThriftFamilyFactory;


public class BasicTest {
	
	@Test
	public void test()
	{
		ThriftFamilyFactory factoryFamily = ThriftFamilyFactory.getInstance();
		Assert.assertNotNull(factoryFamily);
	}

}
