// <editor-fold desc="The MIT License" defaultstate="collapsed">
/*
 * The MIT License
 * 
 * Copyright 2022 Studio 42 GmbH ( https://www.s42m.de ).
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
package de.s42.dl.examples.loadandstore;

import de.s42.base.files.FilesHelper;
import de.s42.dl.core.DefaultCore;
import de.s42.dl.language.DLFileType;
import de.s42.dl.util.DLHelper;
import de.s42.log.LogManager;
import de.s42.log.Logger;
import java.nio.file.Path;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Benjamin Schiller
 */
public class Main
{

	protected final static Path CONFIG_PATH = Path.of("de/s42/dl/examples/loadandstore/config.local.dl");

	private final static Logger log = LogManager.getLogger(Main.class.getName());

	public static void main(String[] args) throws Exception
	{
		log.info("Starting Load and Store Example");

		// init core and 2 custom types Uer and Configuration
		DefaultCore core = new DefaultCore();
		core.defineType(core.createType(User.class));
		core.defineType(core.createType(Configuration.class));

		// pre create configuration if config has not been saved yet
		Configuration config;

		// if config file exists load config from file
		if (FilesHelper.fileExists(CONFIG_PATH)) {

			config = (Configuration) core.parse(CONFIG_PATH.toString()).getChildAsJavaObject(0);
		} // create a new config
		else {
			config = new Configuration();

			User user = new User();
			user.setActive(true);
			user.setLogin("Arthur");
			config.setUser(user);
		}

		// change config
		config.setDepth(Math.random() * 10.0);
		config.setLoadCount(config.getLoadCount() + 1);
		config.setLastLoad(new Date());
		config.getUser().setName("arthur" + (int) (Math.random() * 100.0));
		config.getUser().setUuid(UUID.randomUUID());

		// store config
		DLHelper.writeEntityToFile(core, CONFIG_PATH, config, DLFileType.HRF);

		log.debug("Updated config", config.getLoadCount(), CONFIG_PATH.toAbsolutePath().toString());
	}
}
