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

import de.s42.dl.DLAttribute.AttributeDL;

/**
 *
 * @author Benjamin Schiller
 */
public class LinkTag extends ContainerTag implements ContainedInHead
{

	@SuppressWarnings("FieldNameHidesFieldInSuperclass")
	public static final String NAME = "link";

	@AttributeDL(defaultValue = "stylesheet")
	protected String rel;

	@AttributeDL(required = true)
	protected String href;

	public LinkTag()
	{
		super(NAME);
	}
	
	@Override
	protected void toHtmlTagContent(StringBuilder builder)
	{
		builder.append(" rel=\"").append(getRel()).append("\"");
		builder.append(" href=\"").append(getHref()).append("\"");
	}

	// <editor-fold desc="Getters/Setters" defaultstate="collapsed">
	public String getRel()
	{
		return rel;
	}

	public void setRel(String rel)
	{
		this.rel = rel;
	}

	public String getHref()
	{
		return href;
	}

	public void setHref(String href)
	{
		this.href = href;
	}
	//</editor-fold>
}
