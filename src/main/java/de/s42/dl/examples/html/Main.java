// <editor-fold desc="The MIT License" defaultstate="collapsed">
/*
 * The MIT License
 * 
 * Copyright 2022 Studio 42 GmbH (https://www.s42m.de).
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
package de.s42.dl.examples.html;

import de.s42.base.files.FilesHelper;
import de.s42.dl.examples.html.tags.BodyTag;
import de.s42.dl.examples.html.tags.HtmlTag;
import de.s42.dl.examples.html.tags.PTag;
import de.s42.log.LogManager;
import de.s42.log.Logger;
import java.nio.file.Path;

/**
 * This example shows how to use dl to create a domain model connected as DL dialect and an API within Java. It also
 * shows how to inject values into dl as referencable data.
 *
 * @author Benjamin Schiller
 */
public class Main
{

	private final static Logger log = LogManager.getLogger(Main.class.getName());

	protected final static Path HTML_DL_FILE = Path.of("de/s42/dl/examples/html/page.html.dl");
	protected final static Path HTML_FILE = Path.of("de/s42/dl/examples/html/page.local.html");

	public static void main(String[] args) throws Exception
	{
		log.info("Starting HTML Example");

		// Create the dl core
		HtmlCore core = HtmlCore.create();

		// Inject a variable into core -> can now be referenced with $title within DL
		core.setVariable("title", "Page Title from Java");

		// Read DL file into domain data model
		HtmlTag html = core.parseHtml(HTML_DL_FILE);

		// Find body tag and then append a p tag to it or error if not found
		html.getChild(BodyTag.class).ifPresentOrElse(
			// Body found
			(tag) -> {
				tag.addChild(new PTag("Added this text in Java!"));
			},
			// Not found
			() -> {
				log.error("No body tag found");
			});

		// Create and store the domain model as html file
		FilesHelper.writeStringToFile(HTML_FILE, html.toHtml());

		log.debug(FilesHelper.createMavenNetbeansFileConsoleLink("Written HTML File", HTML_FILE));
	}
}
