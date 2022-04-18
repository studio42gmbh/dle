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
package de.s42.dl.examples.simpleconfiguration;

import de.s42.base.strings.StringHelper;
import de.s42.dl.core.DefaultCore;
import java.nio.file.Path;
import de.s42.log.LogManager;
import de.s42.log.Logger;

/**
 *
 * @author Benjamin Schiller
 */
public class Main
{

	protected final static Path CONFIG_PATH = Path.of("de/s42/dl/examples/simpleconfiguration/config.dl");

	private final static Logger log = LogManager.getLogger(Main.class.getName());

	public static void main(String[] args) throws Exception
	{
		log.info("Starting Simple Example");
		
		// setup dl core and map own class Configuration
		DefaultCore core = new DefaultCore();
		core.defineType(core.createType(Configuration.class), "Configuration");

		// load config -> as it is a dl file with just a single entity we can use this helper method
		Configuration config = core.parse(CONFIG_PATH.toString()).getChildAsJavaObject(0, core);

		// log loaded config
		log.debug(StringHelper.toString(config));
	}
}
