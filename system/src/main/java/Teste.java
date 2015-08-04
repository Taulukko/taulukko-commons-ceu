import org.junit.Assert;
import org.junit.Test;

import com.netflix.astyanax.thrift.ThriftFamilyFactory;


public class Teste {
	
	@Test
	public void teste()
	{
		ThriftFamilyFactory factoryFamily = ThriftFamilyFactory.getInstance();
		Assert.assertNotNull(factoryFamily);
	}

}
