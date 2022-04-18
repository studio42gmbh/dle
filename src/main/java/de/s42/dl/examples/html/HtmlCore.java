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

import de.s42.dl.DLModule;
import de.s42.dl.core.DefaultCore;
import de.s42.dl.examples.html.tags.BodyTag;
import de.s42.dl.examples.html.tags.ContainerTag;
import de.s42.dl.examples.html.tags.DivTag;
import de.s42.dl.examples.html.tags.H1Tag;
import de.s42.dl.examples.html.tags.HtmlTag;
import de.s42.dl.examples.html.tags.Tag;
import de.s42.dl.examples.html.tags.PTag;
import de.s42.dl.examples.html.tags.TextTag;
import de.s42.dl.exceptions.DLException;
import de.s42.dl.exceptions.InvalidInstance;
import de.s42.dl.instances.SimpleTypeDLInstance;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author Benjamin Schiller
 */
public class HtmlCore extends DefaultCore
{

	public static HtmlCore create() throws IOException, DLException
	{
		return new HtmlCore();
	}

	public HtmlCore() throws IOException, DLException
	{
		init();
	}

	private void init() throws IOException, DLException
	{
		defineArrayType(String.class);
		defineType(createType(List.class));
		defineType(createType(Tag.class));
		defineType(createType(ContainerTag.class));
		defineType(createType(TextTag.class));

		defineType(createType(BodyTag.class), BodyTag.NAME);
		defineType(createType(HtmlTag.class), HtmlTag.NAME);
		defineType(createType(DivTag.class), DivTag.NAME);
		defineType(createType(PTag.class), PTag.NAME);
		defineType(createType(H1Tag.class), H1Tag.NAME);

		setAllowDefineAnnotations(false);
		setAllowDefinePragmas(false);
		setAllowDefineTypes(false);
	}

	public void setVariable(String key, String value) throws InvalidInstance
	{
		assert key != null;
		assert value != null;

		// Define a simple data instance representing the given variable
		SimpleTypeDLInstance<String> variable
			= new SimpleTypeDLInstance<>(
				value,
				getType(String.class).orElseThrow(),
				key);

		// Map it into core -> can now be used with $key as reference in dl
		addExported(variable);
	}

	public <TagType extends Tag> TagType parseHtml(Path htmlFile) throws DLException
	{
		assert htmlFile != null;

		// Load file as module
		DLModule module = parse(htmlFile.toAbsolutePath().toString());

		// Expect the file to contain at least 1 child -> return it
		return (TagType) module.getChild(0).toJavaObject(this);
	}
}
