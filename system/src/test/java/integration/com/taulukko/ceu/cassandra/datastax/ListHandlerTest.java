package integration.com.taulukko.ceu.cassandra.datastax;

import io.netty.util.internal.ConcurrentSet;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.Command;
import com.taulukko.ceu.handler.ListHandler;
import com.taulukko.ceu.handler.ListHandlerBuilder;

public class ListHandlerTest extends BaseTest {

	@Test
	public void listfield() throws CEUException, ParseException {

		ListHandler<String> handler = ListHandlerBuilder.build().byAllRows()
				.collect().byField("email", String.class);
		Command command = new Command("SELECT email FROM \"" + TABLE_NAME
				+ "\" allow filtering ");

		List<String> emails = existOptional(runner.query(command, handler));
		final Set<String> setEmails = new ConcurrentSet<String>();

		emails.stream().forEach(email -> {
			Assert.assertTrue(emails.get(0).startsWith("userTest"));
			Assert.assertTrue(emails.get(0).endsWith("@gmail.com"));
			setEmails.add(email);
		});

		Assert.assertEquals(emails.size(), setEmails.size());

	}

	@Test
	public void listFromManyFields() throws CEUException, ParseException {

		ListHandler<String> handler = ListHandlerBuilder.build().byAnyRow()
				.collect().byFieldList("tags", String.class);

		Command command = new Command("SELECT tags FROM \"" + TABLE_NAME
				+ "\" WHERE key = ?", "userTest3");

		List<String> tags = existOptional(runner.query(command, handler));

		Assert.assertEquals("Tag1-3", tags.get(0));
		Assert.assertEquals("Tag2-3", tags.get(1));
		Assert.assertEquals("Tag3-3", tags.get(2));
	}
	
	 
}
