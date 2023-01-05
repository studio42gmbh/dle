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
package de.s42.dl.examples.gui;

import de.s42.dl.DLAttribute.AttributeDL;
import de.s42.dl.annotations.numbers.RangeDLAnnotation.range;

/**
 *
 * @author Benjamin Schiller
 */
public class Font
{

	@AttributeDL(required = true)
	protected String face;

	@AttributeDL(required = true)
	@range(min = 1, max = 1000)
	protected int size;

	@AttributeDL
	protected boolean bold;

	@AttributeDL
	protected boolean italic;

	public java.awt.Font createAwtFont()
	{
		int style
			= (isBold() ? java.awt.Font.BOLD : 0)
			| (isItalic() ? java.awt.Font.ITALIC : 0);

		return new java.awt.Font(getFace(), style, getSize());
	}

	// <editor-fold desc="Getters/Setters" defaultstate="collapsed">
	public String getFace()
	{
		return face;
	}

	public void setFace(String face)
	{
		this.face = face;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	public boolean isBold()
	{
		return bold;
	}

	public void setBold(boolean bold)
	{
		this.bold = bold;
	}

	public boolean isItalic()
	{
		return italic;
	}

	public void setItalic(boolean italic)
	{
		this.italic = italic;
	}
	//</editor-fold>	
}
