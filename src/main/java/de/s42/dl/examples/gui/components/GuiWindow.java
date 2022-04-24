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
import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Benjamin Schiller
 */
public class GuiWindow extends JFrame
{

	protected int contentWidth;
	protected int contentHeight;

	public GuiWindow(String title, int contentWidth, int contentHeight)
	{
		super(title);

		this.contentWidth = contentWidth;
		this.contentHeight = contentHeight;
	}

	public int getContentWidth()
	{
		return contentWidth;
	}

	public int getContentHeight()
	{
		return contentHeight;
	}

	public void init(GuiCore core, Path mainView) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, InterruptedException, InvocationTargetException
	{
		assert core != null;
		assert mainView != null;

		// Load the main view
		View view = core.createView(mainView);

		// Exit on close window
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		// Make Swing look like the system
		javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());

		JComponent viewContent = view.createJComponent();
		getContentPane().add(viewContent);

		// Set size and fill content pane with view
		getContentPane().setPreferredSize(new Dimension(getContentWidth(), getContentHeight()));

		// Make sure the creation of the window happens in the UI Thread
		java.awt.EventQueue.invokeLater(() -> {

			// Fit window, center and show
			pack();
			setLocationRelativeTo(null);
			setVisible(true);
		});
	}
}
