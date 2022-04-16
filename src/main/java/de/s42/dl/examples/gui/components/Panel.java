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

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPanel;
import de.s42.dl.java.DLContainer;

/**
 *
 * @author Benjamin Schiller
 */
public class Panel extends Component implements DLContainer<Component>
{

	protected final List<Component> components = new ArrayList<>();

	@Override
	public JComponent createJComponent()
	{
		JPanel panel = new JPanel();

		panel.setBounds(getBounds());
		panel.setLayout(null);
		panel.setBackground(Color.gray);

		// iterate components and add them to content pane
		for (Component component : components) {

			JComponent jComponent = component.createJComponent();
			panel.add(jComponent);
		}

		return panel;
	}

	@Override
	public void addChild(String name, Component child)
	{
		assert child != null;

		addComponent(child);
	}

	// <editor-fold desc="Getters/Setters" defaultstate="collapsed">
	public List<Component> getComponents()
	{
		return Collections.unmodifiableList(components);
	}

	public void setComponents(List<Component> components)
	{
		assert components != null;

		this.components.clear();
		this.components.addAll(components);
	}

	public void addComponent(Component component)
	{
		assert component != null;

		components.add(component);
	}
	//</editor-fold>
}
