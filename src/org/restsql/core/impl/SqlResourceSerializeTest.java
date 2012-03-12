/* Copyright (c) restSQL Project Contributors. Licensed under MIT. */
package org.restsql.core.impl;

//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.restsql.core.Factory;
import org.restsql.core.SqlResource;
import org.restsql.core.SqlResourceException;
import org.xml.sax.SAXException;

public class SqlResourceSerializeTest extends SqlResourceTestBase {

	@Test
	public void testSerializeReadFlat() {
		try {
			final SqlResource sqlResource = Factory.getSqlResource("SingleTable");
			
			try{
    			final Statement statement = connection.createStatement();
                String sql = "SELECT 'Foo\\'s & bars' as Value FROM actor;";
                final ResultSet resultSet = statement.executeQuery(sql);
                String results = ResultsSerializer.serializeReadFlat(sqlResource, resultSet);
                //Something like below causes failure
                //results ="<readResponse><actor Value=\"Foo's & bars\" /></readResponse>";
                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder xmlBuilder = builderFactory.newDocumentBuilder();
                
                xmlBuilder.parse(new ByteArrayInputStream(results.getBytes()));

			} catch (ParserConfigurationException|SAXException|IOException|SQLException e) {
			    fail(e.getMessage());
			}
			
		} catch (final SqlResourceException exception) {
		    fail(exception.getMessage());
		}
	}
}
