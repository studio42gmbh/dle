// <editor-fold desc="The MIT License" defaultstate="collapsed">
/* 
 * The MIT License
 * 
 * Copyright 2021 Studio 42 GmbH ( https://www.s42m.de ).
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
//</editor-fold>
package de.s42.dl.examples.json;

import de.s42.base.strings.StringHelper;
import de.s42.dl.examples.simpleconfiguration.*;
import de.s42.dl.DLModule;
import de.s42.dl.core.DefaultCore;
import de.s42.dl.io.json.JsonReader;
import de.s42.dl.io.json.JsonWriter;
import de.s42.log.LogManager;
import de.s42.log.Logger;
import java.nio.file.Path;

/**
 *
 * @author Benjamin Schiller
 */
public class Main
{

	protected final static Path CONFIG_PATH = Path.of("de/s42/dl/examples/json/config.dl");
	protected final static Path JSON_PATH = Path.of("de/s42/dl/examples/json/config.local.json");

	private final static Logger log = LogManager.getLogger(Main.class.getName());

	public static void main(String[] args) throws Exception
	{
		log.info("Starting JSON Example");

		// Setup dl core and map own POJO class Configuration
		DefaultCore core = new DefaultCore();
		core.defineType(Configuration.class, "Configuration");

		// Load config
		core.parse(CONFIG_PATH.toString());

		// Convert DL to config
		Configuration config = core.getExported("config").orElseThrow().toJavaObject(core);
		log.debug(StringHelper.toString(config));		

		// Write config to json
		try (JsonWriter writer = new JsonWriter(JSON_PATH, core)) {
			writer.write(config);
		}
		
		// Read the json into a dl and then convert into the java object
		try (JsonReader reader = new JsonReader(core, JSON_PATH)) {
			Configuration configJson = reader.readObject();
			log.debug(StringHelper.toString(configJson));		
		}				
	}
}
