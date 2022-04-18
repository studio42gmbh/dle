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
package de.s42.dl.examples.binary;

import de.s42.dl.*;
import de.s42.dl.core.DefaultCore;
import de.s42.dl.io.DLWriter;
import de.s42.dl.io.binary.BinaryDLReader;
import de.s42.dl.io.binary.BinaryDLWriter;
import de.s42.dl.io.hrf.HrfDLReader;
import de.s42.dl.io.hrf.HrfDLWriter;
import de.s42.log.LogLevel;
import de.s42.log.LogManager;
import de.s42.log.Logger;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Benjamin Schiller
 */
public class Main
{

	private final static Logger log = LogManager.getLogger(Main.class.getName());

	protected final static Path LOCAL_CONFIG_FILE_ZIP = Path.of("de/s42/dl/examples/binary/config.local.dlb.zip");
	protected final static Path LOCAL_CONFIG_FILE_BIN = Path.of("de/s42/dl/examples/binary/config.local.dlb");
	protected final static Path LOCAL_CONFIG_FILE_HRF = Path.of("de/s42/dl/examples/binary/config.local.dl");

	protected final static int AMOUNT = 10;
	protected final static int CYCLES = 3;

	public static void main(String[] args) throws Exception
	{
		log.info("Starting Binary Example");
		
		// init core and 2 custom types Uer and Configuration
		DLCore core = new DefaultCore();
		core.defineType(core.createType(User.class), "User");

		for (int c = 0; c < CYCLES; ++c) {

			List<User> users = new ArrayList<>(AMOUNT);
			for (int i = 0; i < AMOUNT; ++i) {
				User user = new User();

				if (Math.random() > 0.5) {
					user.setName("arti" + i);
				}

				user.setLogin("Arthur " + (int) (Math.random() * 1000.0));
				user.setActive(Math.random() > 0.5);
				user.setUuid(UUID.randomUUID());
				user.setAge(18 + (int) (Math.random() * 50.0));
				user.setScore(Math.random() * 10.0);
				user.setSector((float) (Math.random() * 10000.0));
				user.setSeed((long) (Math.random() * 1000000000000.0));

				users.add(user);
			}

			log.start("BinaryDLWriterCompressed");
			try (DLWriter writer = new BinaryDLWriter(LOCAL_CONFIG_FILE_ZIP, core, true)) {
				for (User user : users) {
					writer.write(user);
				}
			}
			double durationBinaryDLWriterCompressed = log.stop(LogLevel.DEBUG, "BinaryDLWriterCompressed", AMOUNT);

			log.start("BinaryDLWriter");
			try (DLWriter writer = new BinaryDLWriter(LOCAL_CONFIG_FILE_BIN, core, false)) {
				for (User user : users) {
					writer.write(user);
				}
			}
			double durationBinaryDLWriter = log.stop(LogLevel.DEBUG, "BinaryDLWriter", AMOUNT);

			log.start("HrfDLWriter");
			try (DLWriter writer = new HrfDLWriter(LOCAL_CONFIG_FILE_HRF, core, false)) {
				for (User user : users) {
					writer.write(user);
				}
			}
			double durationHrfDLWriter = log.stop(LogLevel.DEBUG, "HrfDLWriter", AMOUNT);

			double sizeBinaryDLWriterCompressed = (LOCAL_CONFIG_FILE_ZIP.toFile()).length();
			log.debug("Size bin compressed", sizeBinaryDLWriterCompressed, "bytes");

			double sizeBinaryDLWriter = (LOCAL_CONFIG_FILE_BIN.toFile()).length();
			log.debug("Size bin", sizeBinaryDLWriter, "bytes");

			double sizeHrfDLWriter = (LOCAL_CONFIG_FILE_HRF.toFile()).length();
			log.debug("Size hrf", sizeHrfDLWriter, "bytes");

			log.debug("Ratio Duration write bin / zip", durationBinaryDLWriter * 100.0 / durationBinaryDLWriterCompressed, "%");
			log.debug("Ratio Duration write zip / hrf", durationBinaryDLWriterCompressed * 100.0 / durationHrfDLWriter, "%");
			log.debug("Ratio Duration write bin / hrf", durationBinaryDLWriter * 100.0 / durationHrfDLWriter, "%");
			log.debug("Ratio Size bin / hrf", sizeBinaryDLWriter * 100.0 / sizeHrfDLWriter, "%");
			log.debug("Ratio Size zip / bin", sizeBinaryDLWriterCompressed * 100.0 / sizeBinaryDLWriter, "%");
			log.debug("Ratio Size zip / hrf", sizeBinaryDLWriterCompressed * 100.0 / sizeHrfDLWriter, "%");

			double sum = 0.0;

			// read from binary	compressed	
			log.start("BinaryDLReaderCompressed");
			try (BinaryDLReader reader = new BinaryDLReader(LOCAL_CONFIG_FILE_ZIP, core)) {

				while (reader.ready()) {
					User user = reader.readObject();
					sum += user.getScore();
				}
			}
			double durationBinaryDLReaderCompressed = log.stop(LogLevel.DEBUG, "BinaryDLReaderCompressed", AMOUNT);

			// read from binary	
			log.start("BinaryDLReader");
			try (BinaryDLReader reader = new BinaryDLReader(LOCAL_CONFIG_FILE_BIN, core)) {

				while (reader.ready()) {
					User user = reader.readObject();
					sum += user.getScore();
				}
			}
			double durationBinaryDLReader = log.stop(LogLevel.DEBUG, "BinaryDLReader", AMOUNT);

			// read from hrf
			log.start("HrfDLReader");
			try (HrfDLReader reader = new HrfDLReader(LOCAL_CONFIG_FILE_HRF, core)) {

				while (reader.ready()) {
					User user = reader.readObject();
					sum += user.getScore();
				}
			}
			double durationHrfDLReader = log.stop(LogLevel.DEBUG, "HrfDLReader", AMOUNT);

			log.debug("Ratio Duration read bin / hrf", durationBinaryDLReader * 100.0 / durationHrfDLReader, "%");
			log.debug("Ratio Duration read bin / zip", durationBinaryDLReader * 100.0 / durationBinaryDLReaderCompressed, "%");
			log.debug("Ratio Duration read zip / hrf", durationBinaryDLReaderCompressed * 100.0 / durationHrfDLReader, "%");

			log.debug("Sum", sum);
		}

	}
}
