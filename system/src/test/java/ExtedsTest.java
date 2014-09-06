import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


//arrumar esse teste, arraylist nao é direto de list e sim de abstract list
//o directchildbysuperclass2 mostra isso
//via superclass da erro se nao for filha diretamente
//evidenciar isso nesse teste pra ficar mais claro
//no runner, usar o assignablefrom e explicar

public class ExtedsTest {

	@Test
	public void directChidBySuperClass() {
		@SuppressWarnings("rawtypes")
		List value = new TestList();
		Assert.assertTrue(value.getClass().getSuperclass().equals(ArrayList.class));
	}

	@Test
	public void directChidByAssignableFrom() {
		@SuppressWarnings("rawtypes")
		List value = new ArrayList<>();
		Assert.assertTrue(List.class.isAssignableFrom(value.getClass()));
	}
	

	@Test
	public void indirectChidBySuperClass() {
		@SuppressWarnings("rawtypes")
		List value = new TestList();
		Assert.assertFalse(value.getClass().getSuperclass().equals(List.class));
	}

	@Test
	public void directChidBySuperClass2() {
		@SuppressWarnings("rawtypes")
		List value = new TestList();
		Assert.assertTrue(value.getClass().getSuperclass().equals(ArrayList.class));
	}

	
	@Test
	public void indirectChidByAssignableFrom() {
		@SuppressWarnings("rawtypes")
		List value = new TestList();
		Assert.assertTrue(List.class.isAssignableFrom(value.getClass()));
	} 

	public class TestList extends ArrayList<String>
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = -7327222695419750373L;
		
	}
}
