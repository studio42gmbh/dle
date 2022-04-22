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
package de.s42.dl.examples.html.tags;

/**
 *
 * @author Benjamin Schiller
 */
public abstract class Tag
{

	protected String name;
	protected String tag;
	protected String[] classes;

	public Tag(String tag)
	{
		assert tag != null;

		this.tag = tag;
	}

	protected void toHtmlTagContent(StringBuilder builder)
	{
	}

	protected void toHtmlContent(StringBuilder builder)
	{
	}

	protected boolean hasContent()
	{
		return false;
	}

	protected void toHtml(StringBuilder builder)
	{
		assert builder != null;

		builder.append("<").append(getTag());

		// Add name as id
		if (getName() != null) {
			builder.append(" id=\"").append(getName()).append("\"");
		}

		// Add classes
		String[] clss = getClasses();
		if (clss != null && clss.length > 0) {
			builder.append(" class=\"");

			boolean first = true;
			for (String cls : clss) {
				if (!first) {
					builder.append(", ");
				}
				first = false;
				builder.append(cls);
			}

			builder.append("\"");
		}

		toHtmlTagContent(builder);

		if (hasContent()) {
			builder.append(">");

			toHtmlContent(builder);

			builder.append("</").append(getTag()).append(">");
		} else {

			builder.append("/>");
		}
	}

	public String toHtml()
	{
		StringBuilder builder = new StringBuilder();

		toHtml(builder);

		return builder.toString();
	}

	// <editor-fold desc="Getters/Setters" defaultstate="collapsed">
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTag()
	{
		return tag;
	}

	public void setTag(String tag)
	{
		this.tag = tag;
	}

	public String[] getClasses()
	{
		return classes;
	}

	public void setClasses(String[] classes)
	{
		this.classes = classes;
	}
	//</editor-fold>
}
