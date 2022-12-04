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
package de.s42.dl.examples.gui.components;

import de.s42.dl.examples.gui.GuiCore;
import de.s42.dl.exceptions.DLException;
import java.nio.file.Path;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Benjamin Schiller
 */
public class View extends Component
{

	protected final GuiCore core;
	protected final Path file;

	public View(GuiCore core, Path file)
	{
		assert core != null;
		assert file != null;

		this.core = core;
		this.file = file;
	}

	@Override
	public JComponent createJComponent()
	{
		try {
			JPanel panel = new JPanel();

			panel.setLayout(null);

			// Load components from main view
			final List<Component> components;
			components = core.parse(file.toString()).getChildrenAsJavaType(Component.class);

			// Iterate components and add them to content pane
			for (Component component : components) {

				JComponent jComponent = component.createJComponent();
				panel.add(jComponent);
			}

			return panel;
		} catch (DLException ex) {
			throw new RuntimeException("Error loading view - " + ex.getMessage(), ex);
		}
	}

	// <editor-fold desc="Getters/Setters" defaultstate="collapsed">
	public GuiCore getCore()
	{
		return core;
	}

	public Path getFile()
	{
		return file;
	}
	//</editor-fold>
}
