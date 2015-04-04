/**
 * 
 *	Copyright 2013 Inject Template Evon (ITE)
 *  
 *	Inject Template Evon by Edson Vicente Carli Junior (evon) 
 *  is licensed under a Creative Commons Attribution 3.0 Unported License.
 *	Permissions beyond the scope of this license may be available at 
 *  http://inject.evon.com.br/permissions.
 * 
 */

package com.taulukko.cassandra;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.taulukko.commons.parsers.jsonParser.JSONParser;
 

public class CEUConfig extends Thread {

	// //////////////////
	// SERVER//
	// //////////////////
	public static final String LINE_SEPARATOR = "#########################################################";

	public static boolean loaded = false;

	public static boolean live = false;

	public static String sliceSeparator = null;

	public static List<ServerInfo> serverInfos = null;

	/**
	 * Description: Time to reload template in seconds. Default: 15
	 */
	public static int refreshTimeInSeconds = 15;

	/**
	 * Description: Print more information. Default: false
	 */
	public static Boolean verbose = false;

	public static boolean isAutoWrapItemName = false;

	private CEUConfig() {
	}

	public static void load(String path) throws CEUException {

		try {
			load(new FileInputStream(path));
		} catch (FileNotFoundException fe) {
			throw new CEUException("Template properties file not founded in ("
					+ path + ")", fe);
		}

	}

	public static void load(InputStream in) throws CEUException {

		BufferedInputStream inStream = null;

		inStream = new BufferedInputStream(in);

		StringBuffer stream = new StringBuffer();

		try {
			int avaiableBytes = inStream.available();
			while (avaiableBytes > 0) {
				byte buff[] = new byte[avaiableBytes];
				inStream.read(buff);
				stream.append(new String(buff));
				avaiableBytes = inStream.available();
			}
		} catch (IOException ioe) {
			throw new CEUException(ioe.getMessage(), ioe);
		}

		try {
			inStream.close();
		} catch (IOException e) {
			throw new CEUException(e.getMessage(), e);
		}

		String json = stream.toString();

		ConfigBean config = JSONParser.convert(json, ConfigBean.class);

		for (ServerInfo serverinfo : config.getServers()) {
			// ignore url from jason and recreate using hostname and port
			serverinfo.setUrl(serverinfo.getHostName() + ":"
					+ serverinfo.getPort());
		}

		serverInfos = config.getServers();
		sliceSeparator = config.getSliceSeparator();
		isAutoWrapItemName = config.isAutoWrapItemName();

		if (CEUConfig.verbose) {
			System.out.println("\n\n" + LINE_SEPARATOR + "\n[" + new Date()
					+ "] : CEU properties loaded!");
			System.out.print("\nproperties = " + json);
			System.out.print("\n" + LINE_SEPARATOR + "\n\n");
		}
	}

}