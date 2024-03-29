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

import de.s42.dl.types.DLContainer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Benjamin Schiller
 */
public abstract class ContainerTag extends Tag implements DLContainer<Tag>
{

	protected final List<Tag> children = new ArrayList<>();

	public ContainerTag(String name)
	{
		super(name);
	}

	@Override
	protected void toHtmlContent(StringBuilder builder)
	{
		assert builder != null;

		builder.append("\n");

		for (Tag child : getChildren()) {

			child.toHtml(builder);

			builder.append("\n");
		}
	}

	@Override
	protected boolean hasContent()
	{
		return !children.isEmpty();
	}

	public Tag addChild(Tag child)
	{
		assert child != null;

		addChild(child.getName(), child);

		return child;
	}

	@Override
	public void addChild(String name, Tag child)
	{
		assert child != null;

		children.add(child);
	}

	public List<Tag> getChildren()
	{
		return Collections.unmodifiableList(children);
	}

	public <TagType extends Tag> Optional<TagType> getChild(Class<? extends TagType> type)
	{
		assert type != null;

		// Find child with given type
		for (Tag child : getChildren()) {
			if (type.isAssignableFrom(child.getClass())) {
				return Optional.of((TagType) child);
			}
		}

		return Optional.empty();
	}
}
